package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.RoleDao;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	@Override
	public Role queryRole(Map<String, Object> paramMap) {
		
		return roleDao.queryRole(paramMap);
	}

	@Override
	public List<Role> pageQuery(Map<String, Object> paramMap) {
		
		return roleDao.pageQuery(paramMap);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return roleDao.queryCount(paramMap);
	}

	@Override
	public void insertRole(Role role) {
		roleDao.insertRole(role);

	}

	@Override
	public Role queryById(Integer id) {
		return roleDao.queryById(id);
	}

	@Override
	public int updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	@Override
	public int deleteRole(Integer id) {
		return roleDao.deleteRole(id);
	}

	@Override
	public int deleteRoles(Datas ds) {
		return roleDao.deleteRoles(ds);
	}

	@Override
	public List<Role> queryAll() {

		return roleDao.queryAll();
	}


	public int insertRolePermissions(Map<String, Object> paramMap) {
		roleDao.deleteRolePermissions(paramMap);
		return roleDao.insertRolePermissions(paramMap);
	}

}
