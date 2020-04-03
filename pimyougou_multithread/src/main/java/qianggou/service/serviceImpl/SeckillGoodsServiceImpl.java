package qianggou.service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import org.quartz.spi.ThreadExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qianggou.bean.Goods;
import qianggou.bean.Order;
import qianggou.mapper.GoodsMapper;
import qianggou.service.SeckillGoodsService;
import qianggou.thread.OrderCreateThread;
import qianggou.utils.IdWorker;
import qianggou.utils.OrderRecord;
import qianggou.utils.Result;
import qianggou.utils.SystemConts;


@Service
@Transactional
public class SeckillGoodsServiceImpl implements SeckillGoodsService{
	//不用持久层了 用redis
	@Autowired
	private RedisTemplate redisTemplate;
	//自动ID生成
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private  Executor  executor;
	@Autowired
	private OrderCreateThread orderCreateThread;
	
	@Override
	public List<Goods> findAll() {  
		return redisTemplate.boundHashOps(Goods.class.getSimpleName()).values();
	}


	public Goods findOne(long id) {

		return(Goods)redisTemplate.boundHashOps(Goods.class.getSimpleName()).get(id); 
	}


	public Result saveOrder(long id, String userId) { 
		//0-从用户的set集合中判断用户是否已经下单 
		boolean member=redisTemplate.boundSetOps(SystemConts.CONST_USER_ID_PRBFIX+id).isMember(userId);
		if(member) {
			//如果正在排队或者已经支付，提示用户你正在排队或有订单未支付
			return new Result(true,"sorry，排队中，请支付");
		}
		
   //  --- 并发处理   1- 从队列中获取秒杀商品
		Long gId=(Long)redisTemplate.boundListOps(SystemConts.CONST_SECKILLGOODS_ID_PRBFIX+id).rightPop();

		//2- 判断商品是否存在 或 库存是否《=0
		if(gId==null ) {
			//3- 商品不存在 或库存《=0 返回失败 提示已售罄
			return new Result(false,"商品已售罄");
		}
		//4-将用户放入用户集合
		redisTemplate.boundSetOps(SystemConts.CONST_USER_ID_PRBFIX+id).add(userId);
		//5-创建orderRecord对象记录用户下单信息：用户 id  商品id 放到 orderRecord队列中
		OrderRecord orderRecord =new OrderRecord(id, userId);
		redisTemplate.boundListOps(OrderRecord.class.getSimpleName()).leftPush(orderRecord);
		//6-通过线程池启动线程处理orderRecord中的数据，返回成功
		executor.execute(orderCreateThread);
		return new Result(true,"秒杀成功，请支付");
	}
		
}
