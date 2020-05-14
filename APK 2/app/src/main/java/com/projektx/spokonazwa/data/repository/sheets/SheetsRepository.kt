package com.projektx.spokonazwa.data.repository.sheets

import com.google.api.services.sheets.v4.model.Spreadsheet
import com.projektx.spokonazwa.data.model.Person
import com.projektx.spokonazwa.data.model.SpreadsheetInfo
import io.reactivex.Observable
import io.reactivex.Single


class SheetsRepository(private val sheetsAPIDataSource: SheetsAPIDataSource) {


    fun readSpreadSheet(spreadsheetId : String,
                        spreadsheetRange : String): Single<List<Person>> {
        return sheetsAPIDataSource.readSpreadSheet(spreadsheetId, spreadsheetRange)
    }

    fun createSpreadsheet(spreadSheet : Spreadsheet) : Observable<SpreadsheetInfo>  {
        return sheetsAPIDataSource.createSpreadsheet(spreadSheet)
    }

}