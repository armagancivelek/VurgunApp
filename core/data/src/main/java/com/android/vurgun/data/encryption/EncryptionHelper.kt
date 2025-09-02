package com.android.vurgun.data.encryption

interface EncryptionHelper {
    fun encryptData(rawData: String): String

    fun decryptData(encryptedData: String): Any
}