package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 插入中间表
     * INSERT INTO tb_category_brand ( category_id, brand_id )
     * VALUES
     * ( 1, 2 )
     *
     * @param cid
     * @param bid
     * @return
     */
    @Insert(" INSERT INTO tb_category_brand ( category_id, brand_id ) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id=#{bid} ")
    int deleteCategoryBrand(@Param("bid") Long bid);
}
