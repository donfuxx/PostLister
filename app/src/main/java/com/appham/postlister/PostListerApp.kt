package com.appham.postlister

import android.app.Application

class PostListerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: PostListerApp
    }
}