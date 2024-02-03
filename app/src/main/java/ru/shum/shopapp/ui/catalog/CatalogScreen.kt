package ru.shum.shopapp.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import org.koin.androidx.compose.koinViewModel
import ru.shum.domain.features.catalog.model.Product
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.navigation.NavigationViewModel
import ru.shum.shopapp.ui.navigation.Screen
import ru.shum.shopapp.ui.theme.DarkPink
import ru.shum.shopapp.ui.theme.DirtyBlue
import ru.shum.shopapp.ui.theme.LightGray
import ru.shum.shopapp.ui.theme.Yellow
import ru.shum.shopapp.ui.util.BottomBar
import ru.shum.shopapp.ui.util.ProductItem
import ru.shum.shopapp.ui.util.SlidingCarousel
import ru.shum.shopapp.ui.util.findImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController, navigationViewModel: NavigationViewModel
) {
    val viewModel = koinViewModel<CatalogViewModel>()

    LaunchedEffect(Unit){
        viewModel.getProducts()
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "Каталог",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }, bottomBar = { BottomBar(navController = navController) }) {

        if (viewModel.products.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .padding(it)
        ) {

            Box(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
            ) {
                DropDownMenu(Modifier.align(Alignment.TopStart)) {
                    viewModel.changeSortState(it)
                }

                Row(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = ""
                    )

                    Text(
                        text = "Фильтры", fontSize = 14.sp
                    )
                }
            }

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(viewModel.tagsList.size) { index ->
                    TagItem(title = viewModel.tagsList[index],
                        index == viewModel.selectedTag.value,
                        onClick = {
                            viewModel.selectTag(index)
                        },
                        onClickClear = {
                            viewModel.selectTag(-1)
                        })
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                itemsIndexed(viewModel.products.value) { index, item ->
                    ProductItem(item, findImage(item),
                        onClick = {
                            navigationViewModel.setProduct(item)
                            navController.navigate(Screen.ProductPage.route)
                        }, onClickFavourite = {
                            viewModel.addToFavorite(item)
                        })
                }
            }
        }
    }
}

@Composable
fun DiscountItem(title: String) {
    Card(
        modifier = Modifier.padding(start = 5.dp), colors = CardDefaults.cardColors(
            containerColor = DarkPink
        ), shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(horizontal = 7.dp),
            text = title,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}


@Composable
fun TagItem(title: String, isSelected: Boolean, onClick: () -> Unit, onClickClear: () -> Unit) {
    Card(
        modifier = Modifier.padding(end = 7.dp), colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DirtyBlue else LightGray
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable { onClick() },
                text = title,
                color = if (isSelected) Color.White else Color.Gray
            )

            if (isSelected) {
                Icon(modifier = Modifier
                    .clickable {
                        onClickClear()
                    }
                    .padding(start = 5.dp)
                    .size(16.dp),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear Text",
                    tint = Color.White)
            }
        }

    }
}

@Composable
fun DropDownMenu(
    modifier: Modifier, onClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("По популярности") }

    Box(
        modifier = modifier
    ) {
        Row(modifier = Modifier
            .align(Alignment.CenterStart)
            .clickable {
                expanded = !expanded
            }) {

            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.sort),
                contentDescription = ""
            )

            Text(
                text = selected, fontSize = 14.sp
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "More"
            )

        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("По популярности") }, onClick = {
                selected = "По популярности"
                onClick(0)
                expanded = false
            })
            DropdownMenuItem(text = { Text("По возрастанию цены") }, onClick = {
                selected = "По возрастанию цены"
                onClick(1)
                expanded = false
            })
            DropdownMenuItem(text = { Text("По уменьшению цены") }, onClick = {
                selected = "По уменьшению цены"
                onClick(2)
                expanded = false
            })
        }
    }
}