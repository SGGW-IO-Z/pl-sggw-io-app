package com.projektx.spokonazwa.data.model

import com.google.api.services.sheets.v4.model.*


class SpreadsheetMaker {

    // Stworzy nowy arkusz
    fun create(spreadsheetTitle : String,
               sheetOneTitle : String,
               people: List<Person>) : Spreadsheet {

        val spreadsheet = Spreadsheet()
        val peopleSheetMaker = PeopleSheetMaker()
        val sheets = mutableListOf<Sheet>()
        val spreadsheetProperties = SpreadsheetProperties()
        spreadsheetProperties.title = spreadsheetTitle
        spreadsheet.properties = spreadsheetProperties
        sheets.add(peopleSheetMaker.create(sheetOneTitle, people))
        spreadsheet.sheets = sheets
        return spreadsheet
    }

    // Stworzy arkusz do dodania do skoroszytu i doda nowe dane
    private class PeopleSheetMaker {

        fun create(title : String, people: List<Person>) : Sheet {
            val sheet = Sheet()
            val sheetProperty = SheetProperties()
            val listGridData = mutableListOf<GridData>()
            val listGridDataMaker = GridDataMaker()
            val gridData = listGridDataMaker.create(people, 0 , 0)
            sheetProperty.title = title
            sheet.properties = sheetProperty
            listGridData.add(gridData)
            sheet.data = listGridData
            return sheet
        }

    }

    //stworzy tablice danych z określonym wierszem początkowym, kolumną początkową i danymi, które chcesz wyświetlić
    private class GridDataMaker {

        fun create(people : List<Person>, startRow : Int, startColumn : Int) : GridData {
            val gridData = GridData()
            val listRowData = mutableListOf<RowData>()
            val rowDataMaker = RowDataMaker()
            gridData.startRow = startRow
            gridData.startColumn = startColumn
            people.mapTo(listRowData) { rowDataMaker.create(it.name, it.major) }
            gridData.rowData = listRowData
            return gridData
        }

    }

    // stworzy wiersz z określoną liczbą komórek
    private class RowDataMaker {

        fun create(name : String, major : String) : RowData {
            val rowData = RowData()
            val listCellData : MutableList<CellData> = mutableListOf()
            val cellDataMaker = CellDataMaker()
            listCellData.add(cellDataMaker.create(name))
            listCellData.add(cellDataMaker.create(major))
            rowData.setValues(listCellData)
            return rowData
        }

    }

    //wypełni komórkę odpowiednimi danymi
    private class CellDataMaker {

        fun create(data : String) : CellData {
            val cellData = CellData()
            val extendedValue = ExtendedValue()
            cellData.userEnteredValue = extendedValue.setStringValue(data)
            return cellData
        }

    }


}