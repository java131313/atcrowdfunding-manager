package com.atguigu.atcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.common.BaseController;
import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
//@Controller("PermissionController2")
//@RequestMapping("/manager/permission")
public class PermissionController2 extends BaseController{
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/index")
	public String index() {
		return "manager/permission/index";
	}
	@RequestMapping("/index2")
	public String index2() {
		return "manager/permission/index";
	}
	//返回数据，我需要什么数据。我生成什么样的数据  []  list => []   map => {} user => {}
	@ResponseBody
	@RequestMapping("/loadTreeDatas")
	public Object loadTreeDatas() {
		start();
		
		try {
			// map ==> {}
			// ArrayList ==> []
			List<Permission> permissions = new ArrayList<Permission>();
			
			/*
			// 父节点
			Permission permission = new Permission();
			permission.setName("parentPermission");
			// 子节点
			Permission permission1 = new Permission();
			permission1.setName("childPermission");
			
			// 组合父子节点
			permission.getChildren().add(permission1);
			
			permissions.add(permission);
			*/
			
			/*
			// 查询根节点(父)
			Permission root = permissionService.queryRootPermission();
			
			// 查询子节点（子）-- 控制面板, 权限管理
			List<Permission> childPermissions = permissionService.queryChildPermissions(root.getId());
			
			for ( Permission permission : childPermissions ) {
				Permission parent = permission;
				// 用户维护, 角色维护, 许可维护
				List<Permission> childChildPermissions = permissionService.queryChildPermissions(parent.getId());
				
				for ( Permission childPermission : childChildPermissions ) {
					Permission childParent = childPermission;
					List<Permission> childChildChildPermissions = permissionService.queryChildPermissions(childParent.getId());
					childParent.setChildren(childChildChildPermissions);
				}
				
				parent.setChildren(childChildPermissions);
			}
			
			// 组合父子节点的关系
			root.setChildren(childPermissions);
			*/
			
			/*
			// 采用递归的方式实现许可数据的查询
			// 1）递归需要方法体中不断调用自身
			// 2）递归逻辑中应该有跳出的逻辑
			// 3）递归调用时传递的参数之间应该有规律
			Permission permission = new Permission();
			permission.setId(0);
			queryChildPermission(permission);
			data(permission.getChildren());
			*/
			
			// 查询一次整合所有许可的关系
			List<Permission> allPermissions = permissionService.queryAll();
			// 父节点，子节点 （ 子==>父 ）
			/* 使用双重for查询许可数据
			for ( Permission permission : allPermissions ) {
				// 子节点
				Permission childPermission = permission;
				// 父节点
				if ( permission.getPid() == 0 ) {
					Permission root = permission;
					permissions.add(root);
				} else {
					for ( Permission innerPermission : allPermissions ) {
						if ( innerPermission.getId() == childPermission.getPid() ) {
							Permission parentPermission = innerPermission;
							// 组合父子节点的关系
							parentPermission.getChildren().add(childPermission);
							break;
						}
					}
				}
			}
			*/
			// id ==> Permission ==> Map
			// 使用Map集合整合父子节点的关系
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			for ( Permission permission : allPermissions ) {
				permissionMap.put(permission.getId(), permission);
			}
			
			for ( Permission permission : allPermissions ) {
				// 子节点
				Permission childPermission = permission;
				if ( permission.getPid() == 0 ) {
					Permission root = permission;
					permissions.add(root);
				} else {
					// 父节点
					Permission parentPermission = permissionMap.get(childPermission.getPid());
					// 组合父子节点关系
					parentPermission.getChildren().add(childPermission);
				}
			}
			
			data(permissions);
			success();
		} catch ( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	/**
	 * 查询父节点中的子节点数据
	 * @param parent
	 */
	private void queryChildPermission(Permission parent) {
		// 查询子节点
		List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());
		
		for ( Permission permission : childPermissions ) {
			Permission parentPermission = permission;
			queryChildPermission(parentPermission);
		}
		
		// 组合父子节点的关系
		parent.setChildren(childPermissions);
	}
}
