package ru.shum.shopapp.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.shum.domain.features.catalog.model.Product
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.catalog.DiscountItem
import ru.shum.shopapp.ui.theme.DarkPink
import ru.shum.shopapp.ui.theme.Yellow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductItem(
    product: Product, imges: List<Int>, onClick: () -> Unit, onClickFavourite: (Product) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .shadow(shape = RoundedCornerShape(5.dp), elevation = 3.dp)
    ) {

        Column(
            modifier = Modifier
                .clickable { onClick() }
                .background(Color.White),
        ) {
            Box() {
                SlidingCarousel(modifier = Modifier.wrapContentWidth(),
                    itemsCount = 2,
                    itemContent = { index ->
                        Image(
                            painter = painterResource(id = imges[index]),
                            contentDescription = "product"
                        )
                    })

                IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = {
                    onClickFavourite(product)
                }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = if (product.favorite) R.drawable.favorite else R.drawable.unfavourite),
                        contentDescription = "",
                        tint = DarkPink
                    )
                }

            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .padding(top = 10.dp),
                text = "${product.price.price}${product.price.unit}",
                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                color = Color.Gray,
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier.padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.price.priceWithDiscount}${product.price.unit}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                DiscountItem(title = "${product.price.discount}%")
            }

            Text(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .padding(horizontal = 5.dp),
                text = product.title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 5.dp)
                    .padding(horizontal = 5.dp),
                text = product.subtitle,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "",
                    tint = Yellow
                )

                Text(
                    modifier = Modifier.padding(start = 3.dp),
                    text = product.feedback.rating.toString(),
                    color = Yellow,
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "(${product.feedback.count})",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        Image(
            modifier = Modifier.align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.add_btn),
            contentDescription = "addBtn"
        )
    }
}