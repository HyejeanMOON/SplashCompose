package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.hyejeanmoon.splashcompose.R
import java.io.File

object DataManager {

    fun clearCacheInScopedStorage(context: Context) {
        val cacheDir = context.cacheDir
        deleteCacheFile(cacheDir)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                context.externalCacheDir?.also {
                    deleteCacheFile(it)
                }
            }
        }
        Toast.makeText(context,context.getString(R.string.toast_clear_cache_completed),Toast.LENGTH_LONG).show()
    }

    private fun deleteCacheFile(file: File) {
        file.listFiles().forEach {
            if (it.isDirectory) {
                deleteCacheFile(it)
            } else {
                it.delete()
            }
        }
    }
}