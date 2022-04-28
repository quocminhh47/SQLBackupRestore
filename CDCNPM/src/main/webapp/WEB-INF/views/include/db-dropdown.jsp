   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
		
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/4.5.6/css/ionicons.min.css">
		<link rel="stylesheet" href="<c:url value='/resources/dd/css/style.css'/>">
  </head>
		
		<section class="ftco-section">
			<div class="container">
				<div class="row">
					<div class="col-md-12 text-center">
						<h2 class="heading-section mb-5 pb-md-4">LIST DATABASE<h2>
					</div>
				</div>
				<div class="row justify-content-center">

					<div class="col-md-6 d-flex justify-content-center">
						<div class="btn-group">
						   <button type="button" class="btn btn-primary dropdown-toggle text-left" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   	<i class="ion-ios-sunny mr-4"></i>
						    ---SELECT---
						  </button>
						  <div class="dropdown-menu dropdown-menu-right">
						   <!--  <a class="dropdown-item color-1" href="#">
					    		<span class="mr-4 ion-ios-sunny"></span>
						    	Sunny Day
						    </a>
						    <a class="dropdown-item color-2" href="#">
					    		<span class="mr-4 ion-ios-rainy"></span>
						    	Rainy Day
						    </a> -->
						    <c:forEach var = "d" items = "${d }">
						    	<a class="dropdown-item color-3" href="${pageContext.request.contextPath}/backup-${d}.htm">
					    		<span class="mr-4 ion-ios-cloudy"></span>
						    	${d }
						    </a>
						    </c:forEach>
						    
						    <!-- <a class="dropdown-item color-4" href="#">
					    		<span class="mr-4 ion-ios-thunderstorm"></span>
						    	Thunderstorm
						    </a> -->
						  </div>
						</div>
					</div>
				</div>
			</div>
		</section>

    <script src="<c:url value='/resources/dd/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/resources/dd/js/popper.js'/>"></script>
    <script src="<c:url value='/resources/dd/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resources/dd/js/main.js'/>"></script>
</html>