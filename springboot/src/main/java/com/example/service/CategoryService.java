package com.example.service;

import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public void add(Category category) {
        categoryMapper.add(category);
    }

    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    public PageInfo<Category> selectPage(Integer pageNum, Integer pageSize,Category category) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}
