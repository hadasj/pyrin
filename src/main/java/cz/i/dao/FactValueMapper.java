package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.db.fact.FactDb;
import cz.i.entity.db.fact.FactValueDb;
import cz.i.entity.db.fact.ValueDb;

/**
 * @author jan.hadas@i.cz
 */
public interface FactValueMapper {

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    DimensionDb oneDimensionById(@Param("id") Long id);

    @Results({
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = DimensionDb.class, one = @One(select = "oneDimensionById")),
    })
    @Select("select * from DIMENSION_VALUE where id = #{id,jdbcType=INTEGER}")
    DimensionValueDb oneDimensionValueById(@Param("id") Long id);

    @Results({
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "factType", column = "FACT_TYPE_ID", javaType = DimensionValueDb.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT where id = #{id,jdbcType=INTEGER}")
    FactDb oneFactById(@Param("id") Long id);

    @Results({
        @Result(property = "valueTimestamp", column = "VALUE_TIMESTAMP"),
        @Result(property = "valueString", column = "VALUE_STRING"),
        @Result(property = "valueInt", column = "VALUE_INT"),
        @Result(property = "valueLong", column = "VALUE_LONG"),
        @Result(property = "valueDouble", column = "VALUE_DOUBLE"),
        @Result(property = "valueBigdecimal", column = "VALUE_BIGDECIMAL"),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValueDb.class, one = @One(select = "oneDimensionValueById")),
        @Result(property = "valueType", column = "VALUE_TYPE")
    })
    @Select("select * from VALUE where FACT_VALUE_ID = #{factValueId}")
    List<ValueDb> allValuesByFactValueId(Long factValueId);

    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "fact", column = "FACT_ID", javaType = FactDb.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = DimensionDb.class, one = @One(select = "oneDimensionById")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "allValuesByFactValueId"))
    })
    @Select("select * from FACT_VALUE order by id")
    List<FactValueDb> all();

    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "fact", column = "FACT_ID", javaType = FactDb.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = DimensionDb.class, one = @One(select = "oneDimensionById")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "allValuesByFactValueId"))
    })
    @Select("select * from FACT_VALUE where id = #{id,jdbcType=INTEGER}")
    FactValueDb oneById(@Param("id") Long id);

    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "fact", column = "FACT_ID", javaType = FactDb.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = DimensionDb.class, one = @One(select = "oneDimensionById")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "allValuesByFactValueId"))
    })
    @Select("select * from FACT_VALUE where code = #{code,jdbcType=VARCHAR} order by id")
    List<FactValueDb> allByCode(@Param("code") String code);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into FACT_VALUE(ID_EXT, CODE, ALIAS, DIMENSION_ID, FACT_ID) " +
            "values(#{idExt}, #{code}, #{alias}, #{dimensionId}, #{factId})")
    int insert(FactValueDb factValue);

    @Update("update FACT_VALUE set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, DIMENSION_ID = #{dimension.id}, FACT_ID = #{fact.id}, " +
            "DIMENSION_VALUE_ID = #{dimensionValue.id}, VALUE_VALUE = #{valueValue}, TYPE = #{type} where ID = #{id}")
    int update(FactValueDb factValue);

    @Delete("delete from FACT_VALUE where ID = #{id}")
    int delete(FactValueDb factValue);
}
