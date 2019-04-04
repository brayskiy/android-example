package com.brayskiy.example.card.base

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

import android.view.View

/**
 * Created by brayskiy on 01/31/19.
 */

abstract class BaseCard<T1, T2, T3 : RecyclerView.ViewHolder>(@param:LayoutRes val layoutId: Int, data: T1,
                                                              private val cardHandlerProvider: CardHandlerProvider<T2>) {
    var cardData: T1 internal set

    var viewHolder: T3? = null

    abstract val cardType: CardType

    val cardHandler: T2 get() = cardHandlerProvider.cardHandler

    abstract fun onCreateViewHolder(view: View): T3

    abstract fun updateCardViews()

    abstract fun onViewRecycled(viewHolder: RecyclerView.ViewHolder)

    init {
        cardData = data
    }

    fun onBindViewHolder(holder: T3) {
        viewHolder = holder
        updateCardViews()
    }
}
