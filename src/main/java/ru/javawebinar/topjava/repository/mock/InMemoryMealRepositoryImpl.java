package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));
        MealsUtil.ADMIN_MEALS.forEach(meal -> save(meal, ADMIN_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        Objects.requireNonNull(meal);
        Integer mealId = meal.getId();

        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        } else if (get(mealId, userId) == null) {
            return null;
        }


        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        meals.put(mealId, meal);

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {

        Map<Integer, Meal> meals = repository.get(userId);

        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {

        Map<Integer, Meal> meals = repository.get(userId);

        return meals != null ? meals.get(id) : null;
    }


    @Override
    public Collection<Meal> getAll(int userId) {

        Map<Integer, Meal> meals = repository.get(userId);

        return meals == null ? Collections.emptyList() : meals.values()
                .stream()
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startTime, LocalDateTime endTime, int userId) {

        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);

        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDays(meal.getDateTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

