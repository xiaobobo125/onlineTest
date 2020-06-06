package com.bolife.online.mapper;

import com.bolife.online.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:25
 * @Description:
 */
@Mapper
public interface AccountMapper {
    public List<Account> getAccounts();
}
