package com.bolife.online.service.impl;

import com.bolife.online.entity.Account;
import com.bolife.online.mapper.AccountMapper;
import com.bolife.online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:24
 * @Description:
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAllAccount() {
        return accountMapper.getAccounts();
    }
}
