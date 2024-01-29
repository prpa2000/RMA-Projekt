package com.example.klubovi

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class Add : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var trophies:EditText
    private lateinit var year:EditText
    private lateinit var curl: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        name = findViewById(R.id.nameTxt)
        trophies = findViewById(R.id.trophiesTxt)
        year = findViewById(R.id.yearTxt)
        curl = findViewById<EditText>(R.id.imgUrl)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnAdd.setOnClickListener {
            insertData()
            clearAll()

        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun insertData() {
        val map = HashMap<String, Any>()
        map["name"] = name.text.toString()
        map["trophies"] = trophies.text.toString()
        map["year"] = year.text.toString()
        map["curl"] = curl.text.toString()

       if(TextUtils.isEmpty(name.text) || TextUtils.isEmpty(trophies.text) || TextUtils.isEmpty(year.text) || TextUtils.isEmpty(curl.text)){
           Toast.makeText(this@Add, "Enter all fields", Toast.LENGTH_SHORT).show()
       }
        else {
           FirebaseDatabase.getInstance().getReference().child("clubs").push()
               .setValue(map)
               .addOnSuccessListener {

                   Toast.makeText(this@Add, "Successful", Toast.LENGTH_SHORT).show()
               }
               .addOnFailureListener { e ->
                   Toast.makeText(this@Add, "Error", Toast.LENGTH_SHORT).show()
               }
       }
    }

    private fun clearAll(){
        if(TextUtils.isEmpty(name.text) || TextUtils.isEmpty(trophies.text) || TextUtils.isEmpty(year.text) || TextUtils.isEmpty(curl.text)){
            Toast.makeText(this@Add, "Enter all fields", Toast.LENGTH_SHORT).show()
        }
        else {
            name.setText("")
            trophies.setText("")
            year.setText("")
            curl.setText("")
        }
    }
}
