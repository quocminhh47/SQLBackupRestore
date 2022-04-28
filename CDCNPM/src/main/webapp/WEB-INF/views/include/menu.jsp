<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
  <head>
  	
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<link rel="stylesheet" href="<c:url value='/resources/menu/css/style.css'/>">

	</head>
	<body>		
		
		<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	    <div class="container-fluid">
	    
	      <div class="collapse navbar-collapse" id="ftco-nav">
	        <ul class="navbar-nav m-auto">
	        	<li class="nav-item active"><a href="${pageContext.request.contextPath}/home.htm" class="nav-link">Home</a></li>
	        	<%-- <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">BACKUP DB</a>
              <div class="dropdown-menu" aria-labelledby="dropdown04">
              	<a class="dropdown-item" href="${pageContext.request.contextPath}/create-backup-${db}.htm">Normal Backup</a>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/create-backup-${db}.htm">Overwrite Backup</a>
               
              </div>
            </li>
	        	<li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">DEVICE BACKUP</a>
              <div class="dropdown-menu" aria-labelledby="dropdown04">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/create-device-backup-${db}.htm">${createBackup }</a>
              	<a class="dropdown-item" href="${pageContext.request.contextPath}/delete-device-backup-${db}.htm">${dropBackup }</a>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/delete-device-backup-${db}.htm">${dropBackup }WITH DELETE FILE</a>
               
              </div>
            </li> --%>
	        	<li class="nav-item"><a href="${pageContext.request.contextPath}/login.htm" class="nav-link">LOGOUT</a></li>
	         <!--  <li class="nav-item"><a href="#" class="nav-link">Contact</a></li> -->
	        </ul>
	      </div>
	    </div>
	  </nav>
    <!-- END nav -->


	<script src="<c:url value='/resources/menu/js/jquery.min.js'/>"></script>
  <script src="<c:url value='/resources/menu/js/popper.js'/>"></script>
  <script src="<c:url value='/resources/menu/js/bootstrap.min.js'/>"></script>
  <script src="<c:url value='/resources/menu/js/main.js'/>"></script>

	</body>
</html>

