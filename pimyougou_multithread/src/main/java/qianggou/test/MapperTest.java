package qianggou.test;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import qianggou.bean.Goods;
import qianggou.bean.GoodsExample;
import qianggou.mapper.GoodsMapper;
import qianggou.mapper.OrderMapper;

 

 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-service.xml"})
public class MapperTest {
	 @Autowired
	 GoodsMapper goodsMapper;
	 @Autowired
	 OrderMapper orderMapper;
	//@Autowired
	SqlSession sqlSession;
	//测试department
	@Test
	public void testCRUD() {
		long id=1;
		GoodsExample example=new GoodsExample();
		java.sql.Date date= new java.sql.Date(new Date().getTime());
		GoodsExample.Criteria criteria= example.createCriteria();
		criteria.andStatusEqualTo("1").andStockCountGreaterThan(0).andStartTimeLessThanOrEqualTo(date).andEndTimeGreaterThan(date);
		List<Goods> goods=goodsMapper.selectByExample(example);
		Goods g=goodsMapper.selectByPrimaryKey(id);
		System.out.println("goods:"+goods);
		System.out.println(g.getTitle());
	}
}
