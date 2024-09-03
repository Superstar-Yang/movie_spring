package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/selectAll")

    public Result selectAll(Category category) {
//        ArrayList<Object> list = new ArrayList<>();
//        Category category =  new Category();
//        category.setTitle("系统公告");
//        category.setContent("系统公告内容");
//        list.add(category);
        List<Category> list = categoryService.selectAll(category);
        return Result.success(list);
    }

    /*
     * 分页查询
     * @params pageNum='当前页'
     * @params pageSize='当前条数'
     * */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Category category) {
        PageInfo<Category> list = categoryService.selectPage(pageNum, pageSize, category);
        return Result.success(list);
    }
}
