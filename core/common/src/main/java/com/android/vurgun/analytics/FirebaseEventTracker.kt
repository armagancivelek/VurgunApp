package com.android.vurgun.analytics

interface FirebaseEventTracker {

    fun addToCart(params: Map<String, Any>)
    fun removeFromCart(params: Map<String, Any>)
    fun matchDetails(params: Map<String, Any>)

}