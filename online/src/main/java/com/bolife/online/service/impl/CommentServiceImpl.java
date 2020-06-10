package com.bolife.online.service.impl;

import com.bolife.online.entity.Comment;
import com.bolife.online.mapper.CommentMapper;
import com.bolife.online.service.CommenService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:28
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommenService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentByPostId(int postId) {
        return commentMapper.getCommentByPostId(postId);
    }

    @Override
    public Integer addComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    public Map<String, Object> getComments(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = commentMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("comments", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("comments", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.getComments();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("comments", comments);
        return data;
    }

    @Override
    public boolean deleteCommentById(int id) {
        return commentMapper.deleteCommentById(id) > 0;
    }
}
