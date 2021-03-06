<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Error 403</title>
</head>

<body>

	<section>
		<div class="jumbotron">
		
			<div class="container">
				<h1>Error</h1>
				<p>no access granted for user: ${userName}</p>
				<p>please use different account</p>
			</div>
		</div>
	</section>
	
	<div class="container">
		<div class="row">
		<div class="panel-body text-center">
						
			<a
				href="<spring:url value="/login"/>" class="btn btn-primary btn-lg"> 
				<span class="glyphicon-link glyphicon"/></span> Proceed to login page
			</a>
			
		</div>
		</div>
	</div>
	
	
</body>
</html>
