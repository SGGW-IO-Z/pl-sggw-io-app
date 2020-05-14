package com.projektx.spokonazwa.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class BaseActivity<P : BasePresenter>  : AppCompatActivity() {

    lateinit var presenter : P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependencies()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    abstract fun initDependencies()

}