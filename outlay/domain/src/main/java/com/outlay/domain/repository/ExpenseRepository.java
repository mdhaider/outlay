package com.outlay.domain.repository;

import com.outlay.domain.model.Expense;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by bmelnychuk on 10/24/16.
 */

public interface ExpenseRepository {
    Observable<Expense> saveExpense(Expense expense);

    Observable<Expense> remove(Expense expense);

    Observable<Expense> getById(Long expenseId);

    Observable<List<Expense>> getExpenses(Date startDate, Date endDate);

    Observable<List<Expense>> getExpenses(Date startDate, Date endDate, Long categoryId);
}
