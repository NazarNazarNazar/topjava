package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    private MealService mealService;

    @Autowired
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return mealService.get(id, userId);
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        int userId = SecurityUtil.authUserId();
        log.info("create meal {} for user {}", meal, userId);
        return mealService.save(meal, userId);
    }

    public Meal update(Meal meal, int id) {
        meal.setId(id);
        int userId = SecurityUtil.authUserId();
        log.info("update meal {} for user {}", meal, userId);
        return mealService.update(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
    }

    public List<MealWithExceed> getAll(int userId) {
        log.info("get all meal for user {}", userId);
        return MealsUtil.getWithExceeded(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getBetween(LocalDate fromDate, LocalDate todate, LocalTime fromTime, LocalTime toTime) {
        int userId = SecurityUtil.authUserId();
        log.info("get between dates {} - {}, for time {} - {}, for user {}", fromDate, todate, fromTime, toTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(todate != null ? todate : LocalDate.MIN,
                        todate != null ? todate : LocalDate.MAX, userId),
                SecurityUtil.authUserCaloriesPerDay(),
                fromTime != null ? fromTime : LocalTime.MIN,
                toTime != null ? toTime : LocalTime.MAX);
    }
}