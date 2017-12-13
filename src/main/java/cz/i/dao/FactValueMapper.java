package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.entity.model.dimension.Dimension;
import cz.i.entity.model.dimension.DimensionValue;
import cz.i.entity.model.fact.Fact;
import cz.i.entity.model.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
public interface FactValueMapper {
    @Select("select * from FACT where id = #{id,jdbcType=INTEGER}")
    Fact oneFactById(@Param("id") Long id);

    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    Dimension oneDimensionById(@Param("id") Long id);

    @Select("select * from DIMENSION_VALUE where id = #{id,jdbcType=INTEGER}")
    DimensionValue oneDimensionValueById(@Param("id") Long id);

    @Results({
        @Result(property = "valueValue", column = "VALUE_VALUE"),
        @Result(property = "fact", column = "FACT_ID", javaType = Fact.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = Dimension.class, one = @One(select = "oneDimensionById")),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValue.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT_VALUE order by id")
    List<FactValue> all();

    @Results({
        @Result(property = "valueValue", column = "VALUE_VALUE"),
        @Result(property = "fact", column = "FACT_ID", javaType = Fact.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = Dimension.class, one = @One(select = "oneDimensionById")),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValue.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT_VALUE where id = #{id,jdbcType=INTEGER}")
    FactValue oneById(@Param("id") Long id);

    @Results({
        @Result(property = "valueValue", column = "VALUE_VALUE"),
        @Result(property = "fact", column = "FACT_ID", javaType = Fact.class, one = @One(select = "oneFactById")),
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = Dimension.class, one = @One(select = "oneDimensionById")),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValue.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT_VALUE where code = #{code,jdbcType=VARCHAR} order by id")
    List<FactValue> allByCode(@Param("code") String code);

    @Insert("insert into FACT_VALUE(ID_EXT, CODE, ALIAS, DIMENSION_ID, FACT_ID, DIMENSION_VALUE_ID, VALUE_VALUE, TYPE) " +
            "values(#{idExt}, #{code}, #{alias}, #{dimension.id}, #{fact.id}, #{dimensionValue.id}, #{valueValue}, #{type})")
    int insert(FactValue factValue);

    @Update("update FACT_VALUE set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, DIMENSION_ID = #{dimension.id}, FACT_ID = #{fact.id}, " +
            "DIMENSION_VALUE_ID = #{dimensionValue.id}, VALUE_VALUE = #{valueValue}, TYPE = #{type} where ID = #{id}")
    int update(FactValue factValue);

    @Delete("delete from FACT_VALUE where ID = #{id}")
    int delete(FactValue factValue);
}
