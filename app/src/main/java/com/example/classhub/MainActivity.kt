package com.example.classhub

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.classhub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

//        binding.txt.text = java.text.SimpleDateFormat("hh:mm", java.util.Locale.getDefault()).format(java.util.Date())

        binding.txt.text = java.time.LocalDate.now().dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault())


        binding.logOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}