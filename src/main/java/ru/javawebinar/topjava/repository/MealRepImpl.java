package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealRepImpl implements MealRepository {


    private static Map<Integer, Meal> map;

    public MealRepImpl() {
        map = new ConcurrentHashMap<>();
        map.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(3, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
    }


    @Override
    public void saveMeal(Meal meal) {

        map.put(map.size() + 1, meal);
    }

    @Override
    public boolean deleteMealById(Integer id) {

        return map.entrySet().removeIf(key -> key.getKey().equals(id));
    }

    @Override
    public Map<Integer, Meal> findAll() {

        return map;
    }

    public static Map<Integer, Meal> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, Meal> map) {
        MealRepImpl.map = map;
    }
}
