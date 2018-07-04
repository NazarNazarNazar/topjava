package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private MealRepImpl repository;

    private static final Logger log = getLogger(MealServlet.class);

    private static final int calPerDay = 900;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        MealWithExceed mwe = MealsUtil.createWithExceed(meals.get(0), meals.get(0).getCalories() > calPerDay);
        MealWithExceed mwe1 = MealsUtil.createWithExceed(meals.get(1), meals.get(1).getCalories() > calPerDay);
        MealWithExceed mwe2 = MealsUtil.createWithExceed(meals.get(2), meals.get(2).getCalories() > calPerDay);

        List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        mealsWithExceeded.add(mwe);
        mealsWithExceeded.add(mwe1);
        mealsWithExceeded.add(mwe2);

        req.setCharacterEncoding("UTF-8");
        log.debug("forward to meals.jsp");

        req.setAttribute("mealsWithExceeded", mealsWithExceeded);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("servlet save new meal");

        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        Meal meal = new Meal(formatDateTime, description, calories);

        repository.saveMeal(meal);

        MealWithExceed mwe = MealsUtil.createWithExceed(meal, meal.getCalories() > calPerDay);

        req.setAttribute("mealWithExceeded", mwe);

        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
