package com.example.mapper;

import com.example.entity.Comment;

import java.util.List;

public interface CommentMapper {
//    @Select("select * from comment")

    void add(Comment comment);

    void updateById(Comment comment);

    void deleteById(Integer id);

    List<Comment> selectAll(Comment comment);


}
