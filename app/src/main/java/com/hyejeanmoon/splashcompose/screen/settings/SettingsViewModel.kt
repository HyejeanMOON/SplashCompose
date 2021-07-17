package com.hyejeanmoon.splashcompose.screen.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hyejeanmoon.splashcompose.OrderBy
import com.hyejeanmoon.splashcompose.Resolution
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SettingsViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val pref = SharedPreferencesUtils(app)

    val displayResolutionList: List<String> = listOf(
//        Resolution.THUMB.name,
        Resolution.SMALL.name,
        Resolution.REGULAR.name,
//        Resolution.FULL.name,
//        Resolution.RAW.name
    )

    val downloadResolutionList: List<String> = listOf(
        Resolution.THUMB.name,
        Resolution.SMALL.name,
        Resolution.REGULAR.name,
        Resolution.FULL.name,
        Resolution.RAW.name
    )

    val orderByList: List<String> = listOf(
        OrderBy.LATEST.name,
        OrderBy.OLDEST.name,
        OrderBy.POPULAR.name
    )

    fun getDisplayResolutionPosition(): Int {
        val resolution = pref.getString(
            SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION
        )
        var position = 0
        displayResolutionList.forEachIndexed { index, s ->
            if(s == resolution) position = index
        }
        return position
    }

    fun putDisplayResolution(data: String) {
        pref.putString(
            SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION,
            data
        )
    }

    fun getOrderByPosition(): Int {
        val orderBy = pref.getString(
            SharedPreferencesUtils.KEY_ORDER_BY
        )
        var position = 0
        orderByList.forEachIndexed { index, s ->
            if (s == orderBy) position = index
        }

        return position
    }

    fun putOrderBy(data: String) {
        pref.putString(
            SharedPreferencesUtils.KEY_ORDER_BY,
            data
        )
    }
}