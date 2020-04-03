package qianggou2;
 
  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import qianggou.bean.Goods;
import qianggou.bean.GoodsExample;
import qianggou.mapper.GoodsMapper;
import qianggou.utils.SystemConts;

@Component
public class KillGoodsToResdisTask {
		//@Scheduled(cron = "0/10 * * * * ?")
		//cron 注意空格。  
		//如果没有空格 即 cron 写错了 会报错：
		/*
		 * Caused by: org.springframework.beans.factory.BeanCreationException: Error
		 * creating bean with name 'killGoodsToResdisTask' defined in file
		 * [D:\SSM\killThread\target\test-classes\qianggou2\KillGoodsToResdisTask.class]:
		 * Initialization of bean failed; nested exception is
		 * java.lang.IllegalStateException: Encountered invalid @Scheduled method
		 * 'importToReids': Cron expression must consist of 6 fields (found 1 in
		 * "0/2****?")
		 * 
		 * Caused by: java.lang.IllegalStateException: Encountered invalid @Scheduled
		 * method 'importToReids': Cron expression must consist of 6 fields (found 1 in
		 * "0/2****?")
		 * 
		 */
	/*
	 * @Scheduled(cron = "0/10 * * * * ?") public void excTask(){
	 * System.out.println("定时任务执行，执行时间是："+new Date()); }
	 */
	
		@Autowired
		private GoodsMapper goodsMapper; 
		@Autowired
		private RedisTemplate redisTemplate; 
 
		@Scheduled(cron = "0/10 * * * * ?")
		public void importToRedis() { 
			//1-查询合法的秒杀商品数据：状态为有效（status=1） ，库存量>0(starkCount>0),秒杀开始时间  <=当前时间<秒杀结束时间
			GoodsExample example=new GoodsExample();	
			GoodsExample.Criteria criteria= example.createCriteria();
			java.sql.Date date= new java.sql.Date(new Date().getTime());
			criteria.andStatusEqualTo("1")
			.andStockCountGreaterThan(0)
			.andStartTimeLessThanOrEqualTo(date)
			.andEndTimeGreaterThan(date);
			List<Goods> goods=goodsMapper.selectByExample(example);
			System.out.println("goods:"+goods);
			//2-讲数据存入reids 
			for(Goods good:goods) { 
				redisTemplate.boundHashOps(Goods.class.getSimpleName()).put(good.getId(), good);
				//为每个商品创建一个队列，队列中存放库存量相同数量的商品ID
				createQueue(good.getId(),good.getStockCount());
			}
		}
		
		private void createQueue(long id, Integer stockCount) {
			if(stockCount >0) {
				for(int i=0;i<stockCount;i++) {
					redisTemplate.boundListOps(SystemConts.CONST_SECKILLGOODS_ID_PRBFIX+id).leftPush(id);
				}
			}
		}
}
