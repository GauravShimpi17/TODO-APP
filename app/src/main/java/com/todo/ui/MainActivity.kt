package com.todo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.todo.R
import com.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUserIdInput.text.toString()
            val password = binding.txtPasswordInput.text.toString()

            if (Credentials.validLogin(username,password)){
                Intent(this, ActivityHomePage::class.java).apply {
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(this)
                }
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    object Credentials {
        private const val validUsername: String = "test"
        private const val validPassword: String = "test123"


        fun validLogin(username: String, password: String) : Boolean {
            return username .contentEquals(validUsername) && password.contentEquals(validPassword)
        }
    }
}