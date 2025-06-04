package com.StCroixRods.budget_tracker;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class ExpenseController {
    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping("/expenses")
    public String showAllExpenses(Model model, @RequestParam(required = false, defaultValue = "date") String sortField,
                                  @RequestParam(required = false, defaultValue = "desc") String sortDir) {
        Collection<Expense> expenseList = expenseService.getAllExpenses(sortField, sortDir);
        model.addAttribute("expenses", expenseList);
        model.addAttribute("currentPage", "expenses");
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortDir);
        return "expenses";
    }
    @GetMapping("/addexpense")
    public String addExpenseForm(Model model) {
        model.addAttribute("allCategories", ExpenseCategory.values());
        model.addAttribute("expense", new Expense());
        model.addAttribute("currentPage", "addexpense");
        return"addexpense";
    }
    @PostMapping("/expenses")
    public String addExpensePost(@Valid @ModelAttribute Expense expense, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCategories", ExpenseCategory.values());
            return "addexpense";
        } else {
            expenseService.addExpense(expense);
            return "redirect:/expenses";
        }
    }
    @PostMapping("/expenses/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        expenseService.removeExpense(id);
        return "redirect:/expenses";
    }
    @GetMapping("/expenses/edit/{id}")
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        Expense toBeEdit = expenseService.getExpense(id);
        model.addAttribute("expense", toBeEdit);
        model.addAttribute("allCategories", ExpenseCategory.values());
        return "edit-expense";
    }
    @PostMapping("/expenses/update")
    public String updateExpense(@Valid @ModelAttribute Expense expense, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCategories", ExpenseCategory.values());
            return "edit-expense";
        } else {
            expenseService.addExpense(expense);//doesnt add just changes new values
            return "redirect:/expenses";
        }
    }
    @GetMapping("/summary")
    public String showSummaryPage(Model model) {
        Double monthSum = expenseService.calculateTotalExpensesForCurrentMonth();
        Double allTimeSum = expenseService.calculateAllTimeExpenses();
        Long monthNumExpenses = expenseService.calculateNumberOfMonthlyExpenses();
        Double lastMonthExpenses = expenseService.calculateTotalExpensesForPastMonth();
        Double expensesPerDay = expenseService.expensesPerDay();
        Double rawPercentChange = expenseService.compareThisAndLastMonthExpenses();
        Map<ExpenseCategory, Double> expenseCategoryDoubleMap = expenseService.expensesPerCategory();
        String formattedPercentChange;
        if (rawPercentChange == null) {
            formattedPercentChange = "-.--%";
        } else {
            formattedPercentChange = String.format("%+.1f%%", rawPercentChange * 100.0);
        }
        model.addAttribute("currentMonthSum", monthSum);
        model.addAttribute("currentPage", "summary");
        model.addAttribute("allTimeSum", allTimeSum);
        model.addAttribute("monthNumExpenses", monthNumExpenses);
        model.addAttribute("pastMonthExpenses", lastMonthExpenses);
        model.addAttribute("expensesPerDay", expensesPerDay);
        model.addAttribute("rawMonthlyPercentChange", rawPercentChange);
        model.addAttribute("monthlyPercentChangeString", formattedPercentChange);
        model.addAttribute("expensesPerCategory", expenseCategoryDoubleMap);
        return "summary";
    }
}
