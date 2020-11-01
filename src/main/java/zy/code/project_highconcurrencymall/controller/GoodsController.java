package zy.code.project_highconcurrencymall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zy.code.project_highconcurrencymall.domain.MiaoshaUser;
import zy.code.project_highconcurrencymall.redis.RedisService;
import zy.code.project_highconcurrencymall.service.GoodsService;
import zy.code.project_highconcurrencymall.service.MiaoshaUserService;
import zy.code.project_highconcurrencymall.vo.GoodsVo;

/**
 * 订单管理
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;

	/**
	 * 商品列表
	 */
    @RequestMapping(value="/to_list")
    public String list(Model model, MiaoshaUser user) {
    	//获取当前登录用户
    	model.addAttribute("user", user);

    	//查询所有商品的信息
    	List<GoodsVo> goodsList = goodsService.listGoodsVo();
    	//将商品集合放入request域中
    	model.addAttribute("goodsList", goodsList);
    	//定位到商品列表页面
    	 return "goods_list";
    }

	/**
	 * 商品详情
	 */
	@RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

    	model.addAttribute("goods", goods);
    	
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	
    	int miaoshaStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		miaoshaStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		miaoshaStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		miaoshaStatus = 1;
    		remainSeconds = 0;
    	}
    	model.addAttribute("miaoshaStatus", miaoshaStatus);//当前商品的状态
    	model.addAttribute("remainSeconds", remainSeconds);//倒计时，以秒为单位
        return "goods_detail";
    }
    
}
