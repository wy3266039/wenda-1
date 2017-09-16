package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    /*
    用户注册
     */
    public Map<String,String> register( String username,String password){
        Map<String,String> map =new HashMap<>();

        //用户名不能为空
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return  map;
        }
        //密码不能为空
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return  map;
        }
        //用户名已经被注册
       User user= userDAO.selectByName(username);
        if (user != null ) {
            map.put("msg","用户名已经被注册");
            return map;
        }

        //用户密码加密
        user= new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket= addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }

    /*
    用户登录
     */
    public Map<String,String> login( String username,String password){

        Map<String,String> map =new HashMap<>();

        //用户名不能为空
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return  map;
        }
        //密码不能为空
        if(StringUtils.isBlank(password)){
                map.put("msg","密码不能为空");
                return  map;
        }
        //用户不存在
        User user= userDAO.selectByName(username);
        if (user == null ) {
            map.put("msg","该用户不存在");
            return map;
        }
        //用户密码对比
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","该用户不存在");
            return  map;
        }
        String ticket= addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

}
