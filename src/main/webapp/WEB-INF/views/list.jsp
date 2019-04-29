<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style/jquery-ui.min.css">
<link rel="stylesheet" href="style/ui.jqgrid.css">
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.jqGrid.min.js"></script>
<script src="js/grid.locale-kr.js"></script>
<script>

// jQuery.browser = {};
// (function () {
//     jQuery.browser.msie = false;
//     jQuery.browser.version = 0;
//     if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
//         jQuery.browser.msie = true;
//         jQuery.browser.version = RegExp.$1;
//     }
// })();
$(document).loadingView('.box');
$(function(){
	var idx = 0;
	$( "#owner" ).selectmenu({change: function( event, ui ) { fn_search(idx);}});
	$( "#tabs" ).tabs({
		activate: function (event, ui) {
			idx = ui.newTab.index();
			if(idx == 0){
				fn_search(idx);
				jQuery("#grid").jqGrid('setGridWidth', $("#tabDiv1").width());
			}else if(idx == 1){
				fn_search(idx);
				jQuery("#grid2").jqGrid('setGridWidth', $("#tabDiv2").width());
			}
	    }
	});
	fn_gridInit();
	fn_gridInit2();
	fn_search(idx);
	
	$("#searchBtn").on("click", function(){
		fn_search(idx);
	});
	$("#excelBtn").on("click", function(){
		excelDown(idx);
	});
	$("#tableLoadBtn").on("click", function(){
		loadTable(idx);
	});
	$("#pager2_right").css("width","60px");
});

var grid_rownum = 50;
function fn_gridInit() {
	$("#grid").jqGrid("GridUnload");
    $("#grid").jqGrid({
    	url : "",
		datatype: "local",
	   	colModel:[
				{label : 'key', name:'RNUM', align:"center", sortable: false, width:5, hidden:true, key:true},
				{label : 'OWNER', name:'OWNER', align:"center", sortable: true, editable: true, width:10},
				{label : 'Talbe-ID', name:'TB_ID', align:"left", sortable: true, editable: true, width:20},
				{label : 'Table-설명', name:'TB_NM', align:"left", sortable: true, editable: true, edittype: 'text', width:30},
				{label : '사용여부', name:'USE_YN', align:"center", sortable: true, editable: true, width:10},
				{label : '데이터건수', name:'CNT', align:"center", sortable: true, editable: true, width:10}
			   	],
		viewrecords: true,
		multiselect:true,
		gridview: true,
		autowidth : true,
		shrinkToFit : true,
// 		mtype:'POST',
// 		ajaxGridOptions: { contentType: "application/json; charset=UTF-8" },
		height: '700px',
		caption : '시스템별 테이블 목록 관리',
		rowNum : grid_rownum,
		rowList : [ 50 ],
		pager: "#pager",
		loadonce : false,
		cellEdit: true,
		cellsubmit: 'remote',
	    cellurl: '/table/updateTableNm.do',
	    beforeSubmitCell : function(rowid, cellName, cellValue) {   // submit 전
	    	//console.log(  "@@@@@@ rowid = " +rowid + " , cellName = " + cellName + " , cellValue = " + cellValue );
	    	var owner = $("#grid").jqGrid('getRowData', rowid).OWNER;
	    	var tbId = $("#grid").jqGrid('getRowData', rowid).TB_ID;
	    	return {"owner":owner, "tbId":tbId, "tbNm": cellValue};
    	},
// 		ondblClickRow: function (rowid, iRow, iCol) {
// 			var colModels = $(this).getGridParam('colModel');
// 			var colName = colModels[iCol].name;
// // 			if (editableCells.indexOf(colName) >= 0) { 
// 				$(this).setColProp(colName, { editable: true }); 
// 				$(this).editCell(iRow, iCol, true); 
// // 			}
// 		},
		gridComplete: function() {
			// -----------------------------------	
			// 페이지 레코드 카운트가0일때	
			if($(this).getRowData().length==0){	
				var gridWidth = $(this).css("width");
				var emptybox = '<div id="emptybox" style="text-align:center; width:100%;" ><span>결과가 없습니다. </span> </div>';
				$(this).parent().find("#emptybox").remove();
				$(this).after(emptybox);
				//$(this).parent().parent().parent().parent().find("#pager").hide();
				$(".ui-jqgrid-pager").hide();
			}else{	
				$(this).parent().find("#emptybox").remove();
				//$(this).parent().parent().parent().parent().find("#pager").show();
				$(".ui-jqgrid-pager").show();
			}	
			// -----------------------------------
		},
		jsonReader : {
			repeatitems: false,
			root : function (obj) { return obj; },
			total : function (obj) {
				if(!obj || !obj || !obj.length){
					return 0;
				}
				
				return (obj[0].TOTAL / grid_rownum != 0) ? ((obj[0].TOTAL % grid_rownum == 0) ? Math.floor(obj[0].TOTAL / grid_rownum) : Math.floor(obj[0].TOTAL / grid_rownum) + 1)
						: Math.floor(obj[0].TOTAL / grid_rownum);
			},
			records : function (obj) {
				if(!obj || !obj.length){
					return 0;
				}
				return obj[0].TOTAL;
			}
		},
		onCellSelect : function(rowid){
		}
	});
    
    $("#grid").css("cursor", "pointer");
    $('#grid').jqGrid('navGrid', '#pager', { edit: false, add: true, del: false, search: false},
    	    //edit options
    	    { url: '' },
    	    //add options
    	    { url: '/table/insertData.do' },
    	    //del options
    	    { url: '' }
    	);
}

function fn_gridInit2() {
	$("#grid2").jqGrid("GridUnload");
    $("#grid2").jqGrid({
    	url : "",
		datatype: "local",
	   	colModel:[
				{label : 'key', name:'RNUM', align:"center", sortable: false, width:5, hidden:true, key:true},
				{label : 'OWNER', name:'OWNER', align:"center", sortable: true, editable: true, width:10},
				{label : 'Talbe-ID', name:'TB_ID', align:"left", sortable: true, editable: true, width:20},
				{label : 'Talbe-순번', name:'TB_SEQ', align:"center", sortable: true, width:8},
				{label : 'Table-설명', name:'TB_NM', align:"left", sortable: true, editable: true, width:30},
				{label : 'Column-ID', name:'COL_ID', align:"left", sortable: true, editable: true, width:30},
				{label : 'Column-설명', name:'COL_NM', align:"left", sortable: true, editable: true, width:30},
				{label : 'KEY WORD', name:'COL_KEY', align:"left", sortable: true, editable: true, width:30},
				{label : 'TYPE', name:'COL_TYPE', align:"center", sortable: true, editable: true, width:30},
				{label : 'NULL여부', name:'COL_NULL', align:"center", sortable: true, editable: true, width:8},
				{label : 'Length', name:'COL_LEN', align:"center", sortable: true, editable: true, width:5},
				{label : 'PK', name:'TB_PK', align:"center", sortable: true, editable: true, width:5},
				{label : 'FK', name:'TB_FK', align:"center", sortable: true, editable: true, width:5},
				{label : 'Default값', name:'COL_DEFAULT', align:"center", sortable: true, editable: true, width:10},
				{label : 'FK Table', name:'FK_TABLE', align:"center", sortable: true, editable: true, width:15}
			   	],
		viewrecords: true,
		multiselect:true,
		gridview: true,
		autowidth : true,
		shrinkToFit : true,
// 		mtype:'POST',
// 		ajaxGridOptions: { contentType: "application/json; charset=UTF-8" },
		height: '700px',
		caption : '시스템별 테이블 정의서 정보 관리',
		rowNum : grid_rownum,
		rowList : [ 50 ],
		pager: "#pager2",
		loadonce : false,
		cellEdit: true,
		cellsubmit: 'remote',
	    cellurl: '/table/updateTableDescNm.do',
	    beforeSubmitCell : function(rowid, cellName, cellValue) {   // submit 전
	    	//console.log(  "@@@@@@ rowid = " +rowid + " , cellName = " + cellName + " , cellValue = " + cellValue );
	    	var owner = $("#grid2").jqGrid('getRowData', rowid).OWNER;
	    	var tbId = $("#grid2").jqGrid('getRowData', rowid).TB_ID;
	    	var colId = $("#grid2").jqGrid('getRowData', rowid).COL_ID;
	    	if(cellName == "COL_NM"){
	    		return {"owner":owner, "tbId":tbId, "colId": colId, "colNm":cellValue};
	    	}else if(cellName == "COL_KEY"){
		    	return {"owner":owner, "tbId":tbId, "colId": colId, "colKey":cellValue};
	    	}
    	},
// 		ondblClickRow: function (rowid, iRow, iCol) {
// 			var colModels = $(this).getGridParam('colModel');
// 			var colName = colModels[iCol].name;
// // 			if (editableCells.indexOf(colName) >= 0) { 
// 				$(this).setColProp(colName, { editable: true }); 
// 				$(this).editCell(iRow, iCol, true); 
// // 			}
// 		},
		gridComplete: function() {
			// -----------------------------------	
			// 페이지 레코드 카운트가0일때	
			if($(this).getRowData().length==0){	
				var gridWidth = $(this).css("width");
				var emptybox = '<div id="emptybox2" style="text-align:center; width:100%;" ><span>결과가 없습니다. </span> </div>';
				$(this).parent().find("#emptybox2").remove();
				$(this).after(emptybox);
				//$(this).parent().parent().parent().parent().find("#pager").hide();
				$(".ui-jqgrid-pager").hide();
			}else{	
				$(this).parent().find("#emptybox2").remove();
				//$(this).parent().parent().parent().parent().find("#pager").show();
				$(".ui-jqgrid-pager").show();
			}	
			// -----------------------------------
		},
		jsonReader : {
			repeatitems: false,
			root : function (obj) { return obj; },
			total : function (obj) {
				if(!obj || !obj || !obj.length){
					return 0;
				}
				
				return (obj[0].TOTAL / grid_rownum != 0) ? ((obj[0].TOTAL % grid_rownum == 0) ? Math.floor(obj[0].TOTAL / grid_rownum) : Math.floor(obj[0].TOTAL / grid_rownum) + 1)
						: Math.floor(obj[0].TOTAL / grid_rownum);
			},
			records : function (obj) {
				if(!obj || !obj.length){
					return 0;
				}
				return obj[0].TOTAL;
			}
		},
		onCellSelect : function(rowid){
		}
	});
    
    $("#grid2").css("cursor", "pointer");
    $('#grid2').jqGrid('navGrid', '#pager2', { edit: false, add: true, del: false, search: false},
    	    //edit options
    	    { url: '' },
    	    //add options
    	    { url: '/table/insertDescData.do' },
    	    //del options
    	    { url: '' }
    	);
}

//그리드 리스트 데이터 호출
function fn_search(idx) {
	var url = "";
	var gridId = "";
	if(idx == 0){
		url = "/table/list.do";
		gridId = "grid";
	}else{
		url = "/table/descList.do";
		gridId = "grid2";
	}
	var owner = $("#owner").val();
	var tableIdVal = $("#tableId").val();
	var params = JSON.stringify({"owner" : owner, "tableIdVal" : tableIdVal});
	$("#"+gridId).jqGrid("setGridParam",{url: url, datatype:'json', page:1 ,postData:{owner: owner, tableIdVal, tableIdVal}}).trigger("reloadGrid");
}

function changeUseYn(type){
	var objArr = [];
	$("#grid").find('input[type=checkbox]').each(function () {
		if($(this).is(":checked")){
			var rowid = $(this).parents('tr:last').attr('id');
			var owner = $("#grid").jqGrid('getRowData', rowid).OWNER;
	    	var tbId = $("#grid").jqGrid('getRowData', rowid).TB_ID;
	    	objArr.push({owner: owner, tbId: tbId, type: type});
		}
	});
   	$.ajax({
   		url: "/table/updateUse.do",
   		type: "post",
   		data: JSON.stringify(objArr),
   		dataType: "json",
   		contentType: "application/json",
   		async: false,
   		success: function(data){
   			if(data){
   				alert("사용여부 변경 완료");
   				fn_search();
   			}
   		},
   		error: function(e){
   		}
   	});
}

function loadTable(idx){
	if(!confirm("테이블을 불러오시겠습니까?")){ return false; }

	var owner = $("#owner").val();
	var url = "";
	if(idx == 0){
		url = "/table/loadTable.do";
	}else{
		url = "/table/loadTableDesc.do";
	}
	$.ajax({
   		url: url,
   		type: "post",
   		data: JSON.stringify({owner: owner}),
   		dataType: "json",
   		contentType: "application/json",
   		async: false,
   		success: function(data){
   			if(data){
   				alert("테이블 불러오기 완료");
   				fn_search(idx);
   			}
   		},
   		error: function(e){
   			alert("테이블 불러오기 실패");
   		}
   	});
}

function excelDown(idx){
	var owner = $("#owner").val();
	var tableIdVal = $("#tableId").val();
	var url = "";
	if(idx == 0){
		url = "/table/tableExcel.xls";
	}else{
		url = "/table/tableDescExcel.xls";
	}
	
	 var form = document.createElement("form");
     form.method = "get";
     form.action = url;
     
     var hiddenField = document.createElement("input");
     hiddenField.setAttribute("type", "hidden");
     hiddenField.setAttribute("name", "owner");
     hiddenField.setAttribute("value", owner);
     form.appendChild(hiddenField);

     hiddenField = document.createElement("input");
     hiddenField.setAttribute("type", "hidden");
     hiddenField.setAttribute("name", "tableIdVal");
     hiddenField.setAttribute("value", tableIdVal);
     form.appendChild(hiddenField);
     
     document.body.appendChild(form);
     
     form.submit();
     document.body.removeChild(form);
}

</script>

</head>
<body>

<div>
	<h1>시스템별 테이블 관리</h1>
	<div>
		<label for="owner">OWNER</label>
		<select id="owner">
			<option value="ALL">ALL</option>
			<option value="KRI001">KRI</option>
			<option value="US_IROWN">e-R&D</option>
			<option value="OKC001">KCI</option>
			<option value="OFD100">외국박사학위</option>
		</select>
		<input type="button" value="테이블 불러오기" id="tableLoadBtn" class="ui-button ui-widget ui-corner-all" />
		<label>TABLE-ID</label>
		<input type="text" id="tableId" size="20" />
		<input type="button" value="검색" id="searchBtn" class="ui-button ui-widget ui-corner-all" />
		<input type="button" value="사용여부 변경(Y)" id="useBtn" class="ui-button ui-widget ui-corner-all" onclick="changeUseYn('Y');" />
		<input type="button" value="사용여부 변경(N)" id="useBtn2" class="ui-button ui-widget ui-corner-all" onclick="changeUseYn('N');"  />
		<input type="button" value="엑셀다운로드" id="excelBtn" class="ui-button ui-widget ui-corner-all" />
	</div>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">TABLE_ID</a></li>
		    <li><a href="#tabs-2">TABLE_DESC</a></li>
		</ul>
		<div id="tabs-1">
			<div id="tabDiv1" class="listbox">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div id="tabs-2">
			<div id="tabDiv2" class="listbox">
				<table id="grid2"></table>
				<div id="pager2"></div>
			</div>
		</div>
	</div>
</div>
	
</body>
</html>