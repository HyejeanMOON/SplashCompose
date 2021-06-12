package com.hyejeanmoon.splashcompose.screen.photodetail

import android.app.NotificationManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hyejeanmoon.splashcompose.screen.photos.PhotosApiService

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters,
    private val downloadService: PhotosApiService,
    private val notificationManager: NotificationManager,
    private val photoId: String
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}