package com.kt.network.base

interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    fun initParam()


    /**
     * 初始化界面观察者的监听
     */
    fun initViewObservable()
}