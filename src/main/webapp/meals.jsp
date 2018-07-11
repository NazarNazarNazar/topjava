<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<body>
<section>

    <div class="container">
        <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            <div class="container">
                <a href="meals.jsp" class="navbar-brand"> Calorie counting</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <%--</div>--%>

                <%--<a class="btn btn-info mr-1" href="users">Users</a>--%>

                <%--<div>--%>

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
                                <form id="filter" method="post" action="meals?action=filter">
                                    <div class="row">
                                        <div class="col-6">

                                            <div class="form-group">
                                                <label class="col-form-label" for="startDate">From the date</label>
                                                <input type="date" class="form-control col-8" name="startDate"
                                                       value="${fromDate}"
                                                       id="startDate"/>

                                                <label class="col-form-label" for="endDate">Up to date</label>
                                                <input type="date" class="form-control col-8" name="endDate"
                                                       value="${toDate}"
                                                       id="endDate">
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="form-group">
                                                <label class="col-form-label" for="startTime">From time</label>
                                                <input type="time" class="form-control col-8" name="startTime"
                                                       value="${fromTime}"
                                                       id="startTime">

                                                <label class="col-form-label" for="endTime">Before time</label>
                                                <input type="time" class="form-control col-8" name="endTime"
                                                       value="${toTime}"
                                                       id="endTime">
                                            </div>
                                        </div>

                                    </div>
                                    <div class="card-footer text-right">
                                        <button type="submit" class="btn btn-primary">
                                            <span class="fa fa-filter"></span>
                                            Filter out
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <br/>

                    <a href="meals?action=create">Add Meal</a>
                    <hr/>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Calories</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach items="${meals}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                                <td>
                                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                        ${fn:formatDateTime(meal.dateTime)}
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
</section>
</body>
</html>