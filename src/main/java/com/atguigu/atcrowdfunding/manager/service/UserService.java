package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.common.bean.User;


public interface UserService {

	//ocp  增强方法的扩展性， 集合作为参数可以动态扩容， 面向接口编程
	List<User> queryUserList(Map<String, Object> paramMap);

	User queryUser(Integer id);

	User queryUser4Login(User user);

	int queryUserCount(Map<String, Object> paramMap);
	
	//不是ajax的分页查询
	List<User> queryPageUsers(Map<String, Object> paramMap);

	int queryPageCount(Map<String, Object> paramMap);

	void insertUser(User user);

	int updateUser(User user);

	int deleteUserById(Integer id);

	int deleteUserByIds(Datas ds);

	List<Integer> queryAssignRoleidsByUserid(Integer id);
	  //直接增加记录
	void insertUserRoles(Map<String, Object> paramMap);

	void deleteUserRoles(Map<String, Object> paramMap);

	List<Permission> queryPermissionByUser(User dbUser);

}
