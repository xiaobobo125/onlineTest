package com.bolife.online.service;

import com.bolife.online.entity.Goods;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/12 17:13
 * @Description:
 */
public interface GoodService {
    public List<Goods> getGoodsByPostId(Integer postId);

    Integer addGood(Goods goods);

    Integer subGood(Goods goods);
}
