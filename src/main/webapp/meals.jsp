<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="shortcut icon" href="images/icon-meal.png">
</head>

<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a href="meals.jsp" class="navbar-brand"><img src="images/icon-meal.png"> Calorie counting</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">


                <li class="nav-item">
                    <form id="command" class="form-inline my-2" action="logout" method="get">

                        <a class="btn btn-info mr-1" href="users">Users</a>

                        <div>
                            <input type="hidden" name="_csrf" value="477a8053-1760-4278-adf3-8d78668bf608"/>
                        </div>
                    </form>
                </li>

            </ul>
        </div>
    </div>
</nav>

<div class="jumbotron pt-4">
    <div class="container">
        <h3>My meal</h3>
        <div class="row">
            <div class="col-7">
                <div class="card">
                    <div class="card-header">
                        <h5>Filter by date and time</h5>
                    </div>

                    <div class="card-body">
                        <form id="filter">
                            <div class="row">
                                <div class="col-6">

                                    <div class="form-group">
                                        <label class="col-form-label" for="startDate">From the date</label>
                                        <input type="date" class="form-control col-8" name="startDate" id="startDate"/>

                                        <label class="col-form-label" for="endDate">Up to date</label>
                                        <input type="date" class="form-control col-8" name="endDate" id="endDate">
                                    </div>

                                </div>
                                <div class="col-6">
                                    <div class="form-group">
                                        <label class="col-form-label" for="startTime">From time</label>
                                        <input type="time" class="form-control col-8" name="startTime" id="startTime">

                                        <label class="col-form-label" for="endTime">Before time</label>
                                        <input type="time" class="form-control col-8" name="endTime" id="endTime">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-right">
                        <button class="btn btn-danger" onclick="clearFilter()">
                            <span class="fa fa-remove"></span>
                            Call off
                        </button>
                        <button class="btn btn-primary" onclick="updateTable()">
                            <span class="fa fa-filter"></span>
                            Filter out
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <br/>

        <div id="datatable_wrapper" class="dataTables_wrapper container-fluid da-bootstrap4 no-filter">
            <table class="table table-striped" id="datatable">

                <thead>
                <tr>
                    <th>Date/Time</th>
                    <th>Description</th>
                    <th>Calorie</th>
                    <th>Delete</th>
                    <th>Edit</th>
                    <%--<th>exceed</th>--%>
                </tr>
                </thead>

                <tbody>
                <jsp:useBean id="mealsWithExceeded" scope="request" type="java.util.List"/>
                <c:forEach items="${mealsWithExceeded}" var="meal">
                    <tr style="background-color:${meal.exceed ? 'greenyellow' : 'red'}">
                        <td><javatime:format value="${meal.dateTime}" style="MS"/></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><c:out value="${meal.calories}"/></td>
                            <%--<td><c:out value="${meal.exceed}"/></td>--%>
                        <div class="form-group">
                            <form method="post" action="meals">
                                <input type="button" class="btn btn-dark" id="delete">
                                <input type="button" class="btn btn-primary" id="edit">
                            </form>
                        </div>
                    </tr>
                </c:forEach>

                <form method="post" action="meals">
                    <div class="form-group">
                        <input type="datetime-local" name="dateTime" value="2017-06-01 08:30">
                        <input type="text" name="description">
                        <input type="text" name="calories">
                        <input type="submit" value="meals">
                    </div>
                </form>

                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
</div>
</nav>

<footer class="footer">
    <div class="container">
        <span class="text-muted">Annex to the project <a href="https://github.com/JavaOPs/topjava" target=_blank>Maven/ Spring/ Security/ JPA(Hibernate)/ Jackson/jQuery</a></span>
    </div>
</footer>

</body>
</html>


