package qianggou.thread;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import qianggou.bean.Goods;
import qianggou.bean.Order;
import qianggou.mapper.GoodsMapper;
import qianggou.utils.IdWorker;
import qianggou.utils.OrderRecord;

@Component
public class OrderCreateThread implements Runnable {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public void run() {
		//0从队列拿 userID 和 id
		OrderRecord orderRecord =(OrderRecord)redisTemplate.boundListOps(OrderRecord.class.getSimpleName()).rightPop();
		if(orderRecord!=null) {
			//1- 从redis获取秒杀商品
			Goods good=(Goods)redisTemplate.boundHashOps(Goods.class.getSimpleName()).get(orderRecord.getId()); 
			//4- 生成秒杀订单 将订单保存到redis缓存
			Order order=new Order();
			order.setUserId(orderRecord.getUserId());
			order.setSellerId(good.getSellerId());
			order.setSeckillId(idWorker.nextId());
			order.setMoney(good.getCostPrice());
			order.setCreateTime(new Date());
			order.setStatus("0");//未支付	
			redisTemplate.boundHashOps(Order.class.getSimpleName()).put(orderRecord.getUserId(), order);
			//5-秒杀商品库存量-1
			synchronized (OrderCreateThread.class) {
				good=(Goods)redisTemplate.boundHashOps(Goods.class.getSimpleName()).get(orderRecord.getId()); 
				good.setStockCount(good.getStockCount()-1);
				//6-判断库存量是否《=0
				if(good.getStockCount()<=0) {
					// -- 是 将秒杀商品更新到数据库，删除秒杀商品
					goodsMapper.updateByPrimaryKey(good);
					redisTemplate.boundHashOps(Goods.class.getSimpleName()).delete(orderRecord.getId());
				}else {
					// --- 否 将秒杀商品更新到缓存。返回成功
					redisTemplate.boundHashOps(Goods.class.getSimpleName()).put(orderRecord.getId(), good);
				}
			}

			
		}
		
		
	}
}
