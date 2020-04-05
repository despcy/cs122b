<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <title>Single Star Detail</title>
</head>

<body>

<br><br>

name: ${star.name}

<br><br>

year of birth: ${star.birthYear}

<br><br>

all movies in which the stars acted:
<ul>
    <c:forEach var="tempMovie" items="${star.movies}">

        <c:url var="titleLink" value="/movie/moviedetail">
            <c:param name="movieId" value="${tempMovie.id}"/>
        </c:url>

        <a href="${titleLink}">${tempMovie.title}</a>

    </c:forEach>
</ul>

<br><br>

</body>


</html>