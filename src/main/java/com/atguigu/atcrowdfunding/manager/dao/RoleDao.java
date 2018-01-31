package com.atguigu.atcrowdfunding.manager.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Role;

public interface RoleDao {
	//查询角色
	Role queryRole(Map<String, Object> paramMap);
//查询角色列表
	List<Role> pageQuery(Map<String, Object> paramMap);
//查询角色的数量
	int queryCount(Map<String, Object> paramMap);
//插入角色
	void insertRole(Role role);
//根据id查询角色
	Role queryById(Integer id);
//更新角色
	int updateRole(Role role);
//根据id删除角色
	int deleteRole(Integer id);
//删除多个角色
	int deleteRoles(Datas ds);
//查询所以的角色
	List<Role> queryAll();
	int insertRolePermissions(Map<String, Object> paramMap);
	void deleteRolePermissions(Map<String, Object> paramMap);
}
