<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/include/bootstrap-lib.jsp" %>
<form>
								 <div class="card mb-4">
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                        	<th>TÊN DATABASE</th>
                                            <th>DETAILS</th>
                                            
                                                                           	
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var = "ds" items = "${d}">
                                        	<tr>
                                        		<td>${ds}</td>
                                        	    <td>
                                        	    	 <a href="${pageContext.request.contextPath}/backup-${ds}.htm">MORE</a>  
                                        	    </td>
                                        	</tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                                                
                               
                                
                                </div>
                                
                             
                            </div>
                        
							</form>
						<%-- 	 <div style="text-align: center; ">                     
							<form  action="${pageContext.request.contextPath}/create-backup-${db}.htm"  method="get">
							  <button type="submit" class="btn btn-outline-success" style="margin-right: 50dp;" >Create new backup</button>         
							</form>
							
							<form  action="${pageContext.request.contextPath}/create-device-backup-${db}.htm"  method="get">
							  <button type="submit" class="btn btn-outline-success" style="margin-right: 50dp;" >${createBackupDEV }</button>         
							</form>
							
							<form  action="${pageContext.request.contextPath}/create-device-backup-${db}.htm"  method="get">
							  <button type="submit" class="btn btn-outline-success" style="margin-right: 50dp;" >${dropBackupDEV }</button>         
							</form>
							</div>
							<div style="text-align: center; ">
								<form action = "${pageContext.request.contextPath}/delete-device-backup-${db}.htm" method="get">
								 Phục hồi theo thời điểm:    <input type="date" name="myDate" > <input type="time" name="myTime"  step="1">
	                               <button class="btn btn-info" type="submit">Restore database</button>
								</form>
							</div> --%>