package com.wakuei.githubusersearch

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // modules
            val list = listOf(myModule, repoModule)
            modules(list)
        }
    }
}