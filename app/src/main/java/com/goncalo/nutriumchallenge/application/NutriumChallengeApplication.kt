package com.goncalo.nutriumchallenge.application

import android.app.Application
import com.goncalo.nutriumchallenge.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NutriumChallengeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NutriumChallengeApplication)
            modules(module)
        }
    }

}