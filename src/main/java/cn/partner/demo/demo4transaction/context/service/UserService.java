package cn.partner.demo.demo4transaction.context.service;

import cn.partner.demo.demo4transaction.context.entity.UserEntity;
import cn.partner.demo.demo4transaction.context.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * Created by Yonsin on 17/12/22.
 */
@Service
public class UserService {

    private @Autowired UserMapper userMapper;
    private @Autowired UserService self;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public String process() {
        UserEntity userEntity = userMapper.findById(1);
        p("1.主线程查询结果a: " + userEntity.getName());

        //
        new Thread(() -> self.update()).start();

        //
        p("1.主线程sleep 5s");
        sleep(5000);


        //
        p("1.主线程醒");
        UserEntity userEntity1 = userMapper.findById(1);
        p("1.主线程查询结果b: " + userEntity1.getName());
        p("1.主线程sleep 20s");
        sleep(20000);

        //
        p("1.主线程醒");
        UserEntity userEntity2 = userMapper.findById(1);
        p("1.主线程查询结果c: " + userEntity2.getName());

        p("1.结束退出抛异常");

        if(userEntity1.getName().equals(userEntity.getName())) {
            return "Error!";
        }
        return "Success!";
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void update() {
        p("2.进入子线程");

        UserEntity userEntity = userMapper.findById(1);
        userEntity.setName(random());
        userMapper.update(userEntity);


        UserEntity userEntity2 = userMapper.findById(1);
        p("2.子线程修改后子线程里查询结果: " + userEntity2.getName());

        p("2.子线程sleep 10s");
        sleep(10000);

        p("2.子线程结束(commit)");
    }





    void p(Object o) {
        System.out.println(o);
    }

    String random() {
        return String.valueOf(new Random().nextDouble());
    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        initDB();
    }

    void initDB() {
        userMapper.createTableIfNotExist();

        UserEntity userEntity = userMapper.findById(1);
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setName(random());
            userMapper.save(userEntity);
        }
    }

}
