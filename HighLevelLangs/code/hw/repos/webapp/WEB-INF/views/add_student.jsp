<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

    <title>Document</title>
</head>
<body>

    <div class="container">
        <h3>Добавить/изменить студента</h3>
        <form action="add" method="post">

            <!--  <form:hidden id="id" name="id" value="<%= request.getAttribute("id")%> type="text"/>-->

            <div class="form-group">
                <label for="id" class="inputLabel"> Номер: </label>
                <input class="form-control" id="id" name="id" value="<%= request.getAttribute("id")%>" type="text" readonly/>
            </div>

            <div class="form-group">
                <label for="fname" class="inputLabel"> Имя: </label>
                <input class="form-control" id="fname" name="fname" value="<%= request.getAttribute("fname")%>" type="text"/>
            </div>

            <div class="form-group">
                <label for="sname" class="inputLabel"> Фамилия: </label>
                <input class="form-control" id="sname" name="sname" value="<%= request.getAttribute("sname")%>" type="text"/>
            </div>

            <div class="form-group">
                <label for="math" class="inputLabel"> Математика: </label>
                <input class="form-control" id="math" name="math" value="<%= request.getAttribute("math")%>" type="text"/>
            </div>

            <div class="form-group">
                <label for="phys" class="inputLabel"> Физика: </label>
                <input class="form-control" id="phys" name="phys" value="<%= request.getAttribute("phys")%>" type="text"/>
            </div>

            <div class="form-group">
                <label for="prog" class="inputLabel"> Программирование: </label>
                <input class="form-control" id="prog" name="prog" value="<%= request.getAttribute("prog")%>" type="text"/>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Сохранить</button>
            </div>
        </form>
    </div>
</body>