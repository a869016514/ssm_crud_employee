package qianggou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qianggou.bean.Goods;
import qianggou.service.SeckillGoodsService;
import qianggou.service.serviceImpl.SeckillGoodsServiceImpl;
import qianggou.utils.Result;

@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {
	
		@Autowired
		private SeckillGoodsServiceImpl goodsService;
	
		@RequestMapping("/findAll")
		public List<Goods> findAll(){	 
			
			return  goodsService.findAll(); 
		}
		
		
		@RequestMapping("/findOne/{id}")
		public Goods findOne(@PathVariable("id") long id) { 
		 
			return goodsService.findOne(id);  
		}
		
		@RequestMapping("/saveOrder/{id}")
		public Result saveOrder(@PathVariable("id") long id) {
			
			String userId="huahua"; //假设登陆user
			
			return goodsService.saveOrder(id,userId);
		}
}
