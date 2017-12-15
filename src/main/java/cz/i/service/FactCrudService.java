package cz.i.service;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.common.ValueType;
import cz.i.dao.DimensionMapper;
import cz.i.dao.FactMapper;
import cz.i.entity.db.fact.FactDb;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.model.fact.Fact;
import cz.i.entity.model.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactCrudService {

    @Autowired
    private FactMapper factMapper;

    @Autowired
    private DimensionCrudService dimensionCrudService;

    @Autowired
    private DimensionValueCrudService dimensionValueCrudService;

    public List<Fact> readAllFacts() {
        List<FactDb> dbRecords = factMapper.all();
        List<Fact> results = new ArrayList<>();

        for (FactDb factDb : dbRecords) {
            results.add(mapFactDeep(factDb));
        }

        return results;
    }

    private Fact mapFactDeep(FactDb factDb) {
        Fact fact = new Fact();
        fact.setId(factDb.getId().toString());
        fact.setIdExternal(factDb.getIdExt());
        fact.setCode(factDb.getCode());
        fact.setAlias(factDb.getAlias());
        fact.setName(factDb.getName());
        fact.setChildren(mapFacts(factDb.getChildren()));
        fact.setValues(mapValues(factDb.getValues()));
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
        fact.setValues(mapValues(factDb.getValues()));

        return fact;
    }


    private List<FactValue> mapValues(List<FactValueDb> values) {
        if (values == null)
            return null;

        List<FactValue> results = new ArrayList<>();

        for (FactValueDb factValueDb : values) {
            FactValue factValue = new FactValue();
            factValue.setId(factValueDb.getId().toString());
            factValue.setIdExternal(factValueDb.getIdExt());
            factValue.setCode(factValueDb.getCode());
            factValue.setAlias(factValueDb.getAlias());
            factValue.setDimension(dimensionCrudService.mapDimension(factValueDb.getDimension()));
            factValue.setValueType(factValueDb.getValueType());

            // TODO: MULTIPLE values
            Object value;
            if (ValueType.DIMENSION_VALUE == factValueDb.getValueType())
                // TODO: DIMENSION_VALUE - use code or localized text??
                value = dimensionValueCrudService.mapDimensionValue(factValueDb.getDimensionValue());
            else
                value = factValueDb.getValue();
            factValue.setValues(asList(value));

            results.add(factValue);
        }
        return results;
    }

    private Fact getFirstMeta(List<FactDb> children) {
        if (children != null)
            for (FactDb child : children) {
                if (child.getFactType() != null && child.getFactType().getDimension() != null &&
                        child.getFactType().getDimension().getCode().equals(DimensionMapper.DIMENSION_OF_FACTS_CODE)) {
                    return mapFact(child);
                }
            }
        return null;
    }
}
