package com.bolife.online.service.impl;

import com.bolife.online.entity.Comment;
import com.bolife.online.mapper.CommentMapper;
import com.bolife.online.service.CommenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
