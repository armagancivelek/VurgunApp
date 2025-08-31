package com.android.vurgun.home.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun CustomHorizontalPagerComponent() {
    val originalList = listOf(
        com.android.vurgun.common_ui.R.drawable.ic_home_selected,
        com.android.vurgun.common_ui.R.drawable.ic_home_selected,
        com.android.vurgun.common_ui.R.drawable.ic_home_selected
    )

    val repeatedList = List(100) { index -> originalList[index % originalList.size] }

    val initialPage = repeatedList.size / 2
    val pagerState = rememberPagerState(initialPage = initialPage) {
        repeatedList.size
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) { page ->

        val pageOffset = (
            (pagerState.currentPage - page) + pagerState
                .currentPageOffsetFraction
            ).absoluteValue

        val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
        val alpha = lerp(0.6f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

        Card(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
                .fillMaxWidth()
                .aspectRatio(1.6f)
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = repeatedList[page]),
                contentDescription = "Page $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomHorizontalPagerPreview() {
    CustomHorizontalPagerComponent()
}