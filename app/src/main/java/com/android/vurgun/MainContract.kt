package com.android.vurgun

import com.android.vurgun.common.core.CoreState

class MainContract {

    data class State(
        override val isLoading: Boolean,
        val keepSplashScreenOn: Boolean = true,
    ) : CoreState.ViewState

    sealed class Event : CoreState.Event {
        data object NavigateToHome : Event()
    }


    object Static {
        const val KEEP_SPLASH_SCREEN_DELAY: Long = 2000L
    }
}
