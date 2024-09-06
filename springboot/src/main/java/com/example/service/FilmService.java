package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Resource
    private FilmMapper filmMapper;
    @Resource
    private CommentMapper  commentMapper;

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

    public Film selectById(Integer id) {
        Film film = filmMapper.selectById(id);
        this.setScore(film);
        return film;
    }

    public PageInfo<Film> selectPage(Integer pageNum, Integer pageSize,Film film) {
        PageHelper.startPage(pageNum, pageSize);
        List<Film> list = filmMapper.selectAll(film);
        for (Film f : list) {
            this.setScore(f);
        }
        return PageInfo.of(list);
    }

    public List<Film> selectRecommend(Integer filmId) {
        List<Film> films = this.selectAll(null);
        films = films.stream().filter(f -> !f.getId().equals(filmId)).collect(Collectors.toList());
        Collections.shuffle(films);
        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());
        for (Film film : filmList) {
            this.setScore(film);
        }
        return filmList;
    }

    public List<Film> selectRanking() {
        List<Film> filmList = filmMapper.selectAll(null);
        for (Film film : filmList) {
            setScore(film);
        }
       return  filmList.stream().sorted((f1,f2)->f2.getScore().compareTo(f1.getScore())).limit(5).collect(Collectors.toList());
    }

    public void setScore(Film film){
        int total = commentMapper.selectByTotal(film.getId());
        film.setCommentNum(total);
        if(total == 0){
            film.setScore(0D);
        }else{
            double sum = commentMapper.selectSum(film.getId());
            BigDecimal score = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total), 1, RoundingMode.HALF_UP);
            film.setScore(score.doubleValue());
        }
    }
}

