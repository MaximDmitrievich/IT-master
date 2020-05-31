
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet"
	        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
	    src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script
	    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Студенты</title>
</head>
<body>

    <div class="container">
        <h3>Демонстрация работы с базой студентов</h3>

        <a href="add" class="btn btn-primary btn-sm" > Добавить студента</a>

        <table class="table table-hover">
            <tr>
                <th>Номер</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Математика</th>
                <th>Физика</th>
                <th>Программирование</th>
                <th>Средний бал</th>
                <th>Действие</th>
            </tr>
            <c:forEach var="project"  items="${projects}">
                <c:url var="updateLink" value="update" >
        			<c:param name="studId" value="${project.studId}"/>
        		</c:url>
        		<c:url var="deleteLink" value="delete" >
        			<c:param name="studId" value="${project.studId}"/>
        		</c:url>	
            <tr>
                <td>${project.studId}</td>
                <td>${project.firstName}</td>
                <td>${project.lastName}</td>
                <td>${project.rateMath}</td>
                <td>${project.ratePhys}</td>
                <td>${project.rateProg}</td>
                <td>
                    <fmt:formatNumber type = "number" 
                    maxFractionDigits = "2" value ="${project.avg}" />
                </td>
                <td>
                    <a href="${updateLink}">Изменить</a>
        						|
        			<a href="${deleteLink}" 
        				onclick="if(!(confirm('Уверены, что хотите удалить студента?'))) return false">
        					Удалить
        			</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>