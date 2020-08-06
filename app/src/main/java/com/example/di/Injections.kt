package com.example.di

import android.app.Application
import androidx.room.Room
import com.example.cloud.CloudApi
import com.example.local.AppDatabase
import com.example.repo.PopularMoviesRepository
import com.example.ui.PopularMoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initInjections(application: Application) {

	val viewModels = module {
		viewModel { PopularMoviesViewModel(get()) }
	}

	val repository = module {
		single {
			Room.databaseBuilder(get(), AppDatabase::class.java, "database.sqlite")
				.build()
		}
		single { PopularMoviesRepository(get(), get()) }

	}

	val services = module {
		single { CloudApi() }
	}

	startKoin {
		androidContext(application)
		modules(viewModels, services, repository)
	}

}
