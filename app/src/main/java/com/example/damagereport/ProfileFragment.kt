package com.example.damagereport

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {
    private lateinit var tvUserName: TextView
    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var btnLogout: MaterialButton
    private lateinit var btnModify: MaterialButton
    private lateinit var myDatabase: MyDatabase
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUserName = view.findViewById(R.id.tvUserName)
        etFullName = view.findViewById(R.id.etFullName)
        etEmail = view.findViewById(R.id.etEmail)
        etPhone = view.findViewById(R.id.etPhone)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnModify = view.findViewById(R.id.btnModify)

        myDatabase = MyDatabase(requireContext())

        // Get the username from arguments
        val username = arguments?.getString("USERNAME") ?: ""

        // Fetch user data and populate fields
        loadUserData(username)

        btnLogout.setOnClickListener {
            logout()
        }

        btnModify.setOnClickListener {
            modifyUserData()
        }
    }

    private fun loadUserData(username: String) {
        currentUser = myDatabase.getUser(username)
        currentUser?.let { user ->
            tvUserName.text = user.username
            etFullName.setText("${user.name} ${user.surname}")
            etEmail.setText(user.email)
            etPhone.setText(user.phone)
        }
    }

    private fun logout() {
        // Clear any session data if needed
        // Navigate back to login screen
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    private fun modifyUserData() {
        val fullName = etFullName.text.toString().split(" ")
        val email = etEmail.text.toString()
        val phone = etPhone.text.toString()

        if (fullName.size < 2 || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        currentUser?.let { user ->
            val updatedUser = User(
                id = user.id,
                username = user.username,
                name = fullName[0],
                surname = fullName.subList(1, fullName.size).joinToString(" "),
                email = email,
                password = user.password, // Keep the existing password
                phone = phone
            )

            if (myDatabase.updateUser(updatedUser)) {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                currentUser = updatedUser // Update the currentUser reference
                loadUserData(user.username) // Reload the updated data
            } else {
                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString("USERNAME", username)
                }
            }
    }
}