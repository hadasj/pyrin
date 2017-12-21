package cz.i.pirin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.i.pirin.dao.FactDao;
import cz.i.pirin.entity.db.fact.FactDb;
import cz.i.pirin.mapper.FactMapper;
import cz.i.pirin.model.entity.fact.Fact;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactCrudService {

    @Autowired
    private FactDao factDao;

    @Autowired
    private FactMapper factMapper;

    public List<Fact> readAllFacts() {
        List<FactDb> dbRecords = factDao.all();
        return factMapper.mapFacts(dbRecords);
    }

    public Fact readOneFact(Long id) {
        FactDb factDb = factDao.oneById(id);
        return factMapper.mapFact(factDb);
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

}
