package cz.i.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.common.ValueType;
import cz.i.dao.ValueMapper;
import cz.i.entity.db.fact.ValueDb;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class ValueCrudService {

    @Autowired
    private ValueMapper valueMapper;

    public void insert(Object value, ValueType valueType, Long factValueId) {
        ValueDb valueDb = new ValueDb();
        valueDb.setValue(value, valueType);
        valueDb.setFactValueId(factValueId);

        valueMapper.insert(valueDb);
    }
}
