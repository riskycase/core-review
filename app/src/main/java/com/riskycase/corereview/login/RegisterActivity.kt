package com.riskycase.corereview.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riskycase.corereview.MainActivity
import com.riskycase.corereview.R
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userPasswordConfirm: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.register).setOnClickListener {
            if (!verifyStrongPassword())
                Toast.makeText(applicationContext, "Password should be at least 8 characters long", Toast.LENGTH_SHORT).show()
            else if (!verifyPasswordSame())
                Toast.makeText(applicationContext, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            else {
                auth.createUserWithEmailAndPassword(
                    userEmail,
                    userPassword
                ).addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Account successfully created",
                        Toast.LENGTH_SHORT
                    ).show()
                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    if(it is FirebaseAuthUserCollisionException)
                        Toast.makeText(applicationContext, "This email is already registered", Toast.LENGTH_SHORT).show()
                }
            }
        }

        auth = Firebase.auth
        userEmail = ""
        userPassword = ""
        userPasswordConfirm = ""

        findViewById<TextInputEditText>(R.id.email_address).doOnTextChanged { text, _, _, _ ->
            userEmail = text.toString()
        }

        findViewById<TextInputEditText>(R.id.password).doOnTextChanged { text, _, _, _ ->
            userPassword = text.toString()
        }

        findViewById<TextInputEditText>(R.id.password_confirm).doOnTextChanged { text, _, _, _ ->
            userPasswordConfirm = text.toString()
        }
    }

    private fun verifyPasswordSame(): Boolean {
        return userPassword == userPasswordConfirm
    }

    private fun verifyStrongPassword(): Boolean {
        return Pattern.compile("\\S{8,}").matcher(userPassword as CharSequence).matches()
    }
}