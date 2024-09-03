package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Film;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    @Resource
    private FilmMapper filmMapper;

    public void add(Film film) {
        filmMapper.add(film);
    }

    public void updateById(Film film) {
        filmMapper.updateById(film);
    }

    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }

    public List<Film> selectAll(Film film) {
        return filmMapper.selectAll(film);
    }

    public PageInfo<Film> selectPage(Integer pageNum, Integer pageSize,Film film) {
        PageHelper.startPage(pageNum, pageSize);
        List<Film> list = filmMapper.selectAll(film);
        return PageInfo.of(list);
    }
}
