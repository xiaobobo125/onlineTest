package com.bolife.online.service;

import com.bolife.online.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:27
 * @Description:
 */
public interface CommenService {
    public List<Comment> getCommentByPostId(int postId);

    Integer addComment(Comment comment);

    Map<String,Object> getComments(int page, int commentPageSize);

    boolean deleteCommentById(int id);
}
