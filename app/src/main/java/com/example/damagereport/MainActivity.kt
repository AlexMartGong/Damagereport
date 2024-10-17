package com.example.damagereport

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var txtUser: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var myDatabase: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)

        myDatabase = MyDatabase(this)

    }

    fun home(v: View) {

        val username = txtUser.text.toString().trim()
        val password = txtPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }

        if (myDatabase.checkLogin(username, password)) {
            val intent = Intent(this, Navigation::class.java).apply {
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }

    }

    fun register(v: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    fun forgotPass(v: View) {
        val intent = Intent(this, PasswordRecovery::class.java)
        startActivity(intent)
    }

}