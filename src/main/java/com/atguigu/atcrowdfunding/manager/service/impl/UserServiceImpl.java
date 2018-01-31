package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.common.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserDao;
import com.atguigu.atcrowdfunding.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	

	public User queryUser(Integer id) {
		return userDao.queryUser(id);
	}


	public List<User> queryUserList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return userDao.queryUserList(paramMap);
	}


	@Override
	public User queryUser4Login(User user) {
		return userDao.queryUser4Login(user);
	}

    //7：查询用户信息
	@Override
	public int queryUserCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return  userDao.queryUserCount(paramMap);
	}

   /**
    * 分页查询用户信息  
    */
	@Override
	public List<User> queryPageUsers(Map<String, Object> paramMap) {
		
		return userDao.queryPageUsers(paramMap);
	}
	/**
	 * 查询用户表的总记录数
	 */

	@Override
	public int queryPageCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return userDao.queryPageCount(paramMap);
	}

	public void insertUser(User user) {
		userDao.insertUser(user);
	}
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}
	public int deleteUserById(Integer id) {
		return userDao.deleteUserById(id);
	}
	public int deleteUserByIds(Datas ds) {
		return userDao.deleteUserByIds(ds);
	}


	@Override
	public List<Integer> queryAssignRoleidsByUserid(Integer id) {
		// TODO Auto-generated method stub
		return userDao.queryAssignRoleidsByUserid(id);
	}


	@Override
	public void insertUserRoles(Map<String, Object> paramMap) {
		userDao.insertUserRoles(paramMap);
		
	}


	@Override
	public void deleteUserRoles(Map<String, Object> paramMap) {
		userDao.deleteUserRoles(paramMap);
		
	}


	@Override
	public List<Permission> queryPermissionByUser(User dbUser) {
		// TODO Auto-generated method stub
		return userDao.queryPermissionByUser(dbUser);
	}
	
}
