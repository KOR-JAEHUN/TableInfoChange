package com.table.info.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TableMapper {

	@SuppressWarnings("rawtypes")
	public List<HashMap> selectTableList(HashMap<String, Object> hm);
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> selectTableDescList(HashMap<String, Object> hm);
	
	public void updateTableNm(HashMap<String, Object> hm);
	
	public void updateTableDescNm(HashMap<String, Object> hm);
	
	public void updateUse(HashMap<String, Object> hm);
	
	public void insertTable(HashMap<String, Object> hm);
	
	public void insertTableDesc(HashMap<String, Object> hm);
	
	public void loadTable(HashMap<String, Object> hm);
	
	public void loadTableDesc(HashMap<String, Object> hm);
	
	public void deleteTable(HashMap<String, Object> hm);
	
	public void deleteTableDesc(HashMap<String, Object> hm);
}
