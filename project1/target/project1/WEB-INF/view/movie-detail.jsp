<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <title>Single Movie Detail</title>
</head>

<body>

tile: ${movie.title}

<br><br>

year: ${movie.year}

<br><br>

director: ${movie.director}

<br><br>

genres:
<ul>
    <c:forEach var="temp" items="${movie.genres}">

        <a>${temp.name}</a>

    </c:forEach>
</ul>

<br><br>

stars:
<ul>
    <c:forEach var="tempStar" items="${movie.stars}">

        <c:url var="titleLink" value="/movie/stardetail">
            <c:param name="starId" value="${tempStar.id}"/>
        </c:url>

        <a href="${titleLink}">${tempStar.name}</a>

    </c:forEach>
</ul>

<br><br>

rating: ${movie.rating}

<br><br>
</body>


</html>

