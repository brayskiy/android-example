package com.brayskiy.example.card

import com.brayskiy.example.models.HostItem

/**
 * Created by brayskiy on 01/31/19.
 */

interface HostItemHandler {
    fun onCardClicked(position: Int, hostItem: HostItem)
}
