package com.atguigu.atcrowdfunding.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.common.AJAXResult;
import com.atguigu.atcrowdfunding.common.BaseController;
import com.atguigu.atcrowdfunding.common.Datas;
import com.atguigu.atcrowdfunding.common.bean.Page;
import com.atguigu.atcrowdfunding.common.bean.Role;
import com.atguigu.atcrowdfunding.common.bean.User;
import com.atguigu.atcrowdfunding.common.util.ConfigUtil;
import com.atguigu.atcrowdfunding.common.util.MD5Util;
import com.atguigu.atcrowdfunding.common.util.StringUtil;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
@Controller
/**
 * 
 * @author kxy
 * <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
 *@RequestMapping("/manager/user")+@RequestMapping("/index") 是相对于  url 地址的
 *   
 */
@RequestMapping("/manager/user")
public class UserController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private  UserService userService;
	
	@Autowired
	private RoleService roleService;
	/**
	 * 2:跳转页面的jsp
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
	
		return "/manager/user/index";
	}
	
	//分配权限
	@RequestMapping("/assign")
	public String assign(Integer id,Model model) {
		User user = userService.queryUser(id);
		model.addAttribute("user", user);
		// 为了展示到select列表中，查询所有的角色信息
		List<Role> roles = roleService.queryAll();
		//用于区别是否把某个角色是否分配		
		List<Role> assignRoleList = new ArrayList<Role>();
		List<Role> unassignRoleList = new ArrayList<Role>();
				// 查询当前用户已经分配的角色ID，  看关系表
		List<Integer> ids = userService.queryAssignRoleidsByUserid(id);
				
				for ( Role role : roles ) {
					//将要分配的id和已经赋权的id是否包含， 如果包含，表示已经分配， 如果不包含，则未分配
					if ( ids.contains(role.getId()) ) {
						assignRoleList.add(role);
					} else {
						unassignRoleList.add(role);
					}
				}
				//同步查询， 先查询，再跳转页面。 查询的慢， 页面展示也慢，数据量大的时候不好，因为角色没多少数据
				
				model.addAttribute("assignRoleList", assignRoleList);
				model.addAttribute("unassignRoleList", unassignRoleList);
				
		return "/manager/user/assign";
	}
	/**
	 * 接收数据
	 * @param userid   用户ID
	 * 
	 * @param ds     包装类
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignRole")
	public Object assignRole( Integer userid, Datas ds ) {
		//ajax的处理结果
	    start();
		try {
			//为了扩展性用map
			Map<String,Object> paramMap = new HashMap<String,Object>();
			//把页面的请求参数封装的map中
			paramMap.put("userid", userid);
			//表示多个角色的id
			paramMap.put("roleids", ds.getIds());
			//调用service方法
			userService.insertUserRoles(paramMap);
			success();
		}catch( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		/*try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", userid);
			paramMap.put("roleids", ds.getIds());
			userService.insertUserRoles(paramMap);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}*/
		
		return end();
	}
	//取消赋权
	@ResponseBody
	@RequestMapping("/unassignRole")
	public Object unassignRole( Integer userid, Datas ds ) {
		  start();
		
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", userid);
			paramMap.put("roleids", ds.getIds());
			userService.deleteUserRoles(paramMap);
			success();
		} catch ( Exception e ) {
			e.printStackTrace();
			fail();
		}
		
		return end();
	}
	
	@RequestMapping("/index2")
	public String index2() {
		return "/manager/user/index2";
	}
	/**
	 * 开发原则  open  close    ocp 配置文件增加扩展性
	 * @param pageno  第几页
	 * @param pagesize   每页大小
	 * @param queryText  查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery( Integer pageno, Integer pagesize, String queryText ) {
		AJAXResult result = new AJAXResult();
		
		// 分页查询用户信息
		// 将参数封装为集合   5：获取传递的参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// limit start, size
		paramMap.put("start", (pageno-1)*pagesize);
		paramMap.put("size", pagesize);
		
		
		if ( !StringUtil.isEmpty(queryText) ) {
			// % ==> \\%
			if ( queryText.indexOf("\\") != -1 ) {
				queryText = queryText.replaceAll("\\\\", "\\\\\\\\");	
			}
			if ( queryText.indexOf("_") != -1 ) {
				queryText = queryText.replaceAll("_", "\\\\_");	
			}
			if ( queryText.indexOf("%") != -1 ) {
				queryText = queryText.replaceAll("%", "\\\\%");	
			}
			
			paramMap.put("queryText", queryText);
		}
		//6：查询用户信息
		// 查询用户信息, 返回查询结果
				// 模糊查询时，需要在SQL中使用参数拼串的方式进行查询
				// 采用MySQL特定的语法 concat(str1, str2, str3);
		
		//10：返回查询结果
		List<User> users = userService.queryUserList(paramMap);
				
				// 对查询结果进行封装
				// 总数据条数
		//11：将查询结果进行封装
				int totalsize = userService.queryUserCount(paramMap);
				
				// 总页码
				int totalno = 0;
				if ( totalsize % pagesize == 0 ) {
					totalno = totalsize / pagesize;
				} else {
					totalno = totalsize / pagesize + 1;
				}
				
				Page<User> userPage = new Page<User>();
				userPage.setDatas(users);
				userPage.setTotalsize(totalsize);
				userPage.setTotalno(totalno);
				userPage.setPageno(pageno);
				userPage.setPagesize(pagesize);
	
				result.setData(userPage);
				//12：将结果转换为JSON字符串传递给用户
		return result;
		
	}
	
	/**
	 * 开发原则  open  close    ocp 配置文件增加扩展性
	 * @param pageno  第几页
	 * @param pagesize   每页大小
	 * @param queryText  查询条件
	 * @return
	 * // 1) 点击菜单，跳转页面
    	// 2) 调用服务，查询数据
    	// 3) Service调用DAO，查询数据（limit start, size）
    	// 4) 返回查询结果 DAO==>Service==>Controller
    	// 5) 保存查询结果（request,）
    	// 6) 跳转页面
	 */

	@RequestMapping("/userPage")
	public String pageQueryNoAjax( @RequestParam(value="pageno",defaultValue="1",required=false)Integer pageno,  @RequestParam(value="pagesize", required=false, defaultValue="2")Integer pagesize, String queryText,Model model ) {
		int start = (pageno -1)*pagesize;
		//封装请求参数  传递Map 易于扩展  ocp
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("start", start);
		paramMap.put("size", pagesize);
		//调用服务层查询对象
		
		List<User> users = userService.queryPageUsers(paramMap);
		int totalsize = userService.queryPageCount(paramMap);
    	
		Page<User> userPage = new Page<User>();
	    	userPage.setPageno(pageno);
	    	userPage.setPagesize(pagesize);
	    	userPage.setDatas(users);
	    	userPage.setTotalsize(totalsize);
    	
	    	model.addAttribute("userPage", userPage);
		
		return index2();
		
	}

	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "manager/user/add";
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		User user = userService.queryUser(id);
		model.addAttribute("user", user);
		
		return "manager/user/edit";
	}
	
	/**
	 * 跳转到批量新增页面
	 * @return
	 */
	@RequestMapping("/addBatch")
	public String addBatch() {
		return "manager/user/addBatch";
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Datas ds) {
		AJAXResult result = new AJAXResult();
		
		try {
			int count = userService.deleteUserByIds(ds);
			result.setSuccess(count == ds.getUsers().size());
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		
		try {
			int count = userService.deleteUserById(id);
			result.setSuccess(count == 1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( User user ) {
		AJAXResult result = new AJAXResult();
		
		try {
			int count = userService.updateUser(user);
			result.setSuccess(count == 1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	/**
	 * 批量新增用户信息
	 * 
	 * 1) SpringMVC获取表单中相同名称的多条数据时，可以采用数组的方式接收
	 * 2) SpringMVC无法直接将表单中多条数据封装成对象模型，如果想要封装，需要采用特殊的方式
	 *    2-1) 创建集合对象(users)的包装类 Datas
	 *    2-2) 表单数据的传递也需要采用特殊方式
	 *          name="users[0].loginacct"
	 *    2-3) SpringMVC中包装类集合属性不能使用泛型，否则会出现错误
	 * @return
	 */
	@RequestMapping("/inserts")
	public String inserts( Datas ds ) {
		// course
		//List<User> users = ds.getUsers();
		// JAVA中List集合中数据的删除一般采用迭代器进行删除
		/*
		Iterator<User> iterUser = users.iterator();
		while ( iterUser.hasNext() ) {
			User user = iterUser.next();
			if ( StringUtil.isEmpty(user.getLoginacct()) ) {
				iterUser.remove();
			}
		}
		*/
		
		return "success";
	}
	
	/**
	 * 新增用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert( User user ) {
		AJAXResult result = new AJAXResult();
		
		// 新增用户信息
		try {
			//user.setUserpswd(""); 这样改密码，属于功能的扩展， 这样就违背了ocp原则
			//任何一丁点的修改会导致程序出错
			user.setUserpswd(MD5Util.digest(ConfigUtil.getValue("DEFAULT_PASSWORD")));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sdf.format(new Date()));
			userService.insertUser(user);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
}
