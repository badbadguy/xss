package com.lry.manager.service;

import java.util.List;
import javax.annotation.Resource;

import com.dao.DaoSupport;
import com.lry.service.UserManager;
import com.lry.utils.Page;
import com.lry.utils.PageData;
import org.springframework.stereotype.Service;

/** 
 * 用户表
 */
@Service("userService")
public class UserService implements UserManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserMapper.save", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserMapper.delete", pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserMapper.edit", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> pd = (List<PageData>)dao.findForList("UserMapper.datalistPage", page);
		if(pd.size()>0) pd.get(0).put("page", page);
		return pd;
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserMapper.listAll", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findById", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据用户名  密码获取用户数据
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserByNameAndPwd",pd);
	}

	/**
	 * 更新最后登录时间
	 */
	public void updLoginTime(PageData pd)throws Exception{
		dao.update("UserMapper.updLoginTime",pd);
	}
}

