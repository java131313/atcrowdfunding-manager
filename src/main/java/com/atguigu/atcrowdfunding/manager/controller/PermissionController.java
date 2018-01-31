package com.atguigu.atcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.common.BaseController;
import com.atguigu.atcrowdfunding.common.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
@Controller
@RequestMapping("/manager/permission")
public class PermissionController extends BaseController{
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
	
	@RequestMapping("/add")
	public String add() {
		return "manager/permission/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		Permission permission = permissionService.queryPermissionById(id);
		model.addAttribute("permission", permission);
		return "manager/permission/edit";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	// ajax    Object   responseBody
	public Object insert( Permission permission ) {
		start();
		
		try {
			permissionService.insertPermission(permission);
			success();
		} catch ( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( Permission permission ) {
		start();
		
		try {
			int cnt = permissionService.updatePermission(permission);
			success(cnt == 1);
		} catch ( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		start();
		
		try {
			int cnt = permissionService.deletePermissionById(id);
			success(cnt == 1);
		} catch ( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	
	@ResponseBody
	@RequestMapping("/loadAssignTreeDatas")
	public Object loadAssignTreeDatas( Integer roleid ) {
		List<Permission> permissions = new ArrayList<Permission>();
		
	
		
		List<Permission> allPermissions = permissionService.queryAll();
		
		
		// 查询当前角色分配的许可信息  1月29日
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
				
		for ( Permission permission : allPermissions ) {
			if ( permissionids.contains(permission.getId()) ) {
				permission.setChecked(true);
			} else {
				permission.setChecked(false);
			}
			permissionMap.put(permission.getId(), permission);
		}
		
		for ( Permission permission : allPermissions ) {
			// 子节点
			Permission childPermission = permission;
			if ( childPermission.getPid() == 0 ) {
			
				permissions.add(permission);
			} else {
				// 父节点
				Permission parent = permissionMap.get(childPermission.getPid());
				// 组合父子菜单的关系
				parent.getChildren().add(childPermission);
			}
		}
		
		return permissions;
	}
	
	@ResponseBody
	@RequestMapping("/loadAsyncTreeDatas")
	public Object loadAsyncTreeDatas() {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> allPermissions = permissionService.queryAll();
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for ( Permission permission : allPermissions ) {
			permissionMap.put(permission.getId(), permission);
		}
		
		for ( Permission permission : allPermissions ) {
			// 子节点
			Permission childPermission = permission;
			if ( new Integer(0).equals(permission.getPid())) {
				Permission root = permission;
				permissions.add(root);
			} else {
				// 父节点
				Permission parentPermission = permissionMap.get(childPermission.getPid());
				// 组合父子节点关系
				parentPermission.getChildren().add(childPermission);
			}
		}
		
		return permissions;
	}
	//返回数据，我需要什么数据。我生成什么样的数据
	@ResponseBody
	@RequestMapping("/loadTreeDatas")
	public Object loadTreeDatas() {
		start();
		
		try {
			// map ==> {}
			// ArrayList ==> []
			List<Permission> permissions = new ArrayList<Permission>();
			
			// 查询一次整合所有许可的关系
			List<Permission> allPermissions = permissionService.queryAll();
			
			// id ==> Permission ==> Map
			// 使用Map集合整合父子节点的关系
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			for ( Permission permission : allPermissions ) {
				permissionMap.put(permission.getId(), permission);
			}
			
			for ( Permission permission : allPermissions ) {
				// 子节点,可能为null
				Permission childPermission = permission;
				//可能为null permission.getPid为null
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
