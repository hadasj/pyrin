package cz.i.pirin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.pirin.dao.ValueDao;
import cz.i.pirin.entity.db.fact.ValueDb;
import cz.i.pirin.model.entity.dimension.ValueType;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class ValueCrudService {

    @Autowired
    private ValueDao valueDao;

    public void insert(Object value, ValueType valueType, Long factValueId) {
        ValueDb valueDb = new ValueDb();
        valueDb.setValue(value, valueType);
        valueDb.setFactValueId(factValueId);

        valueDao.insert(valueDb);
    }
}
