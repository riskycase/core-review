package com.riskycase.corereview.ui

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.riskycase.corereview.R
import com.riskycase.corereview.login.SignUpActivity
import com.squareup.picasso.Picasso

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(findViewById(R.id.tool_bar))

        val user = Firebase.auth.currentUser!!

        Picasso.get().load(user.photoUrl).into(findViewById<ImageView>(R.id.user_photo))
        findViewById<TextView>(R.id.user_name).text = user.displayName
        findViewById<TextView>(R.id.user_email).text = user.email

        findViewById<Button>(R.id.sign_out_button).setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

    }
}