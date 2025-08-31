package com.android.vurgun.common.core

/**
 *  This object is designed to support state management in an application following
 *  the Unidirectional Data Flow (UDF) and MVI (Model-View-Intent) architecture principles.
 */
object CoreState {
    interface ViewState {
        val isLoading: Boolean
    }

    interface Event

    interface Intent
}
