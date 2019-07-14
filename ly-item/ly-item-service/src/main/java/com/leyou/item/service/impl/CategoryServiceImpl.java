package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryByParentId(Long pid) {
        Category category=new Category();
        category.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(categoryList)){
            throw new LyExcetion(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categoryList;
    }

    @Override
    public List<Category> queryByBrandId(Long bid) {
        List<Category> list=categoryMapper.queryByBrandId(bid);
        if (CollectionUtils.isEmpty(list)){
            throw new LyExcetion(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    public void saveCategory(Category category) {
        int count = categoryMapper.insertSelective(category);
        if (count!=1){
            throw new LyExcetion(ExceptionEnum.CATEGORY_SAVE_FAIL);
        }
    }

    @Override
    public void updateCategory(Category category) {
        if (category.getId()==null){
            throw  new LyExcetion(ExceptionEnum.CATEGORY_UPDATE_FAIL);
        }
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (count!=1){
            throw new LyExcetion(ExceptionEnum.CATEGORY_UPDATE_FAIL);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        if (id==null){
            throw new LyExcetion(ExceptionEnum.CATEGORY_DELETE_FAIL);
        }
        //判断是否有儿子节点 如果有的情况 不删除
        Category record=new Category();
        record.setParentId(id);
        List<Category> list = categoryMapper.select(record);
        if (!CollectionUtils.isEmpty(list)){
            throw new LyExcetion(ExceptionEnum.CATEGORY_DELETE_FAIL);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count!=1){
            throw new LyExcetion(ExceptionEnum.CATEGORY_DELETE_FAIL);
        }
    }
}
