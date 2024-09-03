package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.add(comment);
    }

    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    public PageInfo<Comment> selectPage(Integer pageNum, Integer pageSize,Comment comment) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }
}
