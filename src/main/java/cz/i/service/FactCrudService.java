package cz.i.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.i.dao.DimensionDao;
import cz.i.dao.FactDao;
import cz.i.entity.db.fact.FactDb;
import cz.i.entity.model.fact.Fact;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactCrudService {

    @Autowired
    private FactDao factDao;

    @Autowired
    private DimensionCrudService dimensionCrudService;

    @Autowired
    private DimensionValueCrudService dimensionValueCrudService;

    @Autowired
    private FactValueCrudService factValueCrudService;

    public List<Fact> readAllFacts() {
        List<FactDb> dbRecords = factDao.all();
        List<Fact> results = new ArrayList<>();

        for (FactDb factDb : dbRecords) {
            results.add(mapFactDeep(factDb));
        }

        return results;
    }

    public Fact readOneFact(Long id) {
        FactDb factDb = factDao.oneById(id);
        if (factDb == null)
            return null;
        return mapFactDeep(factDao.oneById(id));
    }

    @Transactional
    public void insert(Fact fact) {
        FactDb factDb = new FactDb();
        factDb.setIdExt(fact.getIdExternal());
        factDb.setCode(fact.getCode());
        factDb.setAlias(fact.getAlias());
        factDb.setName(fact.getName());

        factDao.insert(factDb);
    }

    private Fact mapFactDeep(FactDb factDb) {
        Fact fact = new Fact();
        fact.setId(factDb.getId().toString());
        fact.setIdExternal(factDb.getIdExt());
        fact.setCode(factDb.getCode());
        fact.setAlias(factDb.getAlias());
        fact.setName(factDb.getName());
        fact.setChildren(mapFacts(factDb.getChildren()));
        fact.setValues(factValueCrudService.mapFactValues(factDb.getValues()));
        fact.setMetadata(getFirstMeta(factDb.getChildren()));

        return fact;
    }

    private List<Fact> mapFacts(List<FactDb> facts) {
        if (facts == null)
            return null;

        List<Fact> results = new ArrayList<>();

        for (FactDb factDb : facts) {
            results.add(mapFact(factDb));
        }

        return results;
    }

    private Fact mapFact(FactDb factDb) {
        Fact fact = new Fact();
        fact.setId(factDb.getId().toString());
        fact.setIdExternal(factDb.getIdExt());
        fact.setCode(factDb.getCode());
        fact.setAlias(factDb.getAlias());
        fact.setName(factDb.getName());
        fact.setValues(factValueCrudService.mapFactValues(factDb.getValues()));

        return fact;
    }

    private Fact getFirstMeta(List<FactDb> children) {
        if (children != null)
            for (FactDb child : children) {
                if (child.getFactType() != null && child.getFactType().getDimension() != null &&
                        child.getFactType().getDimension().getCode().equals(DimensionDao.DIMENSION_OF_FACTS_CODE)) {
                    return mapFact(child);
                }
            }
        return null;
    }
}
