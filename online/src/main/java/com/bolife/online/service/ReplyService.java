package com.bolife.online.service;

import com.bolife.online.entity.Reply;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:46
 * @Description:
 */
public interface ReplyService {
    public List<Reply> getReplyByPostId(int postId);

    int addReply(Reply reply);
}
