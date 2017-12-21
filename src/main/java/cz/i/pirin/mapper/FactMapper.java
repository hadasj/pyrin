package cz.i.pirin.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.pirin.dao.DimensionDao;
import cz.i.pirin.entity.db.fact.FactDb;
import cz.i.pirin.model.entity.fact.Fact;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactMapper {
    @Autowired
    private FactValueMapper factValueMapper;

    public List<Fact> mapFacts(List<FactDb> factsDb) {
        List<Fact> facts = new ArrayList<>();

        if (factsDb != null)
            for (FactDb factDb : factsDb) {
                facts.add(mapFact(factDb));
            }
        return facts;
    }

    public Fact mapFact(FactDb factDb) {
        if (factDb == null)
            return null;

        String id = factDb.getId().toString();

        Fact fact = new Fact(id, factDb.getAlias(), factDb.getCode(), factDb.getName());
        fact.getValues().addAll(factValueMapper.mapFactValues(factDb.getValues()));
        fact.getChildren().addAll(mapFacts(factDb.getChildren()));
        fact.setMetadata(getFirstMeta(factDb.getChildren()));

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
