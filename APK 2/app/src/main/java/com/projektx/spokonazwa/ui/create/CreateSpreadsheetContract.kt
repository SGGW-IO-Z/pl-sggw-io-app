package com.projektx.spokonazwa.ui.create

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.projektx.spokonazwa.data.model.Person
import com.projektx.spokonazwa.ui.base.BasePresenter
import com.projektx.spokonazwa.ui.base.BaseView

interface CreateSpreadsheetContract {

    interface View : BaseView {
        fun showPerson()
        fun showName(username : String)
        fun launchAuthentication(client : GoogleSignInClient)
        fun initList(people: MutableList<Person>)
        fun clearFields()
        fun showResult(id : String, url : String)
    }

    interface Presenter : BasePresenter {
        fun addPerson(name : String, major : String)
        fun uploadPeopleList()
        fun loginSuccessful()
        fun loginFailed()
    }

}