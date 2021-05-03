package com.teddy.brunch

import android.app.Application
import com.teddy.binder.appMainModules
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BrunchApplication : Application() {

    @FlowPreview
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BrunchApplication)
            modules(appMainModules)
        }
    }
}