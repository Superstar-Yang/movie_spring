package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/selectAll")

    public Result selectAll(Notice notice) {
//        ArrayList<Object> list = new ArrayList<>();
//        Notice notice =  new Notice();
//        notice.setTitle("系统公告");
//        notice.setContent("系统公告内容");
//        list.add(notice);
        List<Notice> list = noticeService.selectAll(notice);
        return Result.success(list);
    }

    /*
     * 分页查询
     * @params pageNum='当前页'
     * @params pageSize='当前条数'
     * */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Notice notice) {
        PageInfo<Notice> list = noticeService.selectPage(pageNum, pageSize, notice);
        return Result.success(list);
    }
}
