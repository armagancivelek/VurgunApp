package com.android.vurgun.domain.mapper

import com.android.vurgun.domain.model.SportUiModel
import com.android.vurgun.domain.model.SportGroupUiModel
import com.android.vurgun.home_api.model.Sport

fun Sport.toUiModel(): SportUiModel {
    return SportUiModel(
        key = key,
        group = group,
        title = title,
        description = description,
        active = active,
        hasOutrights = hasOutrights
    )
}

fun List<Sport>.toUiModel(): List<SportUiModel> {
    return map { it.toUiModel() }
}

fun List<SportUiModel>.toGroupedUiModel(): List<SportGroupUiModel> {
    return groupBy { it.group }.map { (group, sportsList) ->
        SportGroupUiModel(
            groupName = group,
            sports = sportsList
        )
    }
}