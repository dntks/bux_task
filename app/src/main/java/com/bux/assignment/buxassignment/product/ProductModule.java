package com.bux.assignment.buxassignment.product;


import com.bux.assignment.buxassignment.inject.ActivityScoped;
import com.bux.assignment.buxassignment.inject.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ProductPresenter}.
 */
@Module
public abstract class ProductModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProductFragment tasksFragment();

    @ActivityScoped
    @Binds abstract ProductContract.Presenter taskPresenter(ProductPresenter presenter);
}
