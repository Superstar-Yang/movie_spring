package com.example.controller;

import com.example.common.Result;
import com.example.entity.Film;
import com.example.service.FilmService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Resource
    private FilmService filmService;
    @PostMapping("/add")
    public Result add(@RequestBody Film film) {
        System.out.println(film);
        filmService.add(film);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Film film) {
        filmService.updateById(film);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        filmService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/selectAll")

    public Result selectAll(Film film) {
        List<Film> list = filmService.selectAll(film);
        return Result.success(list);
    }
    @GetMapping("/selectRanking")
    public Result selectRanking() {
        List<Film> list = filmService.selectRanking();
        return Result.success(list);
    }
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Film film = filmService.selectById(id);
        return Result.success(film);
    }

    @GetMapping("/selectRecommend/{id}")
    public Result selectRecommend(@PathVariable Integer id) {
        List<Film> list = filmService.selectRecommend(id);
        return Result.success(list);
    }

    /*
     * 分页查询
     * @params pageNum='当前页'
     * @params pageSize='当前条数'
     * */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Film film) {
        PageInfo<Film> list = filmService.selectPage(pageNum, pageSize, film);
        return Result.success(list);
    }
}
