package cz.i.dao;

import org.apache.ibatis.annotations.Insert;

import cz.i.entity.db.fact.ValueDb;

/**
 * @author jan.hadas@i.cz
 */
public interface ValueDao {
    @Insert("insert into VALUE(FACT_VALUE_ID, DIMENSION_VALUE_ID, VALUE_TIMESTAMP, VALUE_STRING, VALUE_INT, VALUE_LONG, VALUE_DOUBLE, VALUE_BIGDECIMAL, VALUE_TYPE) " +
        "values(#{factValueId}, #{dimensionValueId}, #{valueTimestamp}, #{valueString}, #{valueInt}, #{valueLong}, #{valueDouble}, #{valueBigdecimal}, #{valueType})")
    int insert(ValueDb value);
}
