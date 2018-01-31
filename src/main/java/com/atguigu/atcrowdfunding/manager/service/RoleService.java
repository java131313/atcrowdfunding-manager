package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Role;

public interface RoleService {
	public Role queryRole(Map<String, Object> paramMap);

	public List<Role> pageQuery(Map<String, Object> paramMap);

	public int queryCount(Map<String, Object> paramMap);

	public void insertRole(Role role);

	public Role queryById(Integer id);

	public int updateRole(Role role);

	public int deleteRole(Integer id);

	public int deleteRoles(Datas ds);

	public List<Role> queryAll();

	public int insertRolePermissions(Map<String, Object> paramMap);
}
