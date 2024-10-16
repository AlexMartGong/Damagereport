package com.example.damagereport

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewReport : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_report)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = MyDatabase(this)
    }

    fun saveReport(v: View) {
        val dateEditText = findViewById<EditText>(R.id.dateEditText)
        val timeEditText = findViewById<EditText>(R.id.timeEditText)
        val propertyTypeEditText = findViewById<EditText>(R.id.propertyTypeEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val causeEditText = findViewById<EditText>(R.id.causeEditText)
        val severityEditText = findViewById<EditText>(R.id.severityEditText)
        val locationEditText = findViewById<EditText>(R.id.locationEditText)

        val damageDate = dateEditText.text.toString()
        val damageTime = timeEditText.text.toString()
        val damageType = propertyTypeEditText.text.toString()
        val damageDescription = descriptionEditText.text.toString()
        val damageProbableCause = causeEditText.text.toString()
        val damageSeverity = severityEditText.text.toString()
        val damageLocation = locationEditText.text.toString()

        if (damageDate.isEmpty() || damageTime.isEmpty() || damageType.isEmpty() ||
            damageDescription.isEmpty() || damageProbableCause.isEmpty() ||
            damageSeverity.isEmpty() || damageLocation.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val report = Report(
            id = 0,
            damageDate = damageDate,
            damageTime = damageTime,
            damageLocation = damageLocation,
            damageType = damageType,
            damageDescription = damageDescription,
            damageSeverity = damageSeverity,
            damageStatus = "Earring",
            damageProbableCause = damageProbableCause,
            idUser = 1
        )

        dbHelper.insertReport(report)
        finish()

    }
}