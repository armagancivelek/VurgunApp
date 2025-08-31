package com.android.vurgun.domain.model

data class SportUiModel(
    val key: String,
    val group: String,
    val title: String,
    val description: String,
    val active: Boolean,
    val hasOutrights: Boolean
)