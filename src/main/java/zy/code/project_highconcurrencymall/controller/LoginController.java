package zy.code.project_highconcurrencymall.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zy.code.project_highconcurrencymall.redis.RedisService;
import zy.code.project_highconcurrencymall.result.CodeMsg;
import zy.code.project_highconcurrencymall.result.Result;
import zy.code.project_highconcurrencymall.service.MiaoshaUserService;
import zy.code.project_highconcurrencymall.util.ValidatorUtil;
import zy.code.project_highconcurrencymall.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    MiaoshaUserService miaoshaUserService;
	
	@Autowired
    RedisService redisService;

    /**
     * 登录页面
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * =登录功能
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid  LoginVo loginVo) {
        //打印日志
    	log.info("loginController [dologin Method]"+loginVo.toString());
        //登录
         boolean loginSuccess = miaoshaUserService.login(response, loginVo);
        return Result.success(true);

    }
}
