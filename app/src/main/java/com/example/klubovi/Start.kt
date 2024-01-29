package com.example.klubovi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text

class Start : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

       auth = FirebaseAuth.getInstance()


        var start = findViewById<Button>(R.id.start)
        var logout = findViewById<Button>(R.id.logout)
        var textview = findViewById<TextView>(R.id.user_details)
        var textView = findViewById<TextView>(R.id.textView)
        textView.text = "Football, or soccer as it is known in some parts of the world, is a globally beloved sport that unites people across cultures and continents. With its roots dating back centuries, the modern version of the game took shape in England during the 19th century. Share and expand your knowledge about football clubs!"
        user = auth.currentUser!!

        if (user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
            textview.text = user.email
        }
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        start.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}