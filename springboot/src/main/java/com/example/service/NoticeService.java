package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.add(notice);
    }

    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    public PageInfo<Notice> selectPage(Integer pageNum, Integer pageSize,Notice notice) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }
}
