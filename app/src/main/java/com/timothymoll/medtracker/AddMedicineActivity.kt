package com.timothymoll.medtracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AddMedicineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        val button = findViewById<Button>(R.id.addMedButton)
        button.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(MED_NAME, findViewById<TextView>(R.id.addMedName).text.toString())
            replyIntent.putExtra(MED_AMOUNT, findViewById<TextView>(R.id.addMedAmount).text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }
    companion object {
        const val MED_NAME = "com.timothymoll.android.mt.addMedName"
        const val MED_AMOUNT = "com.timothymoll.android.mt.addMedAmount"
    }
}
