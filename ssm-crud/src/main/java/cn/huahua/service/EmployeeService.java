package cn.huahua.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import cn.huahua.bean.Employee;
import cn.huahua.bean.EmployeeExample;
import cn.huahua.bean.EmployeeExample.Criteria;
import cn.huahua.bean.Msg;
import cn.huahua.dao.EmployeeMapper;
@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper ;
	
	
	/**
	 * 查询所有员工数据
	 * @return
	 */
	public List<Employee> getAll(){
	 return employeeMapper.selectByExampleWithDept(null);
	}


	public Msg saveEmp(Employee e) {
		 employeeMapper.insertSelective(e);
		 return Msg.success();
	}

	/*@return true 可用，false不可用
	 * 
	 * 检验用户名是否可用
	 */
	public boolean checkuser(@RequestParam("empName") String empName) { 
		
		EmployeeExample example =new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		
		long count =employeeMapper.countByExample(example);
		
		return count==0;
	}

	/*
	 * select Employee by Id
	 */
	public Employee getEmp(Integer id) {
 
		Employee e=employeeMapper.selectByPrimaryKeyWithDept(id);
		return e;
	}

	/*
	 * update employee
	 */
	public void updateEmp(Employee e) {
		employeeMapper.updateByPrimaryKeySelective(e);
		
	}

	/*
	 * delete employee by id
	 */
	public void delEmpById(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}


	public void delBatch(List<Integer> ids) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
		
	}
}
