package com.example.klubovi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java);
            startActivity(intent);
            finish();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextEmail = findViewById<TextInputEditText>(R.id.email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.password)
        val editTextConfirmPassword = findViewById<TextInputEditText>(R.id.confirm_password)
        val buttonReg = findViewById<Button>(R.id.btn_register)
        var textView = findViewById<TextView>(R.id.loginNow)
        progressBar = findViewById(R.id.progressBar)
        auth = Firebase.auth

        textView.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java);
            startActivity(intent);
            finish();
        }

        buttonReg.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()
            val confirmpassword: String = editTextConfirmPassword.text.toString();




            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@Register, "Enter all fields", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE

            }
            else {
                if(password.length < 6){

                    progressBar.visibility = View.GONE
                    Toast.makeText(this@Register, "The Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                if(confirmpassword != password) {

                    progressBar.visibility = View.GONE
                    Toast.makeText(this@Register, "Passwords does not match", Toast.LENGTH_SHORT).show()
                }
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@Register,
                                "Account created",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(applicationContext, Login::class.java);
                            startActivity(intent);
                            finish();

                        } else {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@Register,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }



        }

    }


}