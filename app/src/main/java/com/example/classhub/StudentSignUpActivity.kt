package com.example.classhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.classhub.databinding.ActivityStudentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class StudentSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logIn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUpWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(
                    this,
                    "Please enter both email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun signUpWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    Toast.makeText(
                        this,
                        "User Creation failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent);
    }
}