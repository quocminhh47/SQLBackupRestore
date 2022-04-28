<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@include file="/WEB-INF/views/include/bootstrap-lib.jsp" %>

			<form modelAttribute="radio" >
								 <div class="card mb-4">
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                        	<th>Bản sao lưu thứ</th>
                                            <th>Mô tả</th>
                                            <th>Ngày giờ sao lưu</th>
                                            <th>User sao lưu</th>
                                           	<th>RESTORE</th>
                                           	<!-- <th>DELETE</th> -->
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var = "ds" items = "${list }">
                                        	<tr>
                                        		<td>${ds.getPosition() }</td>
                                        	    <td>${ds.getName() }</td>
                                        	    <td>${ds.getBackupDate() }</td>
                                        	    <td>${ds.getUser() }</td>
                                        	    <td><a href = "${pageContext.request.contextPath}/restore-${db}-${ ds.getPosition()}.htm">CHỌN </a></td>
                                        	    <%-- <td><a href = "${pageContext.request.contextPath}/del-restore-${db}-${ ds.getPosition()}.htm">CHỌN </a></td> --%>
                                        	</tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div style="text-align: center; ">
                                
                                	<a href="${pageContext.request.contextPath}/create-backup-${db}.htm">Create backup</a>   /  <a href="${pageContext.request.contextPath}/create-backup-${db}-init.htm">Create backup with overwrite</a> 
                                </br>	<a href="${pageContext.request.contextPath}/create-device-backup-${db}.htm">${createBackupDEV }</a>  
                                	<a href="${pageContext.request.contextPath}/delete-device-backup-${db}.htm"> ${dropBackupDEV }</a>   <%-- /  <a href="${pageContext.request.contextPath}/delete-device-backup-with-delete-${db}.htm"> ${dropBackupDEV2 }</a></br> --%>
                                
                                </div>                                
                            </div>
                        </div>
							</form>
							<div style="text-align: center; ">
								<form action = "${pageContext.request.contextPath}/restore-${db}-from-time.htm" method="get">
								 Phục hồi theo thời điểm:   <input type="date" name="myDate" id="datefield" max='2000-13-13' required ></input> <input id = "timeField" type="time" name="myTime"  step="1"  required>
	                              
	                              <button class="btn btn-info" type="submit">Restore database</button>
								</form>
							
							
							</div>