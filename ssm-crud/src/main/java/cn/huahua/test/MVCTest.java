package cn.huahua.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;

import cn.huahua.bean.Employee;

/**
  * 使用spring测试模块提供的测试请求功能，测试crud的准确性
 * @author 花花
 *
 */


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MVCTest {
	//传入Springmvc的ioc
	@Autowired
	WebApplicationContext context;
	//虚拟mvc请求，获取到处理结果
	MockMvc mockMvc;
	@Before
	public void initMokcMvc() {
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
		System.out.println("init");
	}
	@Test
	public void testPage() throws Exception { 
		//模拟请求拿到返回值
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();	
		
		//请求成功以后 请求域中会有pageInfo，取出它进行验证
		MockHttpServletRequest request=  result.getRequest();
		System.out.println(request.getCharacterEncoding());		 
		PageInfo pi=(PageInfo)request.getAttribute("pageInfo");	
		System.out.println(pi);
		System.out.println("当前页码："+pi.getPageNum());
		System.out.println("总页码："+pi.getPages());
		System.out.println("总记录数："+pi.getTotal());
		System.out.println("页码需要连续显示的页码：");
		int[] nums=pi.getNavigatepageNums();
		for(int i:nums) {
			System.out.println(" "+i);
		}
		//获取员工数据
		List<Employee> list=pi.getList();
		for(Employee e:list) {
			System.out.println("ID "+e.getEmpId()+"-----Name: "+e.getEmpName());
		}
	}
	
}
