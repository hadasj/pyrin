package cz.i.dao;

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

import cz.i.entity.dimension.DimensionValue;
import cz.i.entity.fact.Fact;
import cz.i.entity.fact.FactValue;

/**
 * @author jan.hadas@i.cz
 */
public interface FactMapper {
    @Select("select * from FACT where parent_id = #{parentId} order by id")
    List<Fact> childrenByParentId(Long parentId);

    @Select("select * from FACT where id = #{id} order by id")
    List<Fact> parentById(Long id);

    @Select("select * from DIMENSION_VALUE where id = #{id,jdbcType=INTEGER}")
    DimensionValue oneDimensionValueById(@Param("id") Long id);

    @Results({
        @Result(property = "valueValue", column = "VALUE_VALUE"),
        @Result(property = "dimensionValue", column = "DIMENSION_VALUE_ID", javaType = DimensionValue.class, one = @One(select = "oneDimensionValueById"))
    })
    @Select("select * from FACT_VALUE where fact_id = #{factId} order by id")
    List<FactValue> valuesByFactId(Long factId);

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "childrenByParentId")),
        @Result(property = "parent", column = "parent_id", javaType = Fact.class, one = @One(select = "parentById")),
        @Result(property = "values", column = "id", javaType = List.class, many = @Many(select = "valuesByFactId"))})
    @Select("select * from FACT order by id")
    List<Fact> all();

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "childrenByParentId")),
        @Result(property = "parent", column = "parent_id", javaType = Fact.class, one = @One(select = "parentById"))})
    @Select("select * from FACT where id = #{id,jdbcType=INTEGER}")
    Fact oneById(@Param("id") Long id);

    @Results(value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "childrenByParentId")),
        @Result(property = "parent", column = "parent_id", javaType = Fact.class, one = @One(select = "parentById"))})
    @Select("select * from FACT where code = #{code,jdbcType=VARCHAR} order by id")
    List<Fact> allByCode(@Param("code") String code);

    @Insert("insert into FACT(CODE, ALIAS, NAME, PARENT_ID) values(#{code}, #{alias}, #{name}, #{parent.id})")
    void insert(Fact fact);

    @Update("update FACT set CODE = #{code}, ALIAS = #{alias}, NAME=#{name}, PARENT_ID=#{parent.id} where ID = #{id}")
    void update(Fact fact);

    @Delete("delete from FACT where ID = #{id}")
    void delete(Fact fact);
}
