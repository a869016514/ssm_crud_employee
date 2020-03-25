package cn.huahua.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huahua.bean.Department;
import cn.huahua.dao.DepartmentMapper;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	
	public	List<Department> getDepts() {
		return departmentMapper.selectByExample(null);
	}
}
