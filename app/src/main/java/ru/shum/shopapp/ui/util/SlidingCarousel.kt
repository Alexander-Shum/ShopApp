package ru.shum.shopapp.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import ru.shum.domain.features.catalog.model.Product
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.theme.DarkPink

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SlidingCarousel(
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(
        modifier = modifier,
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Transparent
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = DarkPink,
    unSelectedColor: Color = Color.LightGray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier, size: Dp, color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

fun findImage(item: Product): List<Int> {
    return when (item.id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> listOf(
            R.drawable.product_1,
            R.drawable.product_1
        )

        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> listOf(
            R.drawable.product_2,
            R.drawable.product_2
        )

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> listOf(
            R.drawable.product_3,
            R.drawable.product_3
        )

        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> listOf(
            R.drawable.product_4,
            R.drawable.product_4
        )

        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
            R.drawable.product_5,
            R.drawable.product_5
        )

        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
            R.drawable.product_6,
            R.drawable.product_6
        )

        else -> {
            listOf(R.drawable.product_6, R.drawable.product_6)
        }
    }
}