package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import android.os.Environment
import java.io.File

object DataManager {

    fun cleanInternalCache(context: Context) {
        deleteFilesByDirectory(context.cacheDir)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.externalCacheDir);
        }
    }

    // this function is fit for the Android version under Android11
    fun deleteFilesByDirectory(directory: File?) {
        directory?.also {
            if (directory.exists() && directory.isDirectory) {
                directory.listFiles().forEach {
                    it.delete()
                }
            }
        }
    }
}