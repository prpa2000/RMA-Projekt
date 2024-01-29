package com.example.klubovi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(applicationContext, Start::class.java)
            startActivity(intent)
            finish()
        }
        val options: FirebaseRecyclerOptions<MainModel> = FirebaseRecyclerOptions.Builder<MainModel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("clubs"), MainModel::class.java)
            .build()

        mainAdapter = MainAdapter(options)
        recyclerView.adapter = mainAdapter
        floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener{
            startActivity(Intent(applicationContext, Add::class.java))

        }
    }

    override fun onStart() {
        super.onStart()
        mainAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mainAdapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val item: MenuItem = menu!!.findItem(R.id.search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                txtSearch(query!!);
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                txtSearch(query!!)
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    private fun txtSearch(str: String) : Unit {
        val options: FirebaseRecyclerOptions<MainModel> = FirebaseRecyclerOptions.Builder<MainModel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("clubs").orderByChild("name").startAt(str).endAt(str + "~"), MainModel::class.java)
            .build()
        mainAdapter = MainAdapter(options)
        mainAdapter.startListening()
        recyclerView.adapter = mainAdapter

    }
}

