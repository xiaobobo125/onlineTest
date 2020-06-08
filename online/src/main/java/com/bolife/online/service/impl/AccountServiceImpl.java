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
@SuppressWarnings("all")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAllAccount() {
        return accountMapper.getAccounts();
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    @Override
    public Account getAccountById(int authorId) {
        return accountMapper.getAccountById(authorId);
    }

    @Override
    public boolean updateAccount(Account currentAccount) {
        return accountMapper.updateAccountById(currentAccount);
    }
}
