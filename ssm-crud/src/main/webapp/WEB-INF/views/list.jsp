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
<script src="${APP_PATH}/META-INF/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
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
					<button class="btn btn-info">新增</button>
					<button class="btn btn-info">删除</button>
				</div>
			</div>
		<!-- 显示 表格数据-->
			<div class="row">
				<!-- col-md-?占多少列 -->
				<div class="col-md-12">
					<table class="table table-hover">
						<tr>
							<th>##</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${pageInfo.list}" var="emp">
							<tr>
								<th>${emp.empId}</th>
								<th>${emp.empName}</th>
								<th>${emp.gender=="m"?"男":"女"}</th>
								<th>${emp.email}</th>
								<th>${emp.department.deptName}</th>
								<th>
								<button type="button" class="btn btn-info btn-sm"
									aria-label="Left Align">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								 <button type="button" class="btn btn-danger btn-sm"
									aria-label="Left Align">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									刪除
								</button>
							</th>
							</tr>	
						</c:forEach>

					</table>
				</div>
			
			</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 信息 -->
			<div class="col-md-6">当前${pageInfo.pageNum}页,总共${pageInfo.pages}页，总共${pageInfo.total}记录</div>
			<!-- 分页条 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li><a href="${APP_PATH}/emps?pn=1">首页 </a></li>
						<!-- 前一页 -->
						<c:if test="${pageInfo.hasPreviousPage}">
							<li><a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
							</a></li>
						</c:if>
						
						<c:forEach items="${pageInfo.navigatepageNums }" var="page_Num">
							<c:if test="${page_Num==pageInfo.pageNum }">
								<li class="active"><a href="#">${page_Num}</a></li>
							</c:if>
							<c:if test="${page_Num!=pageInfo.pageNum }">
								<li><a href="${APP_PATH}/emps?pn=${page_Num}">${page_Num}</a></li>
							</c:if>
						</c:forEach>
						<!-- 后一页 -->
						<c:if test="${pageInfo.hasNextPage}">
						<li><a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next"> <span
								aria-hidden="true">&raquo; </span>
						</a></li>		
						</c:if>
						<li><a href="${APP_PATH}/emps?pn=${pageInfo.pages}">尾页</a></li>
					</ul>
				</nav>
			</div>
		</div>


	</div>
</body>
</html>