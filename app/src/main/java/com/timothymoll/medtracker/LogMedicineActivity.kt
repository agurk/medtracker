package com.timothymoll.medtracker

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity;
import com.example.medtracker.R

import kotlinx.android.synthetic.main.activity_add_med.*
import kotlinx.android.synthetic.main.activity_add_med.toolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class logMedicineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_med)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        doAsync {
            val query = MTRoomDatabase.getDatabase(application, CoroutineScope(Dispatchers.Main)).mtDAO().getAllMeds()


            val layout = findViewById<LinearLayout>(R.id.addMedLayout)

            query.forEach {
                val btn = Button(this)

                btn.setText("foo".toCharArray(), 0, 3)
                layout.addView(btn)
            }

        }


        val button = findViewById<Button>(R.id.add_0)
        button.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_REPLY, "10mg")
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }

        val intent = Intent(this, logMedicineActivity::class.java)
        addMed.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

}

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}