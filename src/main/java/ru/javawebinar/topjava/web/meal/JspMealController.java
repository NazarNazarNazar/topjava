package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private MealService mealService;

    @GetMapping()
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAllMealWithExceeded for user {}", userId);
        List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealWithExceeds);
        return "meals";
    }

    @GetMapping("/delete&{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        log.info("create {} for user {}", meal, userId);
        return "mealForm";
    }

    @GetMapping("/update&{id}")
    public String update(@PathVariable("id") int id, HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        mealService.update(meal, userId);
        log.info("update {} for user {}", meal, userId);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping//(value = {"/create", "/update"})
    public String updateOrCreate(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        String id = request.getParameter("id");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id), localDateTime, description, calories);

        if (meal.isNew()) {
            mealService.create(meal, userId);
        } else {
            mealService.update(meal, userId);
        }

        return "redirect:/meals";
    }

    @PostMapping(value = "/filter")
    public String filter(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX));

        request.setAttribute("meals", filteredWithExceeded);
        return "meals";
    }

    @Autowired
    public void setMealService(MealService mealService) {
        this.mealService = mealService;
    }
}
