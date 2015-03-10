<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<form action="login" autocomplete="off">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Nickname</label>
							<div class="col-sm-10">
								<input type="text" name="nickname" class="form-control"
									id="nickname" placeholder="Enter your Nickname">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" name="pass" class="form-control"
									id="pass" placeholder="Enter your Password">
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-success">Login</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>