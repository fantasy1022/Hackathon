package com.fantasy1022.hackathon.presentation.base;

/**
 * Created by fantasy_apple on 2017/6/27.
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
