package com.projektx.spokonazwa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.projektx.spokonazwa.ui.create.CreateSpreadsheetActivity
import com.projektx.spokonazwa.ui.read.ReadSpreadsheetActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnReadSpreadsheet : TextView
    lateinit var btnCreateSpreadsheet : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ustaw Layout
        setContentView(R.layout.activity_main)

        //Funkcje layoutu
        btnReadSpreadsheet = findViewById(R.id.btn_read_spreadsheet)
        btnCreateSpreadsheet = findViewById(R.id.btn_create_spreadsheet)


        //Wywołanie funkcji
        btnReadSpreadsheet.setOnClickListener {
            val readSpreadsheetIntent = Intent(this, ReadSpreadsheetActivity::class.java)
            startActivity(readSpreadsheetIntent)
        }

        btnCreateSpreadsheet.setOnClickListener {
            val createSpreadsheetIntent = Intent(this, CreateSpreadsheetActivity::class.java)
            startActivity(createSpreadsheetIntent)
        }
    }

}
