package com.bolife.online.mapper;

import com.bolife.online.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/12 17:14
 * @Description:
 */
@Mapper
public interface GoodMapper {
    List<Goods> getGoodsByPostId(@Param("postId") Integer postId);

    Integer insertGood(@Param("good") Goods good);

    Integer deleteGood(@Param("accountId") Integer accountId);
}
