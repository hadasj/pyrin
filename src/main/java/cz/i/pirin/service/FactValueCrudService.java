package cz.i.pirin.service;

import static cz.i.pirin.util.Util.parseLong;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.i.pirin.dao.FactValueDao;
import cz.i.pirin.entity.db.fact.FactValueDb;
import cz.i.pirin.mapper.FactValueMapper;
import cz.i.pirin.model.entity.fact.FactValue;


/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueCrudService {

    @Autowired
    private FactValueDao factValueDao;

    @Autowired
    private ValueCrudService valueCrudService;

    @Autowired
    private FactValueMapper factValueMapper;

    public List<FactValue> readAllFactValues() {
        return factValueMapper.mapFactValues(factValueDao.all());
    }

    public FactValue readOneFactValue(Long id) {
        FactValueDb factValueDb = factValueDao.oneById(id);
        if (factValueDb == null)
            return null;
        return factValueMapper.mapFactValue(factValueDb);
    }

    @Transactional
    public void insert(FactValue factValue, Long factId) {
        FactValueDb factValueDb = new FactValueDb();
        factValueDb.setIdExt(factValue.getIdExternal());
        factValueDb.setAlias(factValue.getAlias());
        factValueDb.setCode(factValue.getCode());
        if (factValue.getDimension() != null)
            factValueDb.setDimensionId(parseLong(factValue.getDimension().getId()));
        factValueDb.setFactId(factId);

        factValueDao.insert(factValueDb);
        for (Object value : factValue.getValues())
            valueCrudService.insert(value, factValue.getValueType(), factValueDb.getId());
    }


}
