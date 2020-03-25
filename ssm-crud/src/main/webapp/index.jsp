<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>员工列表</title>
<%
	//取出项目路径
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- web路径
	不以/开始的相对路径，找资源 以当前资源路径为基准 。经常容易出问题
	以/开始的相对路径，找资源，以服务器的根路径为标准 http://localhost:8080 需要加上项目名	
 -->
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-1.12.4.js"></script>
<link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 员工添加的模态框 -->
		<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
		      </div>
		      <div class="modal-body">
				<form class="form-horizontal">
				<!-- 员工姓名 -->
				 <div class="form-group">
				    <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" name="empName" id="empName_add_input" placeholder="empName">
				       <span id="helpBlock2" class="help-block"> </span>
				    </div>
				  </div>
				  <!-- 员工email -->
				  <div class="form-group">
				    <label for="email_add_input" class="col-sm-2 control-label">Email</label>
				    <div class="col-sm-10">
				      <input type="email" class="form-control" name="email" id="email_add_input" placeholder="Email">
				       <span id="helpBlock2" class="help-block"> </span>
				    </div>
				  </div>
				<!-- 性别单选框 -->
				  <div class="form-group">
				    <label  class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				       <label class="radio-inline">
						  <input type="radio" name="gender" id="gender_add_input1" value="m"> 男
						</label>
						<label class="radio-inline">
						  <input type="radio" name="gender" id="gender_add_input2" value="f" checked="checked"> 女
						</label>
				    </div>
				  </div>
				 <!-- 部门选择，下拉列表 -->
				<div class="form-group">
				    <label  class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-4">
				    <!-- 部门提交部门ID -->
				      <select class="form-control" name="dId" name="dept_add_select">
					  </select>
				    </div>
				  </div>
				</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        <button type="button" class="btn btn-primary" id="emp_add_save_btn">保存</button>
		      </div>
		    </div>
		  </div>
		</div>

	<!-- 员工修改的模态框 -->
		<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">员工修改</h4>
		      </div>
		      <div class="modal-body">
				<form class="form-horizontal">
				<!-- 员工姓名 -->
				 <div class="form-group">
				    <label for="empName_update_input" class="col-sm-2 control-label">empName</label>
				    <div class="col-sm-10">
				         <p class="form-control-static" id="empName_update_static"></p>
				       <span id="helpBlock2" class="help-block"> </span>
				    </div>
				  </div>
				  <!-- 员工email -->
				  <div class="form-group">
				    <label for="email_update_input" class="col-sm-2 control-label">Email</label>
				    <div class="col-sm-10">
				      <input type="email" class="form-control" name="email" id="email_update_input" placeholder="Email">
				       <span id="helpBlock2" class="help-block"> </span>
				    </div>
				  </div>
				<!-- 性别单选框 -->
				  <div class="form-group">
				    <label  class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				       <label class="radio-inline">
						  <input type="radio" name="gender" id="gender_update_input1" value="m"> 男
						</label>
						<label class="radio-inline">
						  <input type="radio" name="gender" id="gender_update_input2" value="f" checked="checked"> 女
						</label>
				    </div>
				  </div>
				 <!-- 部门选择，下拉列表 -->
				<div class="form-group">
				    <label  class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-4">
				    <!-- 部门提交部门ID -->
				      <select class="form-control" name="dId" name="dept_update_select">
					  </select>
				    </div>
				  </div>
				</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        <button type="button" class="btn btn-primary" id="emp_update_save_btn">更新</button>
		      </div>
		    </div>
		  </div>
		</div>




		<!--搭建显示页面  -->
		<div class="container">
		<!-- 标题  -->
			<div class="row">
				<div class="col-md-12">
					<h1>SSM_CRUD ${APP_PATH}</h1>
				</div>
			</div>
		<!-- 按钮 -->
			<div class="row">
				<!-- bootstrap的列偏移   col-md-offset-?:偏移多少列 -->
				<div class="col-md-4 col-md-offset-8">
					<button class="btn btn-info" id="emp_add_modal_btn">新增</button>
					<button class="btn btn-info" id="del_all_btn">删除</button>
				</div>
			</div>
		<!-- 显示 表格数据-->
			<div class="row">
				<!-- col-md-?占多少列 -->
				<div class="col-md-12">
					<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>##</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>	 
					<tbody>
					
					</tbody>
					</table>
				</div>
			
			</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 信息 -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条 -->
			<div class="col-md-6" id="page_nav_area">
				 
			</div>
		</div>

	</div>
	<script type="text/javascript">
	var totalRecord;
	var currentNum;
	//1-页码加载完成以后，直接去发送一个ajax请求，要到分页数据
	$(function(){
		//去首页
		to_page(1);
	});
	
	
	
	/* 跳页功能，发送ajax请求 */
	function to_page(pn){
		$("#check_all").prop("checked",false);
		$.ajax({
			url:"${APP_PATH}/emps",
			data:"pn="+pn,
			type:"GET",
			success:function(result){
				//console.log(result);
				//1-解析并显示员工数据
				build_emps_table(result);
				//2-解析并显示分页信息
				build_page_info(result);
				//3-解析分页条
				build_page_nav(result);
			}
		});
	};
	
	/* 查询显示body数据 */
	function build_emps_table(result){
		//清空table数据
		$("#emps_table tbody").empty();
		var emps =result.extend.pageInfo.list;//所有的员工数据
		$.each(emps,function(index,item){ //jquery 遍历 list
			var checkBox=$("<td><input type='checkbox' class='check_item'></td>");
			var empIdTd=$("<td></td>").append(item.empId); 
		 	var empNameTd=$("<td></td>").append(item.empName);		 	 
		 	var genderTd=$("<td></td>").append(item.gender=='m'?"男":"女");
		 	var emailTd=$("<td></td>").append(item.email);
		 	var deptNameId=$("<td></td>").append(item.department.deptName);
			var editBtn=$("<button></button>").addClass("btn btn-info btn-sm edit_btn")
			.append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
			.append("编辑");
			//为编辑按钮添加一个自定义属性，来表示员工ID
			editBtn.attr("edit-id",item.empId);
			
			var delBtn=$("<button></button>").addClass("btn btn-danger btn-sm del_btn")
			.append($("<span></span>").addClass("glyphicon glyphicon-trash"))
			.append("删除");
			//为删除按钮添加一个自定义属性，来表示员工ID
			delBtn.attr("del-id",item.empId);
			
			var btnTd=$("<td></td>").append(editBtn)
			.append(" ")
			.append(delBtn);
		 	//append方法执行完成以后还是返回原来的元素
		 	$("<tr></tr>").append(checkBox)
		 	.append(empIdTd)
		 	.append(empNameTd)
		 	.append(genderTd)
			.append(emailTd)
			.append(deptNameId)
			.append(btnTd)
			.appendTo("#emps_table tbody");
		});
	}
	//解析分页信息
	function build_page_info(result){
		$("#page_info_area").empty();
		$("#page_info_area").append("当前 "+result.extend.pageInfo.pageNum+" 页,总共 "+
				result.extend.pageInfo.pages+"页，总共"+
				result.extend.pageInfo.total+"条记录")
				currentNum=result.extend.pageInfo.pageNum;
				totalRecord=result.extend.pageInfo.total;
	}
	
	//解析分页条  点击要有动作
	function build_page_nav(result){
		//page_nav_area
		$("#page_nav_area").empty();
		var ul=$("<ul></ul>").addClass("pagination");
	
		var firstPageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		var prePageLi=$("<li></li>").append($("<a></a>").append("&laquo;")) ;

		
		//如果没有前一页 前一页与首页的按钮都不能按
		if(result.extend.pageInfo.hasPreviousPage ==false){
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{	
			//为元素添加点击事件
			firstPageLi.click(function(){
				to_page(1);
			});
			prePageLi.click(function(){
				to_page(result.extend.pageInfo.pageNum-1);
			});
		}
		var nextPageLi=$("<li></li>").append($("<a></a>").append("&raquo;")) ;
		var lastPageLi=$("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
		//如果没有后一页 后一页与尾页的按钮都不能按
		if(result.extend.pageInfo.hasNextPage ==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			lastPageLi.click(function(){
				to_page(result.extend.pageInfo.pages);
			});
			nextPageLi.click(function(){
				to_page(result.extend.pageInfo.pageNum+1);
			});
		}	
		//添加首页和前一页的提示
		ul.append(firstPageLi).append(prePageLi);
		//页码号
		$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
			var numLi=$("<li></li>").append($("<a></a>").append(item))
			if(result.extend.pageInfo.pageNum == item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_page(item);
			});//点击去当前item的页码
			ul.append(numLi);
		});
		//添加后一页和尾页的提示
		ul.append(nextPageLi).append(lastPageLi);
		//把ul加入到nav
		var navEle=$("<nav></nav>").append(ul);
		navEle.appendTo("#page_nav_area");
	}
	
	/* 清空表单方法 */
	function remove_form(ele){
		$(ele)[0].reset(); //清空表单内容
		$(ele).find("*").removeClass("has-success has-error");//清空表单样式
		$(ele).find(".help-block").text("");//清空.help-block里面的内容
			
		}
	
	//新增按钮事件 弹出模态框
	$("#emp_add_modal_btn").click(function(){
			//清除表单数据（重置）
			$("#empAddModal select").empty();//表单下拉列表为空	
			//	$("#empAddModal form")[0].reset(); //【0】 取出dom对象 调用reset方法重置
			remove_form("#empAddModal form");
			//发送ajax请求，查出部门信息，然后显示在下拉列表
			getDepts("#empAddModal select");
			// 弹出模态框
			$("#empAddModal").modal({
				backdrop:"static"
			});	
		}); 
	//查出部门信息
	function getDepts(ele){
			//extend:depts: Array(2)0: {deptId: 1, deptName: "开发部"}1: {deptId: 2, deptName: "测试部"}
			$.ajax({
				url:"${APP_PATH}/depts",
				type:"GET",
				success:function(result){
					 //显示部门信息在下拉列中。 
					 $.each(result.extend.depts,function(){
						 //不用this 会看不到
						 var optionEle=$("<option></option>").append(this.deptName).attr("value",this.deptId);
						 optionEle.appendTo(ele);					 
						 });
				}
			});
		}
		
	//前端校验信息，并显示校验结果	
	function validata_add_form(){
			//1,拿到要校验的数据，使用正则表达式
			var empName=$("#empName_add_input").val();
			var regName=/(^[a-zA-Z0-9_-]{3,16}$)|(^[\u4e00-\u9fa5]{2,5}$)/;
			//^ 开始 ，$结束 []取值a-z ，0-9，A-Z,可以用下划线和- {3-16}有3-16位
			//(^[\u2E80-\u9FFF]{2，5}$)中文表达式
			
			if(!regName.test(empName)){
				//alert("用户名可以是2-5位中文或者6-12位英文与数字组合");
				//不正确变红色警告
				show_validate_msg("#empName_add_input","error","用户名可以是2-5位中文或者6-12位英文与数字组合");
				return false;
			}else{
				//正确框框变绿
				show_validate_msg("#empName_add_input","success","");	
			};
			
			//校验邮箱
			var email=$("#email_add_input").val();
			var regEmail=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
			if(!regEmail.test(email)){
				//alert("邮箱格式不正确");
				//应该清空这个元素之前的样式
				show_validate_msg("#email_add_input","error","邮箱格式不正确");
				return false;
			}else{
				show_validate_msg("#email_add_input","success","");
			};
			return true;
		}
	//显示校验的结果，和改变校验后的css状态	
	function show_validate_msg(ele,status,msg){
			//清除当前元素校验状态
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else{
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		
	//增加的保存
	 $("#emp_add_save_btn").click(function(){
			//先对提交给服务器的数据进行校验
		 	  if(!validata_add_form()){
				return false;
			};    
			//判断之前的ajax校验是否成功
			if($(this).attr("ajax-va") == "erorr"){
				return false;
			}
			//1 模特框中填写的表单数据提交给服务器进行保存
			//2 发送Ajax请求保存员工
		//	alert($("#empAddModal form").serialize());
	 	$.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data:$("#empAddModal form").serialize(),
				success:function(result){
					//alert(result.msg);
					if(result.code==100){
						//员工保存成功后
						//1 close modal
						$("#empAddModal").modal("hide")
						//2 to lastPage  show new Employee					
						//发送 ajax请求 显示最后一页数据
						to_page(totalRecord);
					}else{
						//显示失败信息
						if(undefined != result.extend.errorFields.email){
							show_validate_msg("#email_add_input","error", result.extend.errorFields.email);
						}
						if(undefined != result.extend.errorFields.empName){
							show_validate_msg("#empName_add_input","error", result.extend.errorFields.empName);
						}
					}	
				}
			});  
			
		});
		
		
	//修改empName的时候，校验是否可用。
	$("#empName_add_input").change(function(){
			var empName=this.value;
			//1-发送ajax请求，看用户名是否可用
			$.ajax({
				url:"${APP_PATH}/checkuser",
				data:"empName="+empName,
				type:"POST",
				success:function(result){
					if(result.code==100){
						show_validate_msg("#empName_add_input","success","用户名可用");
						$("#emp_add_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#empName_add_input","erorr",result.extend.va_msg);
						$("#emp_add_save_btn").attr("ajax-va","erorr");
					}
				}
			});
		});
	/* 绑定编辑事件，因为body里面的员工信息 是加载后通过ajax请求返回的信息
	   如果用普通的 $("").click(function()是绑定不上的，因为页面加载的时候，click也会跟着绑定但是，员工信息是加载后，通过ajax请求查询出来的
	 因此不能绑定上edit按钮。
	 有两种绑定方法：1- 在查询请求后面写 $("").click(function()
			   2-绑定live 但是新版jquery没有了live 所以用新的东西
	这里用第二种方法
	*/
	$(document).on("click",".edit_btn",function(){
		//alert("？？？？？？");
		$("#empUpdateModal select").empty();//表单下拉列表为空	
		remove_form("#empUpdateModal form");
		//1-查出员工信息
		getEmp($(this).attr("edit-id"));
		//2-查出部门信息，并显示部门列表
		getDepts("#empUpdateModal select")
		
		//3-弹出模态框,把员工ID传给更新按钮
		$("#emp_update_save_btn").attr("edit-id",$(this).attr("edit-id"));
		$("#empUpdateModal").modal({
			backdrop:"static"
		});	
	});
	//通过ID获得员工信息，并显示在模态框
	function getEmp(id){
		$.ajax({
			url:"${APP_PATH}/emp/"+id,
			type:"GET", 
			success:function(result){ 
				 var empData=result.extend;
				 //显示名字
				 $("#empName_update_static").text(empData.emp.empName);
				 //显示邮箱，能修改
				 $("#email_update_input").val(empData.emp.email);
				 //选好性别
				 $("#empUpdateModal input[name=gender]").val([empData.emp.gender]);
				 //选好部门
				 $("#empUpdateModal select").val([empData.emp.DId]);
			}
		});
	};
	//点击更新，更新员工信息
	$("#emp_update_save_btn").click(function(){
		// 验证email
		var email=$("#email_update_input").val();
		var regEmail=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		if(!regEmail.test(email)){
			//alert("邮箱格式不正确");
			//应该清空这个元素之前的样式
			show_validate_msg("#email_update_input","error","邮箱格式不正确");
			return false;
		}else{
			show_validate_msg("#email_update_input","success","");
		};
		
		//发送ajax请求，保存更新的员工数据
		$.ajax({
			url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
			type:"PUT",
			data:$("#empUpdateModal form").serialize(),
			success:function(result){
				//alert(result.msg);
				//关闭对话框
				$("#empUpdateModal").modal("hide");
				//回到页面并刷新
				to_page(currentNum);
			}
		});
	});
	/* 单个删除 */
	$(document).on("click",".del_btn",function(){
		//弹出确认删除对话框
		var empName=$(this).parents("tr").find("td:eq(2)").text();
		if(confirm("确认删除["+empName+"]?")){
			//true  send ajax
			$.ajax({
				url:"${APP_PATH}/emp/"+$(this).attr("del-id"),
				type:"DELETE",
				success:function(result){
					alert(result.msg);
					to_page(currentNum);
				}
			});
		}
		//alert($(this).parents("tr").find("td:eq(1)").text());
	});
	
	/*全选删除 */
	
	//全选功能
	$("#check_all").click(function(){
		//attr 获取 checked时 是undefined
		// dom原生的属性，用prop获取和修改
		// attr用于自定义 属性的值
		//alert($(this).prop("checked"));
		$(".check_item").prop("checked",$(this).prop("checked"));
	});
	
	$(document).on("click",".check_item",function(){
		 //判断当前选中的元素是否5个
		 var flag= $(".check_item:checked").length == $(".check_item").length;
		 $("#check_all").prop("checked",flag); 
	});
	
	$("#del_all_btn").click(function(){
		//$(".check_item")
		var empNames="";
		var ids="";
		$.each($(".check_item:checked"),function(){
			 
			empNames += $(this).parents("tr").find("td:eq(2)").text()+",";
			ids += $(this).parents("tr").find("td:eq(1)").text()+"-";
		});
		
		//去除empName多余的逗号
		empNames=empNames.substring(0,empNames.length-1);
		ids=ids.substring(0,ids.length-1);
		if(confirm("确认删除:"+empNames+"？")){
			//ajax
			$.ajax({
				url:"${APP_PATH}/emp/"+ids,
				type:"DELETE", 
				success:function(result){
					alert(result.msg);
					to_page(currentNum);
				}
			});
		} ;
	});
	
	</script>
	
</body>
</html>