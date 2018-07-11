package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext springContext;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {

        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = springContext.getBean(MealRestController.class);

        super.init(config);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
//        String id = request.getParameter("id");
        int userId = SecurityUtil.authUserId();

        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter":
                log.info("filter the meal by date");
                LocalDate fromDate = LocalDate.parse(request.getParameter("startDate"));
                LocalDate toDate = LocalDate.parse(request.getParameter("endDate"));
                LocalTime fromTime = LocalTime.parse(request.getParameter("startTime"));
                LocalTime toTime = LocalTime.parse(request.getParameter("endTime"));
                List<MealWithExceed> resultOfFiltering = mealRestController.getBetween(fromDate, toDate, fromTime, toTime);
                request.setAttribute("meals", resultOfFiltering);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                Meal meal = new Meal(userId,
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))
                );
                if (request.getParameter("id").isEmpty()) {
                    log.info("create {}", meal);
                    mealRestController.create(meal);
                } else {
                    log.info("update {}", meal);
                    mealRestController.update(meal, getId(request));
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        int userId = SecurityUtil.authUserId();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(userId, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll(userId));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
