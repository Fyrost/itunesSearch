package com.example.myapplication.ui.utils

import com.example.myapplication.data.db.entity.ITunesResult
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService



fun List<ITunesResult>.removeNull(): List<ITunesResult> {
    var tempResult: MutableList<ITunesResult> = mutableListOf()
    if (this.isNotEmpty()) {
        this.forEach { row ->
            if (!row.trackName.isNullOrBlank() && row.trackId != 0 && row.trackPrice != null && row.trackPrice > 0) {
                tempResult.add(row)
            }
        }
    }
    return tempResult
}