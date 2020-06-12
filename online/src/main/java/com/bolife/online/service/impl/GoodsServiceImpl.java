package com.bolife.online.service.impl;

import com.bolife.online.entity.Goods;
import com.bolife.online.mapper.GoodMapper;
import com.bolife.online.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/12 17:14
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Override
    public List<Goods> getGoodsByPostId(Integer postId) {
        return goodMapper.getGoodsByPostId(postId);
    }

    @Override
    public Integer addGood(Goods goods) {
        return goodMapper.insertGood(goods);
    }

    @Override
    public Integer subGood(Goods goods) {
        return goodMapper.deleteGood(goods.getAccountId());
    }

}
