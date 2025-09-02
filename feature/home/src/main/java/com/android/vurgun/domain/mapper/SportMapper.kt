package com.android.vurgun.domain.mapper

import com.android.vurgun.domain.model.SportUiModel
import com.android.vurgun.domain.model.SportGroupUiModel
import com.android.vurgun.request.Sport

fun Sport.toUiModel(): SportUiModel {
    return SportUiModel(
        key = key,
        group = group,
        title = title,
        description = description,
        active = active,
        hasOutrights = hasOutrights,
    )
}

fun List<Sport>.toGroupedUiModel(): List<SportGroupUiModel> {
    return map { it.toUiModel() }
        .groupBy { it.group }
        .map { (group, sportsList) ->
            SportGroupUiModel(
                groupName = group,
                sports = sportsList,
            )
        }
}