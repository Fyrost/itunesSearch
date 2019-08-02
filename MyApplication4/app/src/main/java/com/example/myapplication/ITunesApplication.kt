package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.ITunesApiService
import com.example.myapplication.data.db.ITunesDatabase
import com.example.myapplication.data.db.network.ConnectivityInterceptor
import com.example.myapplication.data.db.network.ConnectivityInterceptorImpl
import com.example.myapplication.data.db.network.ITunesNetworkDataSource
import com.example.myapplication.data.db.network.ITunesNetworkDataSourceImpl
import com.example.myapplication.data.repository.ITunesRepository
import com.example.myapplication.data.repository.ITunesRepositoryImpl
import com.example.myapplication.ui.itunes.browse.BrowseViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ITunesApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ITunesApplication))

        bind() from singleton { ITunesDatabase(instance()) }
        bind() from singleton { instance<ITunesDatabase>().iTunesResultDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ITunesApiService(instance()) }
        bind<ITunesNetworkDataSource>() with singleton { ITunesNetworkDataSourceImpl(instance()) }
        bind<ITunesRepository>() with singleton { ITunesRepositoryImpl(instance()) }
        bind() from provider { BrowseViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}