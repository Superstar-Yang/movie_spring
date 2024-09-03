package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {
//    @Select("select * from notice")

    void add(Notice notice);

    void updateById(Notice notice);

    void deleteById(Integer id);

    List<Notice> selectAll(Notice notice);


}
