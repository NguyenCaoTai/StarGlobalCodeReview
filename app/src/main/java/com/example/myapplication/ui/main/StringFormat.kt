package com.example.myapplication.ui.main

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R

object StringFormat {
    fun formatUserCardInfo(
        context: Context,
        upperContent: String,
        belowContent: String
    ): Spannable {
        return SpannableStringBuilder("${upperContent}\n${belowContent}")
            .apply {
                setSpan(
                    RelativeSizeSpan(1.2f),
                    0,
                    upperContent.length,
                    0
                )
                setSpan(
                    ForegroundColorSpan(
                        ResourcesCompat.getColor(
                            context.resources,
                            R.color.user_card_info_sub_content,
                            null
                        )
                    ),
                    0,
                    upperContent.length,
                    0
                )
                setSpan(
                    RelativeSizeSpan(2f),
                    upperContent.length,
                    length,
                    0
                )
                setSpan(
                    ForegroundColorSpan(
                        ResourcesCompat.getColor(
                            context.resources,
                            R.color.user_card_info_main_content,
                            null
                        )
                    ),
                    upperContent.length,
                    length,
                    0
                )
            }
    }
}