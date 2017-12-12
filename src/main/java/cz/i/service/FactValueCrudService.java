package cz.i.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.converter.FactValueConverter;
import cz.i.dao.FactValueMapper;
import cz.i.entity.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueCrudService {

    @Autowired
    private FactValueMapper factValueMapper;

    @Autowired
    private FactValueConverter factValueConverter;

    public FactValue oneById(Long id) {
        FactValue factValue = factValueMapper.oneById(id);
        return factValueConverter.convertDbValue(factValue);
    }

    public List<FactValue> allByCode(String code) {
        return convertValues(factValueMapper.allByCode(code));
    }

    public List<FactValue> all() {
        return convertValues(factValueMapper.all());
    }

    public int insert(FactValue factValue) {
        return factValueMapper.insert(factValueConverter.convertExternalValue(factValue));
    }

    public int update(FactValue factValue) {
        return factValueMapper.update(factValueConverter.convertExternalValue(factValue));
    }

    private List<FactValue> convertValues(List<FactValue> factValues) {
        for (FactValue factValue : factValues)
            factValueConverter.convertDbValue(factValue);
        return factValues;
    }

}
