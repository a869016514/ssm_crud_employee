package cn.huahua.test;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.huahua.bean.Department;
import cn.huahua.bean.Employee;
import cn.huahua.dao.DepartmentMapper;
import cn.huahua.dao.EmployeeMapper;
 

/**
 * @author 花花
 *测试dao层的工作
 *推荐spring项目久可以使用spring的单元测试，可以自动注入我们需要的组件
 *	1导入springtest的模块
 *	2@ContextConfiguration指定spring配置文件的位置
 *	3直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	//测试department
	@Test
	public void testCRUD() {
		//1.创建ioc容器
		//ApplicationContext ioc =new ClassPathXmlApplicationContext("applicationContext.xml");
		//2、从容器中获取mapper
		//DepartmentMapper bean=ioc.getBean(DepartmentMapper.class);
		
		System.out.println(departmentMapper);
		System.out.println(employeeMapper);
		//1) 插入几个部门
		
		/*
		 * Department dept1=new Department(null,"开发部"); Department dept2=new
		 * Department(null,"测试部");
		 */
		/*
		 * departmentMapper.insertSelective(dept1);
		 * departmentMapper.insertSelective(dept2);
		 */
		
		//2)员工插入
		//Employee emp=new Employee(1,"huahua","m","869016514@qq.com",1);
		//Employee emp2=new Employee(2,"huahua2","f","872707774@qq.com",2);
		//employeeMapper.insertSelective(emp);
		
		//3)批量插入员工  使用可以执行批量操作的sqlsession
		
//		EmployeeMapper mapper =sqlSession.getMapper(EmployeeMapper.class);
//		for (int i = 0; i < 800; i++) {
//			int dId;
//			if (i % 2 == 0) {
//				dId = 1;
//			} else {
//				dId = 2;
//			}
//			String uid = (String) UUID.randomUUID().toString().subSequence(0, 5) + i;
//			mapper.insertSelective(new Employee(i, uid, "m", uid + "@huahua.com", dId));
//		}
		List<Employee> l=employeeMapper.selectByExampleWithDept(null);
		System.out.println("Employee :"+l.get(1));
	}
	 

}
