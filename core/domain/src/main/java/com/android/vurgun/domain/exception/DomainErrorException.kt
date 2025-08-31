package com.android.vurgun.domain.exception

class DomainErrorException(
    val rootException: Throwable,
) : Exception(rootException) {

    override val message: String
        get() = rootException.localizedMessage ?: rootException.message ?: ""
}
