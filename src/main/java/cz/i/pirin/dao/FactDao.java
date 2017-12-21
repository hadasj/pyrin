package cz.i.pirin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cz.i.pirin.entity.db.dimension.DimensionDb;
import cz.i.pirin.entity.db.dimension.DimensionValueDb;
import cz.i.pirin.entity.db.fact.FactDb;
import cz.i.pirin.entity.db.fact.FactValueDb;
import cz.i.pirin.entity.db.fact.ValueDb;

/**
 * @author jan.hadas@i.cz
 */
public interface FactDao {

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from FACT where id_ext = #{idExt}")
    FactDb parentByIdExt(Long idExt);

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
        @Result(property = "id", column = "ID", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "valuesByFactId")),
        @Result(property = "factType", column = "FACT_TYPE_ID", javaType = DimensionValueDb.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT where PARENT_ID = #{id,jdbcType=INTEGER}")
    List<FactDb> allByParentId(@Param("id") Long id);

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
        @Result(property = "dimension", column = "DIMENSION_ID", javaType = DimensionDb.class, one = @One(select = "oneDimensionById")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "allValuesByFactValueId"))
    })
    @Select("select * from FACT_VALUE where fact_id = #{factId} order by id")
    List<FactValueDb> valuesByFactId(Long factId);

    @Results(value = {
        @Result(property = "id", column = "ID", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "children", column = "ID", javaType = List.class, many = @Many(select = "allByParentId")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "valuesByFactId")),
        @Result(property = "factType", column = "FACT_TYPE_ID", javaType = DimensionValueDb.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT order by id")
    List<FactDb> all();

    @Results(value = {
        @Result(property = "id", column = "ID", id = true),
        @Result(property = "idExt", column = "ID_EXT"),
        @Result(property = "children", column = "ID", javaType = List.class, many = @Many(select = "allByParentId")),
        @Result(property = "values", column = "ID", javaType = List.class, many = @Many(select = "valuesByFactId")),
        @Result(property = "factType", column = "FACT_TYPE_ID", javaType = DimensionValueDb.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT where id = #{id}")
    FactDb oneById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "idExt", column = "ID_EXT")})
    @Select("select * from FACT where code = #{code,jdbcType=VARCHAR} order by id")
    List<FactDb> allByCode(@Param("code") String code);

    @Insert("insert into FACT(ID_EXT, CODE, ALIAS, NAME, FACT_TYPE_ID, PARENT_ID) " +
        "values(#{idExt}, #{code}, #{alias}, #{name}, #{factTypeId}, #{parentId})")
    void insert(FactDb fact);

    @Update("update FACT set ID_EXT = #{idExt}, CODE = #{code}, ALIAS = #{alias}, NAME=#{name}, PARENT_ID=#{parent.id} where ID = #{id}")
    void update(FactDb fact);

    @Delete("delete from FACT where ID = #{id}")
    void delete(FactDb fact);
}
