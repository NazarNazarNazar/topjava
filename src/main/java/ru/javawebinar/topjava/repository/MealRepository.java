package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface MealRepository {

    Map<Integer, Meal> findAll();

    void saveMeal(Meal meal);

    boolean deleteMealById(Integer id);

}
