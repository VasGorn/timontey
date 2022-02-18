<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="static/images/timontey.ico">

    <title>Login to Timontey</title>
    
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="static/css/login.css" type="text/css" />
</head>
<body>

<div class="wrapper fadeInDown">
  <div id="formContent">

    <div class="fadeIn first">
      <img src="static/images/timontey-32x32.png" alt="Timontey Icon" width="32" height="32" />
    </div>

    <form method="post" action="login">
      <input type="text" id="login" class="fadeIn second" name="username" placeholder="Username" required autofocus>
      <input type="password" id="password" class="fadeIn third" name="password" placeholder="Password" required>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="submit" class="fadeIn fourth" value="Log In">
    </form>

  </div>
</div>

</body>
</html>