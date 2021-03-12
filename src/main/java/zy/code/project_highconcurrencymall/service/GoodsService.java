package zy.code.project_highconcurrencymall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zy.code.project_highconcurrencymall.dao.GoodsDao;
import zy.code.project_highconcurrencymall.domain.MiaoshaGoods;
import zy.code.project_highconcurrencymall.vo.GoodsVo;


@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		GoodsVo goodsVo = null;
		goodsVo = goodsDao.getGoodsVoByGoodsId(goodsId);
		System.out.println("===================");
		System.out.println(goodsVo.getStartDate());
		return goodsVo;

	}

	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}
	
	
	
}
