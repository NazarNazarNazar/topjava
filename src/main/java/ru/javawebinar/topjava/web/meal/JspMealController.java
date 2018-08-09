package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends MealRestController {

    private UserService userService;

    public JspMealController(MealService service) {
        super(service);
    }

//    @GetMapping
//    public String users(Model model) {
//        model.addAttribute("users", userService.getAll());
//        return "users";
//    }

    @GetMapping(value = "/meals")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping(value = "/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping(value = "/create")
    public String create(
            @RequestParam LocalDateTime localDateTime,
            @RequestParam String descriptions,
            @RequestParam int calories,
            Model model
    ) {
        model.addAttribute("meal", super.create(new Meal(localDateTime, descriptions, calories)));
        return "mealForm";
    }

    @GetMapping(value = "/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealForm";
    }

    @PostMapping(value = "/change")
    public String change(
            @RequestParam int id,
            @RequestParam LocalDateTime localDateTime,
            @RequestParam String description,
            @RequestParam int calories,
            Model model
    ) {
        Meal meal = new Meal(id, localDateTime, description, calories);
        if (id == 0) {
            create(meal);
        } else {
            update(meal, id);
        }
        return "redirect:meals";
    }

    @PostMapping(value = "/filter")
    public String filter(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            Model model
    ) {
        model.addAttribute("meal", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
