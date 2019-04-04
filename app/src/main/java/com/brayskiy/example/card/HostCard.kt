package com.brayskiy.example.card

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.widget.TextView

import com.brayskiy.example.R
import com.brayskiy.example.card.base.BaseCard
import com.brayskiy.example.card.base.BaseViewHolder
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.card.base.CardType
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.models.HostItem
import com.squareup.picasso.Picasso

/**
 * Created by brayskiy on 01/31/19.
 */

class HostCard(hostItem: HostItem, private val picasso: Picasso, injector: ActivityComponent,
               cardHandlerProvider: CardHandlerProvider<HostItemHandler>)
    : BaseCard<HostItem, HostItemHandler, HostCard.ViewHolder>(R.layout.host_item, hostItem, cardHandlerProvider) {

    override val cardType: CardType
        get() = CardType.HOST_ITEM

    init {
        injector.inject(this)
    }

    override fun onCreateViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun updateCardViews() {
        viewHolder?.hostName?.text = cardData.hostName
        viewHolder?.hostIpAddress?.text = cardData.ipAddress

        viewHolder?.hostContainer?.setOnClickListener(({ this.onCardClicked(it) }))
    }

    private fun onCardClicked(view: View) {
        cardHandler.onCardClicked(viewHolder!!.adapterPosition, cardData)
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val hostContainer = itemView.findViewById<ConstraintLayout>(R.id.host_container)
        val hostName = itemView.findViewById<TextView>(R.id.host_name)
        val hostIpAddress = itemView.findViewById<TextView>(R.id.host_ip_address)
    }
}
