package qianggou.service;

import java.util.List;

import qianggou.bean.Goods;
import qianggou.utils.Result;

public interface SeckillGoodsService {

	List<Goods> findAll();
	Goods findOne(long id);
	Result saveOrder(long id, String userId);
}
