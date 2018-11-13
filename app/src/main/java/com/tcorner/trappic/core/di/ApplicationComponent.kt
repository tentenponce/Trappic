package com.tcorner.trappic.core.di

import com.tcorner.trappic.TrappicApp
import com.tcorner.trappic.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: TrappicApp)
}
