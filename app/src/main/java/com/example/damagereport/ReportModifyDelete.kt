package com.example.damagereport

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReportModifyDelete : AppCompatActivity() {

    private var position = 0
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var propertyTypeEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var causeEditText: EditText
    private lateinit var severityEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var statusEditText: EditText
    private lateinit var myDatabase: MyDatabase
    private var reportId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_report_modify_delete)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateEditText = findViewById(R.id.dateEditText)
        timeEditText = findViewById(R.id.timeEditText)
        propertyTypeEditText = findViewById(R.id.propertyTypeEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        causeEditText = findViewById(R.id.causeEditText)
        severityEditText = findViewById(R.id.severityEditText)
        locationEditText = findViewById(R.id.locationEditText)
        statusEditText = findViewById(R.id.txtInputStatus)

        myDatabase = MyDatabase(this)

        position = intent.getIntExtra("position", 0)
        val report = Data.listReport[position]
        reportId = report.id

        dateEditText.setText(report.damageDate)
        timeEditText.setText(report.damageTime)
        propertyTypeEditText.setText(report.damageType)
        descriptionEditText.setText(report.damageDescription)
        causeEditText.setText(report.damageProbableCause)
        severityEditText.setText(report.damageSeverity)
        locationEditText.setText(report.damageLocation)
        statusEditText.setText(report.damageStatus)
    }

    fun modifyReport(view: View) {
        val updatedReport = Report(
            id = reportId,
            damageDate = dateEditText.text.toString(),
            damageTime = timeEditText.text.toString(),
            damageLocation = locationEditText.text.toString(),
            damageType = propertyTypeEditText.text.toString(),
            damageDescription = descriptionEditText.text.toString(),
            damageSeverity = severityEditText.text.toString(),
            damageStatus = statusEditText.text.toString(),
            damageProbableCause = causeEditText.text.toString(),
            idUser = Data.listReport[position].idUser
        )

        myDatabase.updateReport(updatedReport)
        Data.listReport[position] = updatedReport
        Toast.makeText(this, "Report updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun deleteReport(view: View) {
        myDatabase.deleteReport(reportId)
        Data.listReport.removeAt(position)
        Toast.makeText(this, "Report deleted successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}