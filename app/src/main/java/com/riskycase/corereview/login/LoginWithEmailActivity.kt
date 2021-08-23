package com.riskycase.corereview.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riskycase.corereview.MainActivity
import com.riskycase.corereview.R

class LoginWithEmailActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_email)

        auth = Firebase.auth
        userEmail = ""
        userPassword = ""

        findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            auth.signInWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        findViewById<TextInputEditText>(R.id.email_address).doOnTextChanged { text, _, _, _ ->
            userEmail = text.toString()
        }

        findViewById<TextInputEditText>(R.id.password).doOnTextChanged { text, _, _, _ ->
            userPassword = text.toString()
        }

        findViewById<Button>(R.id.forgot_password).setOnClickListener {
            AlertDialog.Builder(applicationContext)
                .setTitle("Reset password?")
                .setMessage("Send a password reset email to $userEmail?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.sendPasswordResetEmail(userEmail)
                }
                .setNegativeButton("No"){ _, _ -> }

        }

    }
}