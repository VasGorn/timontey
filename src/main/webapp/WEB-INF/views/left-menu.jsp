<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav id="sidebarMenu"
	class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
	<div class="position-sticky pt-3">
		<ul class="nav flex-column">

			<c:forEach items="${navList}" var="nav">
				<li class="nav-item"><a class="nav-link"
					href=<c:out value="${nav.url}"/>> <span
						data-feather=<c:out value="${nav.icon}"/>></span> <c:out
							value="${nav.name}" />
				</a></li>
			</c:forEach>
		</ul>
	</div>
</nav>
