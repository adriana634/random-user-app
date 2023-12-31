package com.example.randomuser

import android.app.Application
import com.example.randomuser.di.DaggerRandomUserComponent
import com.example.randomuser.di.RandomUserComponent

class RandomUserApp: Application() {

    lateinit var randomUserComponent: RandomUserComponent

    override fun onCreate() {
        super.onCreate()

        randomUserComponent = DaggerRandomUserComponent.create()
    }
}
