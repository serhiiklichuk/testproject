package com.example

import android.app.Application
import com.example.di.initInjections

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		initInjections(this)
	}
}