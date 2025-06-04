package com.StCroixRods.budget_tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date >= :start AND e.date <= :end")
    public Double sumExpensesBetweenDates(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("SELECT SUM(e.amount) FROM Expense e")
    public Double allTimeExpenses();
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.date >= :start AND e.date <= :end")
    public Long monthlyNumberOfExpenses(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE e.date >= :start AND e.date <= :end GROUP BY e.category")
    public List<Object[]> findSumOfExpensesPerCategoryBetweenDates(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

