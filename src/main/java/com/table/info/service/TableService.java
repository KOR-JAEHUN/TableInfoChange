package com.table.info.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.table.info.mapper.TableMapper;

@Service
public class TableService {

	@Autowired
	TableMapper dao;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> tableList(HashMap<String, Object> hm){
		return dao.selectTableList(hm);
	}
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> tableDescList(HashMap<String, Object> hm){
		return dao.selectTableDescList(hm);
	}
	
	public void updateTableNm(HashMap<String, Object> hm){
		dao.updateTableNm(hm);
	}
	
	public void updateTableDescNm(HashMap<String, Object> hm){
		dao.updateTableDescNm(hm);
	}
	
	public void updateUse(HashMap<String, Object> hm){
		dao.updateUse(hm);
	}
	
	public void insertTable(HashMap<String, Object> hm){
		dao.insertTable(hm);
	}
	
	public void insertTableDesc(HashMap<String, Object> hm){
		dao.insertTableDesc(hm);
	}
	
	public void loadTable(HashMap<String, Object> hm){
		dao.loadTable(hm);
	}
	
	public void deleteTable(HashMap<String, Object> hm){
		dao.deleteTable(hm);
	}
	
	public void loadTableDesc(HashMap<String, Object> hm){
		dao.loadTableDesc(hm);
	}
	
	public void deleteTableDesc(HashMap<String, Object> hm){
		dao.deleteTableDesc(hm);
	}
	
}
