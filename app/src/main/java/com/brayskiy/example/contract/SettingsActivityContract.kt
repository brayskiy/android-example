package com.brayskiy.example.contract

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import com.brayskiy.example.base.BaseHandler
import com.brayskiy.example.base.IContract
import com.brayskiy.example.card.HostItemHandler
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.dagger.IoSched
import com.brayskiy.example.dagger.UiSched
import com.brayskiy.example.models.HostItem
import com.brayskiy.example.models.NetworkInfo
import com.brayskiy.example.rest.MobileService
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.net.InetAddress
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

interface SettingsActivityContract {
    interface IHandler : IContract.Handler<IAdapter> {
        fun sniffNetwork()
    }

    interface IAdapter : IContract.Adapter {
        fun onSnifferSuccess(result: NetworkInfo, provider: CardHandlerProvider<HostItemHandler>)
        fun onSnifferError(throwable: Throwable)
        fun onCardClicked(hostItem: HostItem)
    }

    class Handler @Inject internal constructor(private val application: Application,
                                               private val sharedPreferences: SharedPreferences,
                                               private val mobileService: MobileService,
                                               private val gson: Gson,
                                               @IoSched private val ioScheduler: Scheduler,
                                               @UiSched private val uiScheduler: Scheduler)
        : BaseHandler<IAdapter>(), IHandler, HostItemHandler {

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun sniffNetwork() {
            adapter?.showProgress()
            Observable.just(1)
                .flatMap { sniffer() }
                .observeOn(uiScheduler)
                .subscribeOn(ioScheduler)
                .subscribe(object: Observer<NetworkInfo> {
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(result: NetworkInfo) {
                        adapter?.hideProgress()
                        adapter?.onSnifferSuccess(result, provider)
                    }

                    override fun onError(throwable: Throwable) {
                        adapter?.hideProgress()
                        adapter?.onSnifferError(throwable)
                    }
                })
        }

        private fun sniffer(): Observable<NetworkInfo> {
            val allHosts = ArrayList<HostItem>()
            val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            val wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val connectionInfo = wifiManager.connectionInfo
            val myIpAddress = connectionInfo.ipAddress
            val myIpString = Formatter.formatIpAddress(myIpAddress)

            val prefix = myIpString.substring(0, myIpString.lastIndexOf(".") + 1)

            for (index in 0..254) {
                val testIp = prefix + index.toString()

                val address = InetAddress.getByName(testIp)
                val reachable = true //address.isReachable(100)
                val hostName = address.hostName

                if (!hostName.contains(prefix)) {
                    allHosts.add(HostItem(hostName, testIp, address, reachable))
                }
            }

            return Observable.just(
                NetworkInfo(activeNetwork.extraInfo, activeNetwork.isConnected, myIpString, allHosts))
        }

        override fun onCardClicked(position: Int, hostItem: HostItem) {
            adapter?.onCardClicked(hostItem)
        }

        private val provider: CardHandlerProvider<HostItemHandler> = CardHandlerProvider(this@Handler)
    }
}
