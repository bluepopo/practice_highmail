package zy.code.project_highconcurrencymall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zy.code.project_highconcurrencymall.controller.LoginController;
import zy.code.project_highconcurrencymall.dao.MiaoshaUserDao;
import zy.code.project_highconcurrencymall.domain.MiaoshaUser;
import zy.code.project_highconcurrencymall.exception.GlobalException;
import zy.code.project_highconcurrencymall.redis.MiaoshaUserKey;
import zy.code.project_highconcurrencymall.redis.RedisService;
import zy.code.project_highconcurrencymall.result.CodeMsg;
import zy.code.project_highconcurrencymall.result.Result;
import zy.code.project_highconcurrencymall.util.MD5Util;
import zy.code.project_highconcurrencymall.util.UUIDUtil;
import zy.code.project_highconcurrencymall.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    private static Logger log = LoggerFactory.getLogger(MiaoshaUserService.class);
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;
    /**
     * 用户登录功能
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null){
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser userDB = miaoshaUserDao.findById(Long.parseLong(mobile));
        //用户名未注册
        if (userDB == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //密码校验
        String dbPassword = userDB.getPassword();
        String dbSalt = userDB.getSalt();

        //网络表单密码加密为数据库密码
        String  calcPassword = MD5Util.formPassToDBPass(password,dbSalt);

        //对比两个加密后的密码是否一致
        if (!calcPassword.equals(dbPassword)) {
            throw  new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        log.info("MiaoshaUserService [login Method] LoginVo calcPassword 表单密码加密之后 :" + calcPassword);

        //登录成功
        //生成唯一token
        String token = UUIDUtil.uuid();
        //将token与查询出的user对象写回Cookie
        addCookie(response, token, userDB);

        return true;
    }

    /**
     * 根据 id 查询
     */
    public MiaoshaUser findById(long id){
        return miaoshaUserDao.findById(id);
    }


    /**
     * 根据 tocken获取缓存中的user对象
     * 只要用户登录成功，缓存中就会存储 token-->miaoshauser的键值对
     */

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长该token的redis有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     *  方法功能：将 token-->miaoshaUser存入redis缓存，并生成Cookie写回 response
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        //将token与对应的user加入到redis缓存
        redisService.set(MiaoshaUserKey.token, token, user);
        //创建cookie并设置name与value
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置cookie的最大期限
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        //设置cookie的访问路径
        cookie.setPath("/mall");
        //将cookie写回应答
        response.addCookie(cookie);
    }
}
