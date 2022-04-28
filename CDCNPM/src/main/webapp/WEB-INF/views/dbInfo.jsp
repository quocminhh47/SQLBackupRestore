<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@taglib uri = "http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ page  pageEncoding="utf-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Trang Backup</title>
        <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="<c:url value = '/resources/srcAdmin/css/styles.css'/>" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
    	<%@include file="/WEB-INF/views/include/bootstrap-lib.jsp" %> 
        <script type="text/javascript">
		function maxDate() {
			var today = new Date();
			var dd = today.getDate()+1;
			var mm = today.getMonth() + 1; //January is 0!
			var yyyy = today.getFullYear();
			
			if (dd < 10) {
			   dd = '0' + dd;
			}
			
			if (mm < 10) {
			   mm = '0' + mm;
		} 
		    
			today = yyyy + '-' + mm + '-' + dd;
			document.getElementById("datefield").setAttribute("max", today);
			
			
		}

</script>
    </head>
   
    <body class="sb-nav-fixed" onload="maxDate()">
    <div class="container-fluid px-4">
    	 <!-- menu -->
 <%@include file="/WEB-INF/views/include/menu.jsp" %>        
        <h4 class="mt-4"> ${noti }</h4>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="index.html"></a></li>
                            <li class="breadcrumb-item active">DANH SÁCH DATABASE :</li>
                        </ol>
                        <h5></h5>
                        ${mess } </br>
                        ${notify }
                         
      	
     	</div>
     	
     	<%@include file="/WEB-INF/views/include/listDB.jsp" %>
     	 <div style="text-align: center; ">
     	 	<form action = "${pageContext.request.contextPath}/delete-log-file.htm" method="get">
								Xoá tất cả file log cũ, chỉ giữ lại từ ngày:   <input type="date" name="myDate2" id="datefield" max='2000-13-13' required ></input> 
	                             <button class="btn btn-info" type="submit">Delete File</button>
			</form>
		</div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<c:url value = '/resources/srcAdmin/js/scripts.js'/>"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="<c:url value='/resources/srcAdmin/js/datatables-simple-demo.js'/>"></script>
    </body>
    <%--  <%@include file="/WEB-INF/views/include/db-dropdown.jsp" %> --%>
    <!-- Footer Section Begin -->

	<!-- Footer Section End -->
</html>
