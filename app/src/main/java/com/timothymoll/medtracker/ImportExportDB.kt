package com.timothymoll.medtracker

import android.content.Context
import java.io.File

class ImportExportDB {

    private val filename = "exported_values"

    fun exportDB (mtdao: MTDAO, context: Context) : Boolean {
        val file = File(context.getExternalFilesDir(null), filename)
        file.createNewFile()

        DoAsync {
            file.printWriter().use { out ->
                mtdao.exportAllValues().forEach {
                    out.println(it.toString())
                }
            }
        }
        return true
    }

}