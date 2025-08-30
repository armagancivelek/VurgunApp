package com.android.vurgun.common_ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.vurgun.common_ui.extensions.shimmerWhen
import com.valentinilk.shimmer.Shimmer

@Composable
fun HomeScreenShimmerView(
    shimmerInstance: Shimmer,
) {
    val randomNumbers = remember {
        buildList {
            var previous: Int? = null
            repeat(10) {
                val next = listOf(200, 300).filter { it != previous }.random()
                add(next)
                previous = next
            }
        }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(10) {
            PostItemShimmer(
                shimmerInstance = shimmerInstance,
                height = randomNumbers[it].dp,
            )
        }
    }
}

@Composable
internal fun PostItemShimmer(
    shimmerInstance: Shimmer,
    height: Dp,
) {
    Box(
        modifier = Modifier
            .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(height)
            .shimmerWhen(
                enabled = true,
                shimmerInstance = shimmerInstance,
            ),
    )
}
