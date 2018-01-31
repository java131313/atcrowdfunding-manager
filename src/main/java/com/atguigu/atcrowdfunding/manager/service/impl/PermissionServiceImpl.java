package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionDao;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	@Override
	public Permission queryRootPermission() {
		// TODO Auto-generated method stub
		return permissionDao.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermissions(Integer id) {
		// TODO Auto-generated method stub
		return permissionDao.queryChildPermissions(id);
	}

	@Override
	public List<Permission> queryAll() {
		// TODO Auto-generated method stub
		return permissionDao.queryAll();
	}

	
	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}

	public Permission queryPermissionById(Integer id) {
		return permissionDao.queryPermissionById(id);
	}

	public int updatePermission(Permission permission) {
		return permissionDao.updatePermission(permission);
	}

	public int deletePermissionById(Integer id) {
		return permissionDao.deletePermissionById(id);
	}

	public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
		return permissionDao.queryPermissionidsByRoleid(roleid);
	}

}
