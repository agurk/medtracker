package com.timothymoll.medtracker

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medtracker.R
import java.time.*


class MainActivity : AppCompatActivity() {

    private val newMTActivityRequestCode = 1
    private lateinit var mtViewModel: MTViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mtViewModel = ViewModelProviders.of(this).get(MTViewModel::class.java)

        val historyView = findViewById<RecyclerView>(R.id.HistoryDetailsView)
        val historyAdapter = TakenDetailsAdapter(this)
        historyView.adapter = historyAdapter
        historyView.layoutManager = LinearLayoutManager(this)
        mtViewModel.historyValues.observe(this, Observer { mt ->
            mt?.let { historyAdapter.setTimes(it) }
        })

        val currentView = findViewById<RecyclerView>(R.id.CurrentDetailsView)
        val currentAdapter = TakenDetailsAdapter(this)
        currentView.adapter = currentAdapter
        currentView.layoutManager = LinearLayoutManager(this)
        mtViewModel.currentValues.observe(this, Observer { mt ->
            mt?.let { currentAdapter.setTimes(it) }
        })


        val intent = Intent(this, AddMedActivity::class.java)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMedActivity::class.java)
            startActivityForResult(intent, newMTActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMTActivityRequestCode && resultCode == Activity.RESULT_OK) {
            System.out.println("Now adding")

            intentData?.let { data ->
                val word = MedTaken(
                    data.getStringExtra(AddMedActivity.EXTRA_REPLY),
                    ZonedDateTime.now().toString(),//.format(DateTimeFormatter.ISO_INSTANT),
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
