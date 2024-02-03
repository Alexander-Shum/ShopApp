package ru.shum.shopapp.ui.productpage

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.compose.koinViewModel
import ru.shum.domain.features.catalog.model.Product
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.catalog.DiscountItem
import ru.shum.shopapp.ui.navigation.NavigationViewModel
import ru.shum.shopapp.ui.theme.DarkPink
import ru.shum.shopapp.ui.theme.LightGray
import ru.shum.shopapp.ui.theme.LightPink
import ru.shum.shopapp.ui.theme.LightWhite
import ru.shum.shopapp.ui.util.BottomBar
import ru.shum.shopapp.ui.util.RatingBar
import ru.shum.shopapp.ui.util.SlidingCarousel
import ru.shum.shopapp.ui.util.findImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPageScreen(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {

    val viewModel = koinViewModel<ProductPageScreenViewModel>()

    viewModel.setProduct(navigationViewModel.selectedProduct.value!!)

    val product by remember {
        mutableStateOf(viewModel.newItem.value!!)
    }

    var isFavourite by remember {
        mutableStateOf(product.favorite)
    }


    var isShowInfo by remember {
        mutableStateOf(true)
    }

    var isShowIngredients by remember {
        mutableStateOf(true)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 10.dp)
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.share_btn),
                    contentDescription = "shareBtn"
                )

                Icon(
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(25.dp)
                        .align(Alignment.TopStart),
                    painter = painterResource(id = R.drawable.back_btn),
                    contentDescription = "backBtn"
                )
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(bottom = 60.dp)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
        ) {
            ProductItem(product = navigationViewModel.selectedProduct.value!!,
                isFavourite,
                onClickFavourite = {item ->
                    isFavourite = !isFavourite
                    viewModel.addToFavorite(item)
                }
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 5.dp),
                text = product.title,
                color = Color.Gray,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 5.dp),
                text = product.subtitle,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 5.dp),
                text = "Доступно для заказа ${product.available} штук",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Row(
                modifier = Modifier
                    .padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    rating = product.feedback.rating.toFloat(),
                    modifier = Modifier.height(15.dp)
                )

                Text(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .padding(start = 10.dp),
                    text = "${product.feedback.rating}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Canvas(
                    modifier = Modifier
                        .size(5.dp)
                        .background(Color.Transparent),
                    onDraw = {
                        drawCircle(
                            color = Color.Gray,
                            radius = 5f,
                            center = center
                        )
                    }
                )

                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${product.feedback.count} отзыва",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.price.priceWithDiscount}${product.price.unit}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    text = "${product.price.price}${product.price.unit}",
                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                    color = Color.Gray,
                    fontSize = 18.sp
                )

                DiscountItem(title = "${product.price.discount}%")
            }

            Text(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(start = 5.dp),
                text = "Описание",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )

            if (isShowInfo) {
                Card(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LightGray
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 10.dp),
                            text = "${product.title}",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 16.sp
                        )

                        Icon(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .align(Alignment.CenterEnd),
                            painter = painterResource(id = R.drawable.next_btn),
                            contentDescription = "nextBtn",
                            tint = Color.Black
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(start = 5.dp),
                    text = product.description,
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }

            BtnCoverOpen(isShow = isShowInfo) {
                isShowInfo = !isShowInfo
            }

            Text(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(start = 5.dp),
                text = "Характеристики",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
                    .padding(top = 15.dp)
            ) {
                repeat(product.info.size) { index ->
                    InfoLine(title = product.info[index].title, data = product.info[index].value)
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 5.dp, top = 15.dp)
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Состав",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.boxes),
                    contentDescription = "boxesBtn",
                )
            }

            if (isShowIngredients) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(start = 5.dp),
                    text = product.ingredients,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(start = 5.dp),
                    text = product.ingredients,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            BtnCoverOpen(isShow = isShowIngredients) {
                isShowIngredients = !isShowIngredients
            }

            BtnAddToCart(
                priceWithDisc = "${product.price.priceWithDiscount}${product.price.unit}",
                price = "${product.price.price}${product.price.unit}"
            )
        }
    }
}

@Composable
fun BtnAddToCart(priceWithDisc: String, price: String) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(5.dp)),
        colors = CardDefaults.cardColors(
            containerColor = DarkPink
        )
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = priceWithDisc,
                    color = Color.White,
                    fontSize = 14.sp
                )

                Text(
                    text = price,
                    color = LightGray,
                    fontSize = 12.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                )
            }


            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp),
                text = "Добавить в корзину",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

}

@Composable
fun InfoLine(title: String, data: String) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = title,
                fontSize = 12.sp,
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                text = data,
                fontSize = 12.sp
            )
        }

        Divider(
            Modifier
                .padding(top = 5.dp)
                .background(Color.Gray)
                .height(1.dp)
        )
    }
}

@Composable
fun BtnCoverOpen(isShow: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(start = 5.dp)
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    onClick()
                },
            text = if (isShow) "Скрыть" else "Подробнее",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductItem(product: Product, isFavourite: Boolean, onClickFavourite: (Product) -> Unit) {

    Box(
        Modifier
            .padding(top = 10.dp)
            .background(Color.White)
    ) {
        val images = findImage(product)

        SlidingCarousel(modifier = Modifier.wrapContentWidth(),
            itemsCount = 2,
            itemContent = { index ->
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = "product"
                )
            })

        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = {
            onClickFavourite(product)
        }) {
            Icon(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp),
                painter = painterResource(id = if (isFavourite) R.drawable.favorite else R.drawable.unfavourite),
                contentDescription = "",
                tint = DarkPink
            )
        }

        Image(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomStart)
                .size(25.dp),
            painter = painterResource(id = R.drawable.why),
            contentDescription = ""
        )
    }
}

