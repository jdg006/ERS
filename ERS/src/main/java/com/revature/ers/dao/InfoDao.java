package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.model.Info;

public interface InfoDao {
	
	public List<Info> getAllInfo();
	public Info getInfo(int id);
	public Info getInfoByUserId(int userId);
	public boolean createInfo(Info info);
	public boolean updateInfo(int id, Info info);
	public boolean deleteInfo(int id);
	
}
