package com.bolife.online.service.impl;

import com.bolife.online.entity.Reply;
import com.bolife.online.mapper.ReplyMapper;
import com.bolife.online.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:46
 * @Description:
 */
@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> getReplyByPostId(int postId) {
        return replyMapper.getReplyByPostId(postId);
    }

    @Override
    public int addReply(Reply reply) {
        return replyMapper.insertReply(reply);
    }
}
