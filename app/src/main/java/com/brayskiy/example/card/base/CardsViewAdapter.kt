package com.brayskiy.example.card.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

/**
 * Created by brayskiy on 10/23/18.
 */

class CardsViewAdapter @JvmOverloads constructor(private val context: Context,
                                                 private val cards: ArrayList<BaseCard<*, *, RecyclerView.ViewHolder>> = ArrayList())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardClass = CardType.getType(viewType).cardClass

        for (card in cards) {
            if (card.javaClass.isAssignableFrom(cardClass)) {
                val view = LayoutInflater.from(context).inflate(card.layoutId, parent, false)

                return card.onCreateViewHolder(view)
            }
        }

        val view = LayoutInflater.from(context).inflate(cards[0].layoutId, parent, false)
        return cards[0].onCreateViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        cards[position].onBindViewHolder(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).cardType.ordinal
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun addCardsList(cardsList: List<BaseCard<*, *, RecyclerView.ViewHolder>>) {
        cards.addAll(cardsList)
        notifyDataSetChanged()
    }

    fun addCardsList(position: Int, cardsList: List<BaseCard<*, *, RecyclerView.ViewHolder>>) {
        cards.addAll(position, cardsList)
        notifyDataSetChanged()
    }

    fun addCard(card: BaseCard<*, *, RecyclerView.ViewHolder>) {
        addCard(cards.size, card)
    }

    fun addCard(position: Int, card: BaseCard<*, *, RecyclerView.ViewHolder>) {
        cards.add(position, card)
        notifyItemInserted(position)
    }

    fun removeCardAt(position: Int) {
        cards.removeAt(position)
        notifyItemRemoved(position)
    }

    fun replaceCardAt(position: Int, card: BaseCard<*, *, RecyclerView.ViewHolder>) {
        cards.removeAt(position)
        cards.add(position, card)
        notifyItemRemoved(position)
    }

    fun removeCardsBetween(start: Int, end: Int) {
        var lastCard = end
        if (start < 0 || lastCard > itemCount) {
            throw IndexOutOfBoundsException()
        }

        while (start < lastCard) {
            removeCardAt(start)
            lastCard--
        }
    }

    fun clearAllCards() {
        cards.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): BaseCard<*, *, RecyclerView.ViewHolder> {
        return cards[position]
    }

    fun getCards(): List<BaseCard<*, *, RecyclerView.ViewHolder>> {
        return cards
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        if (holder.adapterPosition > -1) {
            cards[holder.adapterPosition].onViewRecycled(holder)
        }
    }
}
