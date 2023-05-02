package com.example.carbooking

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.WHITE
        }

        val forgot_password_reset: Button = findViewById(R.id.forgot_password_reset)
        val forgot_password_email: EditText = findViewById(R.id.forgot_password_email)


        forgot_password_reset.setOnClickListener{
            val email = forgot_password_email.text.toString()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                task -> if (task.isSuccessful){
                    Toast.makeText(this, "Reset link sent successfully" ,Toast.LENGTH_LONG).show()
                finish()
            }
            }
        }
    }
}