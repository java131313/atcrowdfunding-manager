package com.atguigu.atcrowdfunding.manager.dao;

import java.util.List;

import com.atguigu.atcrowdfunding.common.bean.Permission;

public interface PermissionDao {
	Permission queryRootPermission();

	List<Permission> queryChildPermissions(Integer id);

	List<Permission> queryAll();

	void insertPermission(Permission permission);

	Permission queryPermissionById(Integer id);

	int updatePermission(Permission permission);

	int deletePermissionById(Integer id);

	List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
