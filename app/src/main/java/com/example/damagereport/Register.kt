package com.example.damagereport

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Register : AppCompatActivity() {

    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = MyDatabase(this)
    }

    fun registerUser(view: View) {
        val name = findViewById<TextInputEditText>(R.id.txtName).text.toString()
        val surname = findViewById<TextInputEditText>(R.id.txtFullSurname).text.toString()
        val email = findViewById<TextInputEditText>(R.id.txtEmail).text.toString()
        val phone = findViewById<TextInputEditText>(R.id.txtPhone).text.toString()
        val username = findViewById<TextInputEditText>(R.id.txtUsername).text.toString()
        val password = findViewById<TextInputEditText>(R.id.txtPassword).text.toString()
        val confirmPassword = findViewById<TextInputEditText>(R.id.txtConfirmPassword).text.toString()

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() ||
            username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (database.isUsernameTaken(username)) {
            Toast.makeText(this, "Username already exists. Please choose a different one.", Toast.LENGTH_LONG).show()
            return
        }

        val newUser = User(
            id = 0,
            username = username,
            name = name,
            surname = surname,
            email = email,
            password = password,
            phone = phone
        )

        val result = database.registerUser(newUser)
        if (result != -1L) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }
}