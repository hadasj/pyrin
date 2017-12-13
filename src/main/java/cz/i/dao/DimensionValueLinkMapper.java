package cz.i.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cz.i.entity.db.dimension.DimensionDb;
import cz.i.entity.db.dimension.DimensionValueDb;
import cz.i.entity.db.dimension.DimensionValueLinkDb;
import cz.i.entity.model.dimension.DimensionValue;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionValueLinkMapper {

    @Select("select * from DIMENSION where ID = #{id}")
    DimensionDb dimensionById(Long id);

    @Select("select * from DIMENSION_VALUE where ID = #{id}")
    DimensionValueDb valueById(Long id);

    @Results({
        @Result(property = "dimension", column = "DIMENSION", javaType = DimensionDb.class, one = @One(select = "dimensionById")),
        @Result(property = "parent", column = "PARENT_ID", javaType = DimensionValueDb.class, one = @One(select = "valueById"))
    })
    @Select("select dm.*, l.DIMENSION_ID as DIMENSION from DIMENSION_VALUE dm " +
            "join DIMENSION_VALUE_LINK l on l.VALUE_ID = dm.id" +
            "where l.OWNER_ID = #{dimensionValue.id}")
    List<DimensionValueDb> allByOwner(DimensionValue dimensionValue);

    @Insert("insert into DIMENSION_VALUE_LINK(OWNER_ID, DIMENSION_ID, VALUE_ID) " +
        "values(#{ownerId}, #{dimensionId}, #{valueId})")
    void insert(DimensionValueLinkDb link);

}
