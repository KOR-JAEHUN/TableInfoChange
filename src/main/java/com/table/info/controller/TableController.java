package com.table.info.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.table.info.service.TableService;

@Controller
public class TableController {

	private String username = "kri001";
	
	@Autowired
	public TableService service;
	
	@RequestMapping("/table")
	public String selectTableList(HttpServletRequest req, HttpServletResponse rse) {
		
		return "list";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/table/list.do")
	@ResponseBody
	public List<HashMap> selectTableListByAjax(HttpServletRequest req) {
		int page = Integer.parseInt(req.getParameter("page"));
		int rows = Integer.parseInt(req.getParameter("rows"));
		String sidx = req.getParameter("sidx");
		String sord = req.getParameter("sord");
		String owner = req.getParameter("owner");
		String tableIdVal = req.getParameter("tableIdVal");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tableIdVal", tableIdVal);
		hm.put("sNum", (page-1)*rows+1);
		hm.put("eNum", page*rows);
		hm.put("sidx", sidx);
		hm.put("sord", sord);
		hm.put("username", username);
		
		List<HashMap> list = service.tableList(hm);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/table/tableExcel.xls")
	public String tableExcel(@RequestParam("owner") String owner, @RequestParam(value="tableIdVal", required=false) String tableIdVal
			,Model model) {
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tableIdVal", tableIdVal);
		hm.put("username", username);
		
		String[] headers = new String[] {"OWNER", "Table-ID", "Table-설명", "사용여부", "데이터건수"};
		String[] bodyCols = new String[] {"OWNER", "TB_ID", "TB_NM", "USE_YN", "CNT"};
		int[] colSize = new int[] {5000, 7000, 12000, 5000, 5000};
		
		List<HashMap> list = service.tableList(hm);
		model.addAttribute("headers", headers);
		model.addAttribute("bodyCols", bodyCols);
		model.addAttribute("colSize", colSize);
		model.addAttribute("list", list);
		model.addAttribute("filename", "TABLE-ID");
		return"excelDownload";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/table/tableDescExcel.xls")
	public String tableDescExcel(HttpServletRequest req, Model model) {
		String owner = req.getParameter("owner");
		String tableIdVal = req.getParameter("tableIdVal");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tableIdVal", tableIdVal);
		hm.put("username", username);
		
		String[] headers = new String[] {"OWNER", "Table-ID", "Table-순번", "Table-설명", "Column-ID", "Column-설명", "KEY WORD", "TYPE", "NULL여부", "Length", "PK", "FK", "Default값", "FK Table"};
		String[] bodyCols = new String[] {"OWNER", "TB_ID", "TB_SEQ", "TB_NM", "COL_ID", "COL_NM", "COL_KEY", "COL_TYPE", "COL_NULL", "COL_LEN", "TB_PK", "TB_FK", "COL_DEFAULT", "FK_TABLE"};
		int[] colSize = new int[] {3000, 7000, 3000, 10000, 7000, 7000, 5000, 3000, 2000, 2000, 2000, 2000, 3000, 3000};
		
		List<HashMap> list = service.tableDescList(hm);
		model.addAttribute("headers", headers);
		model.addAttribute("bodyCols", bodyCols);
		model.addAttribute("colSize", colSize);
		model.addAttribute("list", list);
		model.addAttribute("filename", "TABLE-DESC");
		return "excelDownload";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/table/descList.do")
	@ResponseBody
	public List<HashMap> selectTableDescListByAjax(HttpServletRequest req) {
		int page = Integer.parseInt(req.getParameter("page"));
		int rows = Integer.parseInt(req.getParameter("rows"));
		String sidx = req.getParameter("sidx");
		String sord = req.getParameter("sord");
		String owner = req.getParameter("owner");
		String tableIdVal = req.getParameter("tableIdVal");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tableIdVal", tableIdVal);
		hm.put("sNum", (page-1)*rows+1);
		hm.put("eNum", page*rows);
		hm.put("sidx", sidx);
		hm.put("sord", sord);
		hm.put("username", username);
		
		List<HashMap> list = service.tableDescList(hm);
		return list;
	}
	
	@RequestMapping(value="/table/updateTableNm.do")
	@ResponseBody
	public Boolean updateTableNm(HttpServletRequest req) {
		String owner = req.getParameter("owner");
		String tbId = req.getParameter("tbId");
		String tbNm = req.getParameter("tbNm");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tbId", tbId);
		hm.put("tbNm", tbNm);
		hm.put("username", username);
		service.updateTableNm(hm);
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/updateTableDescNm.do")
	@ResponseBody
	public Boolean updateTableDescNm(HttpServletRequest req) {
		String owner = req.getParameter("owner");
		String tbId = req.getParameter("tbId");
		String colNm = req.getParameter("colNm");
		String colId = req.getParameter("colId");
		String colKey = req.getParameter("colKey");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tbId", tbId);
		hm.put("colNm", colNm);
		hm.put("colId", colId);
		hm.put("colKey", colKey);
		hm.put("username", username);
		service.updateTableDescNm(hm);
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/updateUse.do")
	@ResponseBody
	public Boolean updateUse(@RequestBody List<HashMap> hm) {
		
		for (HashMap map : hm) {
			map.put("username", username);
			service.updateUse(map);
		}
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/insertData.do")
	@ResponseBody
	public Boolean insertTable(HttpServletRequest req) {
		String owner = req.getParameter("OWNER");
		String tbId = req.getParameter("TB_ID");
		String tbNm = req.getParameter("TB_NM");
		String useYn = req.getParameter("USE_YN");
		String cnt = req.getParameter("CNT");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tbId", tbId);
		hm.put("tbNm", tbNm);
		hm.put("useYn", useYn);
		hm.put("cnt", cnt);
		hm.put("username", username);
		service.insertTable(hm);
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/insertDescData.do")
	@ResponseBody
	public Boolean insertTableDesc(HttpServletRequest req) {
		String owner = req.getParameter("OWNER");
		String tbId = req.getParameter("TB_ID");
		String tbNm = req.getParameter("TB_NM");
		String colId = req.getParameter("COL_ID");
		String colNm = req.getParameter("COL_NM");
		String colKey = req.getParameter("COL_KEY");
		String colType = req.getParameter("COL_TYPE");
		String colNull = req.getParameter("COL_NULL");
		String colLen = req.getParameter("COL_LEN");
		String tbPk = req.getParameter("TB_PK");
		String tbFk = req.getParameter("TB_FK");
		String colDefault = req.getParameter("COL_DEFAULT");
		String fkTable = req.getParameter("FK_TABLE");
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("owner", owner);
		hm.put("tbId", tbId);
		hm.put("tbNm", tbNm);
		hm.put("colId", colId);
		hm.put("colNm", colNm);
		hm.put("colKey", colKey);
		hm.put("colType", colType);
		hm.put("colNull", colNull);
		hm.put("colLen", colLen);
		hm.put("tbPk", tbPk);
		hm.put("tbFk", tbFk);
		hm.put("colDefault", colDefault);
		hm.put("fkTable", fkTable);
		hm.put("username", username);
		service.insertTableDesc(hm);
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/loadTable.do")
	@ResponseBody
	public Boolean loadTable(@RequestBody HashMap hm) {
		
		hm.put("username", username);
		
		service.deleteTable(hm);
		service.loadTable(hm);
		
		return new Boolean(true);
	}
	
	@RequestMapping(value="/table/loadTableDesc.do")
	@ResponseBody
	public Boolean loadTableDesc(@RequestBody HashMap hm) {
		
		hm.put("username", username);
		
		service.deleteTableDesc(hm);
		service.loadTableDesc(hm);
		
		return new Boolean(true);
	}
	
	
	
}
