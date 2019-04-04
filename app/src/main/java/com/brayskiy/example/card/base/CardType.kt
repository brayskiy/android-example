package com.brayskiy.example.card.base

import com.brayskiy.example.card.HostCard
import com.brayskiy.example.card.MovieSummaryCard

/**
 * Created by brayskiy on 01/31/19.
 */

enum class CardType constructor(val cardClass: Class<out BaseCard<*, *, *>>) {
    INVALID(BaseCard::class.java),
    HOST_ITEM(HostCard::class.java),
    POPULAR_MOVIES_ITEM(MovieSummaryCard::class.java);

    companion object {

        fun getType(ind: Int): CardType {
            return if (ind < 1 || ind >= values().size) INVALID else values()[ind]
        }
    }
}
