package com.android.vurgun.network.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class AuthInternalClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class InternalClient
