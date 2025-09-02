package com.android.vurgun.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import javax.inject.Inject

class FirebaseEventTrackerImpl @Inject constructor() : FirebaseEventTracker {
    companion object {
        private const val MATCH_DETAILS_EVENT = "MATCH_DETAILS_EVENT"
    }

    private val firebaseAnalytics = Firebase.analytics

    override fun addToCart(params: Map<String, Any>) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, params.toBundle())
    }

    override fun removeFromCart(params: Map<String, Any>) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, params.toBundle())
    }

    override fun sportDetails(params: Map<String, Any>) {
        firebaseAnalytics.logEvent(MATCH_DETAILS_EVENT, params.toBundle())
    }
}

fun Map<String, Any>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())

