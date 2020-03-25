package cn.huahua.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.huahua.bean.Employee;
import cn.huahua.bean.Msg;
import cn.huahua.service.EmployeeService;


/**
 * 处理员工的CRUD请求
 * @author 花花
 * 
 * URI:
 * emps/{id}  GET 查询员工
 * emps 	  POST 保存员工
 * emps/{id}  PUT 修改员工
 * emps/{id}  DELETE 删除员工
 *
 */

@Controller 
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService ;
	/*
	 * delete employee by id
	 * 原来：deleteEmpById
	 * 改造：deleteEmps
	 * 		单个与批量删除二合一
	 * 		批量删除：1-2-3....
	 * 		单个删除：1	
	 */
	@RequestMapping(value="emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmps(@PathVariable("ids") String ids) {
		//employeeService.delEmpById(id);
		if(ids.contains("-")) {
			//如果包含- 就批量删除
			String[] str =ids.split("-");
			List<Integer> list=new ArrayList<Integer>();
			//组装id的集合
			for(String s:str) {		
				System.out.println("--"+s);
				list.add(Integer.parseInt(s));
			}
			employeeService.delBatch(list);
		}else {
			//单个删除
			employeeService.delEmpById(Integer.parseInt(ids));
		}
		return Msg.success();
	}

 
	/*如果直接发送ajax=put的请求
	 * 封装的数据有问题
	 * 请求体中有数据但是对Employee对象封装不上
	 * 
	 * 原因：
	 * tomcat：
	 * 		  将请求体中的数据，封装一个map，
	 * 		 request.gerParameter("empname")将会从这个map中取值
	 * 		 SpringMVC封装POJO对象的时候会把POJO中
	 * 	会把每个属性的值拿到（request.gerParameter("empname")）
	 * Ajax发送put请求引发的血案：
	 * 		 PUT请求，请求体中的数据 ，request.gerParameter("empname")拿不到
	 * 		 Tomcat一看是PUT请求，就不会封装请求体中的数据为map，只有POST的请求形式才能封装请求体为map
	 * 		 org.apache.catalina.connector.Request ---parseParameters() ;
	 * 		
	 * 		只有判断为post请求，才会继续往下执行，封装
	 * 		if( !getConnector().isParseBodyMethod(getMethod()) ) {
                success = true;
                return;
            }
	 * 解决： web.xml 配置过滤器，过滤器把PUT请求也想post一样，把请求体封装起来。
	 * 		request.gerParameter被重写
	 * <filter>
		<filter-name>HttpPutFormContentFilter</filter-name>
		<filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
		</filter>
		<filter-mapping>
		<filter-name>HttpPutFormContentFilter</filter-name>
		<url-pattern>/*</url-pattern>
		</filter-mapping>
	 * 
	 * 更新员工
	 */
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	@ResponseBody
	public Msg updateEmp(Employee e) {
		System.out.println("id:"+e.getEmpId());
	 	employeeService.updateEmp(e);
		return Msg.success();
	}
	
	/* 查询员工通过id */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmployeeById(@PathVariable("id") Integer id) {
		Employee employee=employeeService.getEmp(id);
		
		return Msg.success().add("emp", employee);
	}
	
	
	/*
	 * 检测用户名是否可用
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Msg checkUser(String empName) {
		String rex="(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u4e00-\\u9fa5]{2,5}$)";
		if(!empName.matches(rex)) {
			return Msg.fail().add("va_msg", "用户名可以是2-5位中文或者6-12位英文与数字组合");
		}
 
		boolean b=employeeService.checkuser(empName);
 
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail();
		}
	}
	
	
	// 查询员工数据（分页查询） 
	@RequestMapping("emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn) {
		PageHelper.startPage(pn, 5);
		List<Employee> emps=employeeService.getAll();
		PageInfo page=new PageInfo(emps,5);
		return Msg.success().add("pageInfo",page);
	}
	/**
	 * save 员工数据 
	 * 1-支持JSR303校验
	 * 2-导入Hibernate-validator 包
	 * 3-@Valid 注解要校验的类
	 * 	 BindingResult result校验结果
	 *@return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee e ,BindingResult result) {
		if(result.hasErrors()) {
			//校验失败返回失败,在模态框中显示校验失败的错误信息
			Map<String,Object> map=new HashMap();
			List<FieldError>errors= result.getFieldErrors();
			for(FieldError error:errors) {
				System.out.println("错误的字段名"+error.getField());
				System.out.println("错误的message"+error.getDefaultMessage());
				map.put(error.getField(), error.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
		Msg msg=employeeService.saveEmp(e);
		System.out.println(">>>>");
		return msg.success() ; 
		}
	}

	/**
	 * 查询员工数据（分页查询） 
	 *@return
	 */
	//@RequestMapping("emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,
			Model model) {
		//引入pageHelper分页插件
		//在查询之前只需要调用 传入页码 以及每页大小
		PageHelper.startPage(pn, 5);
		//紧跟着的第一个select方法会被分页
		List<Employee> emps=employeeService.getAll();
		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页码就行了
		//pageInfo封装了详细的分页信息，还包括有我们查询出来的数据
		PageInfo page=new PageInfo(emps,5); //连续显示的页数是：五页 就是下面显示几页几页的那种
		model.addAttribute("pageInfo",page);
		return"list";
	}
}
