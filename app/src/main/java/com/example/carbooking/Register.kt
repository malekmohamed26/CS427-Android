package com.example.carbooking

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        val first_name : EditText = findViewById(R.id.first_name)
        val email_register : EditText = findViewById(R.id.email_register)
        val password_register : EditText = findViewById(R.id.password_register)
        val password_confirm_register : EditText = findViewById(R.id.confirm_password_register)
        val google_register_button: Button = findViewById(R.id.google_sign_up)
        val register_button : Button = findViewById(R.id.register_button)

        val email_error_register : TextView = findViewById(R.id.email_error_register)
        val password_error_register : TextView = findViewById(R.id.password_error_register)
        val password_confirm_register_error : TextView = findViewById(R.id.confirm_password_register_error)

        // Initialize Firebase Auth
        auth = Firebase.auth

        /*-----------------------------------------------------------------------------------------------------*/

        register_button.setOnClickListener {
            // class to add values in the database
            //val values = ContentValues()

            email_error_register.visibility = View.INVISIBLE

            /*-------------------------------------------------------------------------------------------------------*/
            //Validation of email
            if (isEmailValid(email_register)
                && !TextUtils.isEmpty(email_register.text.toString())
            ) {
                email_error_register.visibility = View.GONE
            }
            else if (!isEmailValid(email_register)
                && !TextUtils.isEmpty(email_register.text.toString())
            ) {
                email_error_register.visibility = View.VISIBLE
                email_error_register.setText("Make sure that your email address is gmail, icloud, yahoo, hotmail, or msn to register")
            }
            else {
                email_error_register.visibility = View.VISIBLE
                email_error_register.setText("Hmm! Seems that your forgot entering your email")
            }

            /*-------------------------------------------------------------------------------------------------------*/

            //Validation of password
            if (passwordValidation(password_register)
                && !TextUtils.isEmpty(password_register.text.toString())
            ) {
                password_error_register.visibility = View.GONE
            } else {
                password_error_register.visibility = View.VISIBLE
            }

            /*-------------------------------------------------------------------------------------------------------*/

            //Confirm password validation
            if (password_confirm_register.text.toString() == password_register.text.toString())
            {
                password_confirm_register_error.visibility = View.GONE
            }
            else
                password_confirm_register_error.visibility = View.VISIBLE

            if ((isEmailValid(email_register))
                && !TextUtils.isEmpty(email_register.text.toString())
                && !TextUtils.isEmpty(password_register.text.toString())
                && (passwordValidation(password_register))
                && (password_confirm_register.text.toString() == password_register.text.toString())
            ) {
                auth.createUserWithEmailAndPassword(email_register.text.toString(),
                    password_register.text.toString()).addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Account created successfully", Toast.LENGTH_LONG).show()
                            //returning with email to login screen
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        } else{
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }

    fun isEmailValid(email: EditText): Boolean {
        return (Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                && (email.text.toString().contains("gmail")
                || email.text.toString().contains("icloud")
                || email.text.toString().contains("yahoo")
                || email.text.toString().contains("hotmail")
                || email.text.toString().contains("msn")))
    }

    fun passwordValidation(password: EditText) : Boolean {
        return (password.text.toString().length >= 8
                && password.text.toString().contains(".*[A-Z].*".toRegex())
                && password.text.toString().contains(".*[0-9].*".toRegex())
                && password.text.toString().contains(".*[~`!@#$%^&|*()_+=*-/?><.,;:].*".toRegex())
                )
    }
}