package zy.code.project_highconcurrencymall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zy.code.project_highconcurrencymall.domain.User;
import zy.code.project_highconcurrencymall.redis.RedisService;
import zy.code.project_highconcurrencymall.redis.UserKey;
import zy.code.project_highconcurrencymall.result.Result;
import zy.code.project_highconcurrencymall.service.UserService;

@RestController
@RequestMapping("/hello")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;


    @RequestMapping("/hi")
    public String thmeleaf(Model model){

        model.addAttribute("name","zhaoyang");
        return "hello";
    }

    @RequestMapping("/getById")
    @ResponseBody
    public Result<User> getById(){
        User user = userService.getById(1);
        System.out.println(user);
        return Result.success(user);
    }

    @RequestMapping("/tx")
    @ResponseBody
    public Result<Boolean> tx(User user){

        userService.insert(user);
        return Result.success(true);
    }


//    @GetMapping("/jedis/get")
//    public Result<User> redisGet(){
//
//        User user = userService.getById(1);
//        redisService.set("user1",user);
//
//        User user1 = redisService.get("user1", User.class);
//        return Result.success(user1);
//    }
//
//    @GetMapping("/jedis/set")
//    public Result<String> redisSet(){
//        Boolean setsuccess = redisService.set("key1", "hello redis");
//        String v1 = redisService.get("key1", String.class);
//        return Result.success(v1);
//    }

        @GetMapping("/jedis/set")
    public Result<User> redisSet(User user){

        userService.insert(user);

        User user1 = userService.getById(user.getId());

        Boolean setsuccess = redisService.set(UserKey.getById,""+user1.getId(), user);

        User redis_value = redisService.get(UserKey.getById,""+user1.getId(),User.class);
        return Result.success(redis_value);
    }

    @GetMapping("/jedis/setbyname")
    public Result<User> redisSetByName(User user){

        userService.insert(user);

        User user2 = userService.getByName(user.getName());

        redisService.set(UserKey.getByName,""+user2.getName(),user2);
        User redis_value = redisService.get(UserKey.getByName, "" + user2.getName(), User.class);
        return Result.success(redis_value);
    }



}
