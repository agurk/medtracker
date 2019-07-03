package com.timothymoll.medtracker

import android.app.Activity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.icu.util.Calendar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medtracker.R
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    private val newMTActivityRequestCode = 1
    private lateinit var mtViewModel: MTViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MTListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        mtViewModel = ViewModelProviders.of(this).get(MTViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mtViewModel.allValues.observe(this, Observer { mt ->
            // Update the cached copy of the words in the adapter.
            mt?.let { adapter.setTimes(it) }
        })

        val intent = Intent(this, AddMed::class.java)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMed::class.java)
            startActivityForResult(intent, newMTActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMTActivityRequestCode && resultCode == Activity.RESULT_OK) {
            System.out.println("Now adding")

            intentData?.let { data ->
                val word = MedTaken(
                    data.getStringExtra(AddMed.EXTRA_REPLY),
                    LocalDateTime.now().toString()
                )
                mtViewModel.insert(word)
            }
//        } else {
//            Toast.makeText(
//                applicationContext,
//                R.string.empty_not_saved,
//                Toast.LENGTH_LONG
//            ).show()
       }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
