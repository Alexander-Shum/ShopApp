package ru.shum.shopapp.ui.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.navigation.NavigationViewModel
import ru.shum.shopapp.ui.productpage.ProductPageScreenViewModel
import ru.shum.shopapp.ui.util.BottomBar
import ru.shum.shopapp.ui.util.ProductItem
import ru.shum.shopapp.ui.util.findImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController
) {

    val viewModel = koinViewModel<FavoriteViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Избранное",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    modifier = Modifier
                        .padding(7.dp)
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
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize()
                .padding(it)
        ) {

            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 15.dp),
                columns = GridCells.Fixed(2)
            ) {
                itemsIndexed(viewModel.products.value) { index, item ->
                    ProductItem(
                        product = item,
                        imges = findImage(item),
                        onClick = { },
                        onClickFavourite = {item ->
                            viewModel.addToFavorite(item)
                        }
                    )
                }
            }
        }
    }
}