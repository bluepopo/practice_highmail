package zy.code.project_highconcurrencymall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zy.code.project_highconcurrencymall.dao.UserDao;
import zy.code.project_highconcurrencymall.domain.User;

@Service
public class UserService {
    @Autowired
   UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }


    //@Transactional
    public void insert (User user){
        userDao.insert(user);
//
//        User u2 = new User();
//        u1.setId(4);
//        u1.setName("李四");
//        userDao.insert(u2);
    }


    public User getByName(String name){
        return userDao.getByName(name);
    }

}
