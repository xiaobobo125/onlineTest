package com.bolife.online.mapper;

import com.bolife.online.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:28
 * @Description:
 */
@Mapper
public interface CommentMapper {
    List<Comment> getCommentByPostId(@Param("postId") int postId);

    Integer insertComment(@Param("comment") Comment comment);

    int getCount();

    List<Comment> getComments();

    Integer deleteCommentById(@Param("id") int id);
}
