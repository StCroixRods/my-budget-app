package com.StCroixRods.budget_tracker;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense); //saves expense to database
    }
    public List<Expense> getAllExpenses(String sortField, String sortType) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortType) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String actualSortField = (sortField == null || sortField.isEmpty()) ? "date" : sortField;
        Sort sort = Sort.by(direction, actualSortField);
        return expenseRepository.findAll(sort); //lists all expenses
    }
    public void removeExpense(Long id) {
        expenseRepository.deleteById(id); //deletes from database by id
    }
    public Expense getExpense(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.orElseThrow(() ->
                new RuntimeException("Expense not found with id: " + id));
    }
    public Double calculateTotalExpensesForCurrentMonth() {
        LocalDate today = LocalDate.now();
        Double sum = expenseRepository.sumExpensesBetweenDates(today.withDayOfMonth(1),
                today.withDayOfMonth(today.lengthOfMonth()));
        return sum == null ? 0.0 : sum;
    }
    public Double calculateTotalExpensesForPastMonth() {
        LocalDate pastMonth = LocalDate.now().minusMonths(1);
        Double sum = expenseRepository.sumExpensesBetweenDates(pastMonth.withDayOfMonth(1),
                pastMonth.withDayOfMonth(pastMonth.lengthOfMonth()));
        return sum == null ? 0.0 : sum;
    }
    public Double calculateAllTimeExpenses() {
        Double sum = expenseRepository.allTimeExpenses();
        return sum == null ? 0.0 : sum;
    }
    public Long calculateNumberOfMonthlyExpenses() {
        LocalDate today = LocalDate.now();
        Long count = expenseRepository.monthlyNumberOfExpenses(today.withDayOfMonth(1),
                today.withDayOfMonth(today.lengthOfMonth()));
        return count == null ? 0 : count;
    }
    public Double expensesPerDay() {
        Double sum = calculateTotalExpensesForCurrentMonth();
        Integer numDays = LocalDate.now().getDayOfMonth();
        return sum / numDays;
    }
    public Double compareThisAndLastMonthExpenses() {
        Double last = calculateTotalExpensesForPastMonth();
        Double current = calculateTotalExpensesForCurrentMonth();
        if (last == null || last == 0.0) {
            return null;
        }
        if (current == null || current == 0.0) {
            return null;
        }
        return (current - last) / last;
    }
    public Map<ExpenseCategory, Double> expensesPerCategory() {
        LocalDate now = LocalDate.now();
        HashMap<ExpenseCategory,Double> map = new HashMap<>();
        List<Object[]> list = expenseRepository.findSumOfExpensesPerCategoryBetweenDates(now.withDayOfMonth(1), now.withDayOfMonth(now.lengthOfMonth()));
        for (Object[] objects : list) {
            if (objects[1] != null)
                map.put((ExpenseCategory) objects[0], (Double) objects[1]);
            else
                map.put((ExpenseCategory) objects[0], 0.0);
        }
        return map;
    }
}
