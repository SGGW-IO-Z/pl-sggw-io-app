package com.projektx.spokonazwa.ui.read

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.projektx.spokonazwa.data.model.Person
import com.projektx.spokonazwa.ui.base.BasePresenter
import com.projektx.spokonazwa.ui.base.BaseView


interface ReadSpreadsheetContract {

    interface View : BaseView {
        fun initList(people: MutableList<Person>)
        fun showPeople()
        fun showName(username : String)
        fun launchAuthentication(client : GoogleSignInClient)
    }

    interface Presenter : BasePresenter {
        fun startAuthentication()
        fun loginSuccessful()
        fun loginFailed()
    }

}