<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <title>Top 20 Rated Movie List</title>
</head>

<body>

<br><br>

 <ul>
     <c:forEach var="temp" items="${movies}">
         <li> title:

             <c:url var="titleLink" value="/movie/moviedetail">
                 <c:param name="movieId" value="${temp.id}"/>
             </c:url>

             <a href="${titleLink}">${temp.title}</a>
         <li>

         </li>
         <li> year: ${temp.year} </li>
         <li> director: ${temp.director} </li>
         <li> first three genres:
         <c:forEach var="tempGe" items="${temp.genres}">
             <li>${tempGe.name}</li>
         </c:forEach>
         </li>

         <li> first three stars:
         <c:forEach var="tempSt" items="${temp.stars}">
             <c:url var="starLink" value="/movie/stardetail">
                 <c:param name="starId" value="${tempSt.id}"/>
             </c:url>

             <a href="${starLink}">${tempSt.name}</a>
         </c:forEach>
         </li>

         <li> rating: ${temp.rating} </li>
         <br><br>

     </c:forEach>
 </ul>
</body>


</html>