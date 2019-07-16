package com.timothymoll.medtracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime
import java.time.ZonedDateTime


class MainActivity : AppCompatActivity() {

    private val newMTActivityRequestCode = 1
    private lateinit var mtViewModel: MTViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mtViewModel = ViewModelProviders.of(this).get(MTViewModel::class.java)

        val currentView = findViewById<RecyclerView>(R.id.CurrentView)
        val currentAdapter = CurrentAdapter(this)
        currentView.adapter = currentAdapter
        currentView.layoutManager = LinearLayoutManager(this)
        mtViewModel.currentValues.observe(this, Observer { mt ->
            mt?.let { currentAdapter.setTimes(it) }
        })


        val historyView = findViewById<RecyclerView>(R.id.HistoryDetailsView)
        val historyAdapter = TakenDetailsAdapter(this)
        historyView.adapter = historyAdapter
        historyView.layoutManager = LinearLayoutManager(this)
        mtViewModel.historyValues.observe(this, Observer { mt ->
            mt?.let { historyAdapter.setTimes(it) }
        })

        val currentDetailsView = findViewById<RecyclerView>(R.id.CurrentDetailsView)
        val currentDetailsAdapter = TakenDetailsAdapter(this)
        currentDetailsView.adapter = currentDetailsAdapter
        currentDetailsView.layoutManager = LinearLayoutManager(this)
        mtViewModel.currentValues.observe(this, Observer { mt ->
            mt?.let { currentDetailsAdapter.setTimes(it) }
        })


        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, LogMedicineActivity::class.java)
            startActivityForResult(intent, newMTActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMTActivityRequestCode && resultCode == Activity.RESULT_OK) {

            intentData?.let { data ->
                val word = MedTaken(
                    with(data.getStringExtra(LogMedicineActivity.EXTRA_REPLY)) { this.toInt() },
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
        return when (item.itemId) {
            R.id.action_export_db -> {
                val iedb = ImportExportDB()
                val success = iedb.exportDB(MTRoomDatabase.getDatabase(application, CoroutineScope(Dispatchers.Main)).mtDAO(), applicationContext)
                if (success) {
                    Toast.makeText(applicationContext, "exported ok", Toast.LENGTH_LONG).show()
                    true
                } else {
                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_LONG).show()
                    false
                }


            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
