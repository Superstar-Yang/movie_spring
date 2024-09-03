package com.example.controller;

import com.example.common.Result;
import com.example.entity.Comment;
import com.example.service.CommentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Comment comment) {
        commentService.updateById(comment);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/selectAll")

    public Result selectAll(Comment comment) {
//        ArrayList<Object> list = new ArrayList<>();
//        Comment comment =  new Comment();
//        comment.setTitle("系统公告");
//        comment.setContent("系统公告内容");
//        list.add(comment);
        List<Comment> list = commentService.selectAll(comment);
        return Result.success(list);
    }

    /*
     * 分页查询
     * @params pageNum='当前页'
     * @params pageSize='当前条数'
     * */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Comment comment) {
        PageInfo<Comment> list = commentService.selectPage(pageNum, pageSize, comment);
        return Result.success(list);
    }
}
