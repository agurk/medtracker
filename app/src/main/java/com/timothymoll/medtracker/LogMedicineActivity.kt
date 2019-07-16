package com.timothymoll.medtracker

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_log_med.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LogMedicineActivity : AppCompatActivity() {

    private val newMedResponseCode = 1
    private lateinit var mtViewModel: MTViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_med)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mtViewModel = ViewModelProviders.of(this).get(MTViewModel::class.java)


        DoAsync {
            val query = MTRoomDatabase.getDatabase(application, CoroutineScope(Dispatchers.Main)).mtDAO().getAllMeds()


            val layout = findViewById<LinearLayout>(R.id.addMedLayout)

            query.forEach {
                val btn = Button(this)
                btn.text = it.name
                val id = it.id
                btn.setOnClickListener{
                    val replyIntent = Intent()
                    replyIntent.putExtra(EXTRA_REPLY, id.toString())
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
                layout.addView(btn)
            }

        }

        addMed.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.timothymoll.android.mt.loggedMed."
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMedResponseCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val newMed = MedDetails(
                    data.getStringExtra(AddMedicineActivity.MED_NAME),
                    data.getStringExtra(AddMedicineActivity.MED_AMOUNT).toInt()
                )
                mtViewModel.addMedicine(newMed)
            }
        }
    }

}

class DoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}