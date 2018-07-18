package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_SEQUENCE = START_SEQ + 2;
    public static final int ADM_SEQUENCE = START_SEQ + 9;

    public static final Meal meal = new Meal(USER_SEQUENCE, of(2015, Month.MARCH, 29, 10, 0), "dinner", 500);
    public static final Meal meal1 = new Meal(USER_SEQUENCE + 1, of(2015, Month.MARCH, 29, 10, 0), "snacks", 700);
    public static final Meal meal2 = new Meal(USER_SEQUENCE + 2, of(2015, Month.MARCH, 29, 11, 0), "smogasboard", 600);
    public static final Meal meal3 = new Meal(USER_SEQUENCE + 3, of(2015, Month.MARCH, 30, 12, 0), "branch", 1000);
    public static final Meal meal4 = new Meal(USER_SEQUENCE + 4, of(2015, Month.MARCH, 30, 14, 0), "breakfast", 400);
    public static final Meal meal5 = new Meal(USER_SEQUENCE + 5, of(2015, Month.MARCH, 30, 16, 0), "perekus", 800);
    public static final Meal meal6 = new Meal(USER_SEQUENCE + 6, of(2015, Month.MARCH, 31, 10, 0), "lunch", 900);
    public static final Meal meal7 = new Meal(USER_SEQUENCE + 7, of(2015, Month.MARCH, 31, 11, 0), "supper", 700);

    public static final Meal adminMeal = new Meal(ADM_SEQUENCE + 18, of(2015, Month.APRIL, 20, 10, 0), "breakfast", 600);
    public static final Meal adminMeal1 = new Meal(ADM_SEQUENCE + 19, of(2015, Month.APRIL, 20, 15, 0), "dinner", 1000);
    public static final Meal adminMeal2 = new Meal(ADM_SEQUENCE + 20, of(2015, Month.APRIL, 20, 20, 0), "supper", 1500);

    public static final List<Meal> MEALS = Arrays.asList(meal, meal1, meal2, meal3, meal4, meal5, meal6, meal7);
    public static final List<Meal> ADMIN_MEALS = Arrays.asList(adminMeal, adminMeal1, adminMeal2);

}
