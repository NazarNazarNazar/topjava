<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>


        <!-- FILTER -->
        <div class="row">
            <div class="col-7">
                <div class="card">
                    <div class="card-body">
                        <form id="filter">
                            <div class="row">
                                <div class="col-6">
                                    <div class="form-group">

                                        <label class="col-form-label"><spring:message code="meal.startDate"/>:</label>
                                        <input id="d1" type="date" name="startDate" class="form-control col-8"
                                               value="${param.startDate}"/>


                                        <label class="col-form-label"><spring:message code="meal.endDate"/>:</label>
                                        <input id="d2" id="endDate" type="date" name="endDate"
                                               class="form-control col-8"
                                               value="${param.endDate}"/>

                                    </div>
                                </div>

                                <div class="col-6">
                                    <div class="form-group">

                                        <label class="col-form-label"><spring:message code="meal.startTime"/>:</label>
                                        <input id="t1" type="time" name="startTime" class="form-control col-8"
                                               value="${param.startTime}">


                                        <label class="col-form-label"><spring:message code="meal.endTime"/>:</label>
                                        <input id="t2" type="time" name="endTime" class="form-control col-8"
                                               value="${param.endTime}">

                                    </div>
                                </div>
                            </div>
                        </form>
                        <button id="reset" type="reset" class="btn btn-primary">
                            <span class="fa fa-plus"></span>
                            <spring:message code="meal.reset"/>
                        </button>
                        <button type="submit" class="btn btn-primary" onclick="filter()">
                            <span class="fa fa-plus"></span>
                            <spring:message code="meal.filter"/>
                        </button>
                    </div>

                </div>
            </div>
        </div>


        <hr>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="meal.add"/>
        </button>
        <%--<a href="meals/create"><spring:message code="meal.add"/></a>--%>
        <hr>


        <!-- TABLE -->
        <table class="table">
            <thead>
            <tr scope="col">
                <th scope="row" type="hidden"></th>
                <th scope="row"><spring:message code="meal.dateTime"/></th>
                <th scope="row"><spring:message code="meal.description"/></th>
                <th scope="row"><spring:message code="meal.calories"/></th>
                <th scope="row"></th>
                <th scope="row"></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr data-mealExceed="${meal.exceed}" scope="col">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                        <%--<td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
                    <td><a href="meals/update?id=${meal.id}"><span class="fa fa-pencil"></span></a></td>
                        <%--<td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
                    <td><a class="delete" id="${meal.id}"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>

    </div>
</div>

<!-- MODAL WINDOW -->
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
                <%--<h3><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>--%>
                <%--<hr>--%>
                <form id="detailsForm">
                    <input type="hidden" name="id" value="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input id="dateTime" name="dateTime" type="datetime-local" class="form-control"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input id="description" name="description" type="text" class="form-control"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input id="calories" name="calories" type="text" class="form-control"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>

                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="save()">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>

                    <%--<button type="button" class="btn btn-secondary" data-dismiss="modal">--%>
                    <%--<span class="fa fa-close"></span>--%>
                    <%--<spring:message code="common.cancel"/>--%>
                    <%--</button>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>