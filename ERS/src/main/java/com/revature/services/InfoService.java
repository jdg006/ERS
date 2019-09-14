package com.revature.services;

import java.util.List;

import com.revature.ers.dao.impl.InfoDaoImpl;
import com.revature.ers.model.Info;

public class InfoService {
	
	InfoDaoImpl idi = new InfoDaoImpl();
	
	public boolean createInfo(Info info) {
		
		return idi.createInfo(info);
		
	}
	
	public boolean deleteInfo(int id) {
		
		return idi.deleteInfo(id);
		
	}
	
	public Info getInfo(int id) {
		return idi.getInfo(id);
	}
	
	public Info getInfoByUserId(int userId) {
		return idi.getInfoByUserId(userId);
	}
	
	public boolean updateInfo(int id, Info info) {
		
		return idi.updateInfo(id, info);
		
	}
	
	public List<Info> getAllInfo(){
		
		return idi.getAllInfo();
		
	}

}
