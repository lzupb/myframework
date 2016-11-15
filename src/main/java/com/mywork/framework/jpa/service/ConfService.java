package com.mywork.framework.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywork.framework.jpa.entity.ConfDB;
import com.mywork.framework.jpa.repository.BaseRepository;
import com.mywork.framework.jpa.repository.IConfDAO;

@Service
public class ConfService extends BaseService<ConfDB, Long> {

	@Autowired
	private IConfDAO confDao;

	@Override
	protected BaseRepository<ConfDB, Long> getRepository() {		
		return confDao;
	}

	public String findByKey(String key) {
		ConfDB confDB = confDao.find(key);
		if (confDB != null) {
			return confDB.getValue();
		}
		return null;
	}
	
	public List<ConfDB> findAll() {
		return confDao.findAll();
	}

}
