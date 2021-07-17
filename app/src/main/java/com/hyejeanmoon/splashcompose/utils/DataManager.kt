package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import android.os.Build
import android.os.Environment
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