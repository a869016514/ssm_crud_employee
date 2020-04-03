package qianggou2;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-*.xml")
public class KillGoodsToResdisTaskTest {
	
	@Test
	public void testTask() {
		while(true) {
			try {
				System.in.read();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
	}
}
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
 * java.lang.IllegalStateException: Failed to load ApplicationContext
	 
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'killGoodsToResdisTask': Unsatisfied dependency expressed through field 'goodsMapper'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'qianggou2.dao.GoodsMapper' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'qianggou2.dao.GoodsMapper' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 
默认@Autowired是按类型type自动注入
默认@Resource是按名字byName自动注入，也可以指定type注入
@Autowired是Spring提供的注解
@Resource是J2EE提供的注解



java.lang.IllegalStateException: Failed to load ApplicationContext
 
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'killGoodsToResdisTask': Unsatisfied dependency expressed through field 'goodsMapper'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'qianggou.mapper.GoodsMapper' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'qianggou.mapper.GoodsMapper' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 


 * */


