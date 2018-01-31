package com.atguigu.atcrowdfunding.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.common.bean.User;
import com.atguigu.atcrowdfunding.common.util.ConfigUtil;

public interface UserDao {

	@Select("select * from t_user where id = #{id}")
	User queryUser(Integer id);
	
	@Select("select * from t_user where loginacct = #{loginacct} and userpswd = #{userpswd}")
	User queryUser4Login(User user);

	int queryUserCount(Map<String, Object> paramMap);
	
	List<User> queryUserList(Map<String, Object> paramMap);
	
	List<User> queryPageUsers(Map<String, Object> paramMap);
//8：查询用户信息
	int queryPageCount(Map<String, Object> paramMap);
//9：返回查询结果
	

	

	

	void insertUser(User user);

	int updateUser(User user);

	int deleteUserById(Integer id);

	int deleteUserByIds(Datas ds);
	
	
	public static void main(String[] args) {
		String conf = ConfigUtil.getValue("DEFAULT_PASSWORD");
		System.out.println(conf);
	}
//mapper中是userid  因为是简单类型
	List<Integer> queryAssignRoleidsByUserid(Integer id);
//拷贝方法的名字到mapper中
	void insertUserRoles(Map<String, Object> paramMap);

	void deleteUserRoles(Map<String, Object> paramMap);

	List<Permission> queryPermissionByUser(User dbUser);

}
