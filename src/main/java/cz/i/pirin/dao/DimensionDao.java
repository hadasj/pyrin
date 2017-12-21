package cz.i.pirin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cz.i.pirin.entity.db.dimension.DimensionDb;

/**
 * @author jan.hadas@i.cz
 */
public interface DimensionDao {
    public static final String DIMENSION_OF_FACTS_CODE = "DFAC";

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION order by id_ext, id")
    List<DimensionDb> all();

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id = #{id,jdbcType=INTEGER}")
    DimensionDb oneById(@Param("id") Long id);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where id_ext = #{idExt}")
    DimensionDb oneByIdExt(Long idExt);

    @Results(@Result(property = "idExt", column = "ID_EXT"))
    @Select("select * from DIMENSION where code = #{code,jdbcType=VARCHAR}")
    DimensionDb oneByCode(@Param("code") String code);

    @Insert("insert into DIMENSION(ID_EXT, CODE, ALIAS, MODE, STRUCTURE, TYPE, TEXT_CS, TEXT_EN, TEXT_BG) " +
        "values(#{idExt}, #{code}, #{alias}, #{mode}, #{structure}, #{type}, #{textCs}, #{textEn}, #{textBg})")
    void insert(DimensionDb dimension);
}
