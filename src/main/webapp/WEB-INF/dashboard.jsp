<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <!-- c:out ; c:forEach ; c:if -->
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
   <!-- Formatting (like dates) -->
 <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
   <!-- form:form -->
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
   <!-- for rendering errors on PUT routes -->
 <%@ page isErrorPage="true" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Title Here</title>
  <!-- Bootstrap -->
  <link rel="stylesheet"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
    crossorigin="anonymous">
</head>
<body>
    <div class="container"> <!-- Beginning of Container -->
        <h1>Awwww yeaaaa you did it!!</h1>
        
        	<h3>Welcome: <c:out value="${user.userName}"></c:out> </h3>
        	
        	
        	<a href="/logout">Log Out</a>
        	
        	
        	<a href="/newIdea">New Idea</a>
	<a href="/editIdea">Edit Idea</a>
	<a href="/oneIdea">One Idea</a>
	<a href="/dashboard">Dashboard</a>
        <h1>Dashboard</h1>
        
        <table class="table table-dark table-striped table-hover">
        	<thead>
        		<tr>
        			<th class="align-middle">Creator</th>
        			<th class="align-middle">Name</th>
        			<th class="align-middle">Location</th>
        			<th class="align-middle">Number of Days</th>
        			<th class="align-middle">Description</th>
        			<th class="align-middle">Action</th>
       			</tr>
        	</thead>
        	<tbody>
        		<c:forEach var="i" items="${ideas}">
        			<tr>
        				<td> 
        				<a href="/creator/${i.user.id}">
        				<c:out value="${i.user.userName}"></c:out> </td>
        				</a>
        				<td>
        				<a href="/oneIdea/${i.id}">
        				 <c:out value="${i.name}"></c:out> 
        				</a> 
        				 </td>
        				<td> <c:out value="${i.location}"></c:out> </td>
        				<td> <c:out value="${i.days}"></c:out> </td>
        				<td> <c:out value="${i.description}"></c:out> </td>
        				
        				<td>
        				<a href="/editIdea/${i.id}">
        				 Edit  
        				</a> 
        				<a href="/delete/${i.id}">
        				 Delete  
        				</a>
        				 </td>
        				
        			</tr>
        		</c:forEach>
        	</tbody>
        </table>	
    </div> <!-- End of Container -->
</body>
</html>
