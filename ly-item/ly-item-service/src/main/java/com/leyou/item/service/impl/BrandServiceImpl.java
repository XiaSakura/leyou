package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //1.利用分页助手开始分页 底层利用mybatis的拦截器 对接下来的执行的sql进行拦截 自动的拼接上limit语句
        PageHelper.startPage(page, rows);
        //2.过滤
        Example example = new Example(Brand.class); ////利用反射得到 表名这些 就可以帮我们修改sql
        if (!StringUtils.isEmpty(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orLike("letter", key.toUpperCase());
        }
        //3.排序
        if (!StringUtils.isEmpty(sortBy)) {
            String orderByClause = sortBy + " " + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);//clause是语句的意思  因为通用mapper不知道我们会根据哪个字段进行排序 没有办法帮我们自动生成 所以需要我们自己写sql语句
        }
        //4.查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyExcetion(ExceptionEnum.BRAND_NOT_FOUND);
        }

        //5.解析分页结果 获取总条数等等信息
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<Brand>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 我们不仅要新增品牌，还要维护品牌和商品分类的中间表。因为中间表 没有实体类这些所以我们要单独写sql
     *
     * @param brand 品牌
     * @param cids  分类ids
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        // 新增品牌信息
        int count = brandMapper.insertSelective(brand);
        if (count != 1) {
            throw new LyExcetion(ExceptionEnum.BRAND_SAVE_FAIL);
        }
        //新增中间表
        for (Long cid : cids) {
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyExcetion(ExceptionEnum.BRAND_SAVE_FAIL);
            }
        }
    }

    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌信息
        int count = brandMapper.updateByPrimaryKeySelective(brand);
        if (count != 1) {
            throw new LyExcetion(ExceptionEnum.BRAND_UPDATE_FAIL);
        }
        //我们需要先删除中间表中此brand的信息 再添加修改的
        count = brandMapper.deleteCategoryBrand(brand.getId());
        if (count < 1) {
            throw new LyExcetion(ExceptionEnum.BRAND_UPDATE_FAIL);
        }
        //新增中间表
        for (Long cid : cids) {
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyExcetion(ExceptionEnum.BRAND_UPDATE_FAIL);
            }
        }
    }

    @Override
    @Transactional
    public void deleteBrand(Long bid) {
        //删除品牌表内容
        int count = brandMapper.deleteByPrimaryKey(bid);
        if (count < 1) {
            throw new LyExcetion(ExceptionEnum.BRAND_DELETE_FAIL);
        }
        //我们需要先删除中间表中此brand的信息
        count = brandMapper.deleteCategoryBrand(bid);
        if (count < 1) {
            throw new LyExcetion(ExceptionEnum.BRAND_DELETE_FAIL);
        }
    }
}
