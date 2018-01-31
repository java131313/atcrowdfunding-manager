package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;

import com.atguigu.atcrowdfunding.common.bean.Permission;

public interface PermissionService {
	Permission queryRootPermission();

	List<Permission> queryChildPermissions(Integer id);

	List<Permission> queryAll();

	Permission queryPermissionById(Integer id);

	void insertPermission(Permission permission);

	int updatePermission(Permission permission);

	int deletePermissionById(Integer id);

	List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
