<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header
	class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
	<a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="#">TIMONTEY</a>
	<button class="navbar-toggler position-absolute d-md-none collapsed"
		type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu"
		aria-controls="sidebarMenu" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<form class="form-inline my-2 my-lg-0" method="post" action="logout">
      	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
	</form>
</header>

