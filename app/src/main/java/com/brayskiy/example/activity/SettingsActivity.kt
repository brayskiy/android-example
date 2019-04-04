package com.brayskiy.example.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brayskiy.example.R
import com.brayskiy.example.base.BaseActivity
import com.brayskiy.example.card.HostCard
import com.brayskiy.example.card.HostItemHandler
import com.brayskiy.example.card.base.BaseCard
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.card.base.CardsViewAdapter
import com.brayskiy.example.contract.SettingsActivityContract
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.models.HostItem
import com.brayskiy.example.models.NetworkInfo
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

class SettingsActivity : BaseActivity<SettingsActivityContract.IAdapter, SettingsActivityContract.IHandler>(),
    SettingsActivityContract.IAdapter {
    override var layoutId = R.layout.activity_settings

    private var injector: ActivityComponent? = null

    lateinit var picasso: Picasso @Inject set

    private lateinit var hostsCount: TextView
    private lateinit var hostsList: RecyclerView

    override fun setupActivity(activityComponent: ActivityComponent, savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        injector = activityComponent

        hostsCount = findViewById(R.id.hosts_count)

        hostsList = findViewById(R.id.hosts_list)
        hostsList.setItemViewCacheSize(0)
        hostsList.layoutManager = LinearLayoutManager(this)

        handler.onAddViewAdapter(this, savedInstanceState)

        handler.sniffNetwork()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSnifferSuccess(result: NetworkInfo, provider: CardHandlerProvider<HostItemHandler>) {
        if (result.allHosts.isEmpty()) {
            hostsCount.visibility = View.VISIBLE
            hostsList.visibility = View.GONE
            hostsCount.text = getText(R.string.no_active_settings)
        } else {
            hostsCount.visibility = View.GONE
            hostsList.visibility = View.VISIBLE

            val cardsList = ArrayList<BaseCard<*, *, RecyclerView.ViewHolder>>()
            result.allHosts.forEach {
                val card = HostCard(it, picasso, injector!!, provider)
                cardsList.add(card as BaseCard<*, *, RecyclerView.ViewHolder>)
            }

            if (hostsList.adapter == null) {
                hostsList.adapter = CardsViewAdapter(this, cardsList)
            } else {
                (hostsList.adapter as CardsViewAdapter).addCardsList(cardsList)
            }
        }
    }

    override fun onSnifferError(throwable: Throwable) {
        val dialogData = DialogData(DialogData.Type.ALERT)
        dialogData.titleResId = R.string.error_title
        dialogData.messageResId =  R.string.error_settings
        dialogData.positiveCallback = Runnable { finish() }
        displayDialog(dialogData)
    }

    override fun onCardClicked(hostItem: HostItem) {
        val dialogData = DialogData(DialogData.Type.ALERT)
        dialogData.titleResId = R.string.not_implemented_title
        dialogData.messageResId =  R.string.not_implemented_message
        displayDialog(dialogData)
    }
}
