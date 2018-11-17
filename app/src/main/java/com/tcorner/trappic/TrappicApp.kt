package com.tcorner.trappic

import android.app.Application
import com.tcorner.trappic.core.di.ApplicationComponent
import com.tcorner.trappic.core.di.DaggerApplicationComponent
import com.tcorner.trappic.core.di.ApplicationModule

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/13/2018.
 */
class TrappicApp : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}