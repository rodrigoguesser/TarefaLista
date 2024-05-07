package com.example.tasklist.ui.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tasklist.R

data class AppUiState(
    @StringRes val title: Int = R.string.tarefa_list,
    @DrawableRes val fabIcon : Int = R.drawable.baseline_content_paste_go_24,
    @StringRes val iconContentDescription: Int = R.string.insert_new_tarefa,
)
