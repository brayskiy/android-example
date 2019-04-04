package com.brayskiy.example.card.base

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

import android.view.View

/**
 * Created by brayskiy on 6/14/18.
 */

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val resources: Resources
        get() = itemView.resources
}
