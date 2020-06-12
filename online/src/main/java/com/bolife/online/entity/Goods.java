package com.bolife.online.entity;

import java.io.Serializable;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/12 16:55
 * @Description:
 */
public class Goods implements Serializable {

    private Integer id;
    private Integer postId;
    private Integer accountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", postId=" + postId +
                ", accountId=" + accountId +
                '}';
    }
}
