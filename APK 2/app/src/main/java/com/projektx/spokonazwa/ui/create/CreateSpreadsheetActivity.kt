package com.projektx.spokonazwa.ui.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.drive.Drive
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.sheets.v4.SheetsScopes
import com.projektx.spokonazwa.R
import com.projektx.spokonazwa.data.manager.AuthenticationManager
import com.projektx.spokonazwa.data.model.Person
import com.projektx.spokonazwa.data.repository.sheets.SheetsAPIDataSource
import com.projektx.spokonazwa.data.repository.sheets.SheetsRepository
import com.projektx.spokonazwa.ui.adapter.SpreadsheetAdapter
import com.projektx.spokonazwa.ui.base.BaseActivity
import java.util.*


class CreateSpreadsheetActivity :
        BaseActivity<CreateSpreadsheetContract.Presenter>(), CreateSpreadsheetContract.View {

    private lateinit var btnNewItem : Button
    private lateinit var btnFinish : Button
    private lateinit var etProduct : EditText
    private lateinit var etQuantity : EditText
    private lateinit var itemList : TextView
    private lateinit var rvSpreadsheet : RecyclerView

    private lateinit var spreadSheetAdapter : SpreadsheetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_spreadsheet)
        bindingViews()
        presenter.init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_GOOGLE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.loginSuccessful()
            } else {
                presenter.loginFailed()
            }
        }
    }

    override fun initDependencies() {
        val signInOptions : GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
                        .requestScopes(Drive.SCOPE_FILE)
                        .requestEmail()
                        .build()
        val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        val googleAccountCredential = GoogleAccountCredential
                .usingOAuth2(this, Arrays.asList(*AuthenticationManager.SCOPES))
                .setBackOff(ExponentialBackOff())
        val authManager =
                AuthenticationManager(
                        lazyOf(this),
                        googleSignInClient,
                        googleAccountCredential)
        val sheetsApiDataSource =
                SheetsAPIDataSource(authManager,
                        AndroidHttp.newCompatibleTransport(),
                        JacksonFactory.getDefaultInstance())
        val sheetsRepository = SheetsRepository(sheetsApiDataSource)
        presenter = CreateSpreadsheetPresenter(this, authManager, sheetsRepository)
    }

    private fun bindingViews() {
        rvSpreadsheet = findViewById(R.id.rv_spreadsheet)
        itemList = findViewById(R.id.tv_username)
        btnNewItem = findViewById(R.id.btn_add)
        btnFinish = findViewById(R.id.btn_upload)
        etQuantity = findViewById(R.id.et_major)
        etProduct = findViewById(R.id.et_name)
        btnNewItem.setOnClickListener({
            addPerson()
        })
        btnFinish.setOnClickListener({
            presenter.uploadPeopleList()
        })
    }

    private fun addPerson() {
        presenter.addPerson(etProduct.text.toString(), etQuantity.text.toString())
    }

    // View implementation

    override fun initList(people: MutableList<Person>) {
        spreadSheetAdapter = SpreadsheetAdapter(people)
        rvSpreadsheet.layoutManager = LinearLayoutManager(this)
        rvSpreadsheet.adapter = spreadSheetAdapter
    }

    override fun clearFields() {
        etQuantity.text.clear()
        etProduct.text.clear()
        etProduct.requestFocus()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showPerson() {
        spreadSheetAdapter.notifyDataSetChanged()
    }

    override fun showName(username: String) {
        itemList.text = username
    }

    override fun launchAuthentication(client: GoogleSignInClient) {
        startActivityForResult(client.signInIntent, RQ_GOOGLE_SIGN_IN)
    }

    override fun showResult(id: String, url: String) {
        Toast.makeText(this, "Raport został zapisany" , Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "ReadSpreadsheetActivity"
        const val RQ_GOOGLE_SIGN_IN = 999
    }


}