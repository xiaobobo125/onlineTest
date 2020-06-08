package com.bolife.online.mapper;

import com.bolife.online.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:47
 * @Description:
 */
@Mapper
public interface ReplyMapper {
    List<Reply> getReplyByPostId(@Param("postId") int postId);

    int insertReply(@Param("reply") Reply reply);
}
