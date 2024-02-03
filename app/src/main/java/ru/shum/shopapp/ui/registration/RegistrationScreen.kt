package ru.shum.shopapp.ui.registration

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.shum.shopapp.ui.theme.DarkPink
import ru.shum.shopapp.ui.theme.LightGray
import ru.shum.shopapp.ui.theme.LightPink
import ru.shum.shopapp.ui.theme.LightWhite
import ru.shum.shopapp.ui.util.Constants

@Composable
fun RegistrationScreen(
    navHostController: NavHostController
) {

    val viewModel = koinViewModel<RegistrationViewModel>()


    if (viewModel.isUserRegistered.value) {
        navHostController.navigate(Constants.MAIN)
    }


    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "Вход",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }


        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        ) {
            item {
                NameTextField(
                    hint = "Имя",
                    text = viewModel.formState.name,
                    isError = viewModel.formState.nameError != null,
                    errorMessage = viewModel.formState.nameError,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.NameChanged(it))
                    }
                )

                NameTextField(
                    hint = "Фамилия",
                    text = viewModel.formState.surname,
                    isError = viewModel.formState.surnameError != null,
                    errorMessage = viewModel.formState.surnameError,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.SurnameChanged(it))
                    }
                )

                PhoneTextField(
                    hint = "Номер телефона",
                    text = viewModel.formState.phone,
                    isError = viewModel.formState.phoneError != null,
                    errorMessage = viewModel.formState.phoneError,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.PhoneChanged(it))
                    }
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                EntranceButton(
                    title = "Вход",
                    viewModel.formState.nameError == null && viewModel.formState.name != "" && viewModel.formState.surname != "" && viewModel.formState.phone != "" && viewModel.formState.phone.length == 12
                ) {
                    if (viewModel.formState.nameError == null && viewModel.formState.name != "" && viewModel.formState.surname != "" && viewModel.formState.phone != "" && viewModel.formState.phone.length == 12) {
                        viewModel.saveUser()
                        navHostController.popBackStack(navHostController.graph.startDestinationId, true)
                        navHostController.navigate(Constants.MAIN)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(bottom = 7.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                fontSize = 12.sp,
                text = "Нажимая кнопку “Войти”, Вы принимаете",
                color = Color.Gray
            )
            Text(
                modifier = Modifier.align(CenterHorizontally),
                fontSize = 12.sp,
                text = "условия программы лояльности",
                color = Color.Gray,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                )
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PhoneTextField(
    hint: String,
    text: String,
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                value = text,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = if (isError) MaterialTheme.colorScheme.error else Color.Black
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = LightGray,
                ),
                placeholder = {
                    Text(
                        text = hint,
                        color = Color.Gray,
                        style = TextStyle(fontSize = 16.sp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                visualTransformation = PhoneNumberVisualTransformation(),
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = { onValueChange("") },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )
        }

        Text(
            text = if (isError) errorMessage!! else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NameTextField(
    hint: String,
    text: String,
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(5.dp),
                value = text,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = if (isError) MaterialTheme.colorScheme.error else Color.Black
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = LightGray,
                ),
                placeholder = {
                    Text(
                        text = hint,
                        color = Color.Gray,
                        style = TextStyle(fontSize = 16.sp)
                    )
                },
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = { onValueChange("") },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Text"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )
        }

        Text(
            text = if (isError) errorMessage!! else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Composable
fun EntranceButton(
    title: String,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clip(RoundedCornerShape(5.dp))
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .background(if (isActive) DarkPink else LightPink)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.Center),
                text = title,
                fontSize = 16.sp,
                color = if (isActive) Color.White else LightWhite
            )
        }
    }

}


@Preview
@Composable
fun EntranceButtonPreview() {
    EntranceButton(title = "Title") {}
}