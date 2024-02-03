package ru.shum.shopapp.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.shum.shopapp.R
import ru.shum.shopapp.ui.navigation.Screen
import ru.shum.shopapp.ui.theme.LightGray
import ru.shum.shopapp.ui.util.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController) {

    val viewModel = koinViewModel<ProfileViewModel>()

    LaunchedEffect(Unit){
        viewModel.initContent()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = "Личный кабинет",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column() {
                Line(
                    icon = R.drawable.profile_icon,
                    title = "${viewModel.user.value.name} ${viewModel.user.value.surname}",
                    desc = viewModel.formatRussianPhoneNumber(viewModel.user.value.phone),
                    icon2 = R.drawable.share_icon
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                Line(
                    icon = R.drawable.unfavourite,
                    title = "Избранное",
                    desc = "${viewModel.sizeFavourites.value} товар",
                    icon2 = R.drawable.next_btn
                ){
                    navController.navigate(Screen.Favourite.route)
                }

                Line(icon = R.drawable.shop, title = "Магазины", icon2 = R.drawable.next_btn)

                Line(
                    icon = R.drawable.feedback,
                    title = "Обратная связь",
                    icon2 = R.drawable.next_btn
                )

                Line(icon = R.drawable.oferta, title = "Оферта", icon2 = R.drawable.next_btn)

                Line(
                    icon = R.drawable.backup,
                    title = "Возврат товара",
                    icon2 = R.drawable.next_btn
                )


            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGray
                )
            ) {
                Box(modifier = Modifier
                    .clickable {
                        viewModel.deleteAllUsers()
                        navController.popBackStack(navController.graph.startDestinationId, true)
                        navController.navigate(Screen.Registration.route)
                    }
                    .height(50.dp)
                    .fillMaxWidth()){
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Выйти",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Line(icon: Int, title: String, desc: String = "", icon2: Int, onClick:() -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        )
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick() }
                .height(50.dp)
                .fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .padding(start = 5.dp)
                    .align(Alignment.CenterStart),
                painter = painterResource(id = icon),
                contentDescription = "icon1"
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 35.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 14.sp
                )
                if (desc != "") {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = desc,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = icon2),
                contentDescription = "icon2"
            )

        }
    }
}