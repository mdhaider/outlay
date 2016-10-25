package com.outlay.di.module;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.outlay.data.source.CategoryDataSource;
import com.outlay.data.source.ExpenseDataSource;
import com.outlay.database.dao.CategoryDao;
import com.outlay.database.dao.DaoMaster;
import com.outlay.database.dao.DaoSession;
import com.outlay.database.dao.ExpenseDao;
import com.outlay.database.source.CategoryDatabaseSource;
import com.outlay.database.source.ExpenseDatabaseSource;
import com.outlay.domain.repository.CategoryRepository;
import com.outlay.domain.repository.ExpenseRepository;
import com.outlay.impl.CategoryRepositoryImpl;
import com.outlay.data.repository.ExpenseRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bogdan Melnychuk on 12/17/15.
 */
@Module
public class DaoModule {
    private static final String DATABASE_NAME = "outlay_db";

    @Provides
    @Singleton
    public DaoMaster.DevOpenHelper provideDatabaseHelper(Application application) {
        return new DaoMaster.DevOpenHelper(application, DATABASE_NAME, null);
    }

    @Provides
    @Singleton
    public SQLiteDatabase provideDatabase(DaoMaster.DevOpenHelper helper) {
        return helper.getWritableDatabase();
    }

    @Provides
    @Singleton
    public DaoMaster provideDaoMaster(SQLiteDatabase database) {
        return new DaoMaster(database);
    }

    @Provides
    @Singleton
    public DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Provides
    @Singleton
    public CategoryDao provideCategoryDao(DaoSession session) {
        return session.getCategoryDao();
    }

    @Provides
    @Singleton
    public ExpenseDao provideExpenseDaoDao(DaoSession session) {
        return session.getExpenseDao();
    }

    @Provides
    @Singleton
    public ExpenseDataSource provideExpenseDataSource(ExpenseDao expenseDao) {
        return new ExpenseDatabaseSource(expenseDao);
    }

    @Provides
    @Singleton
    public CategoryDataSource provideCategoryDataSource(
            CategoryDao categoryDao,
            ExpenseDao expenseDao
    ) {
        return new CategoryDatabaseSource(categoryDao, expenseDao);
    }

    @Provides
    @Singleton
    public CategoryRepository provideCategoryRepository(
            CategoryDataSource categoryDataSource,
            Application application
    ) {
        return new CategoryRepositoryImpl(categoryDataSource, application);
    }

    @Provides
    @Singleton
    public ExpenseRepository provideExpenseRepository(
            ExpenseDataSource expenseDataSource
    ) {
        return new ExpenseRepositoryImpl(expenseDataSource);
    }
}
