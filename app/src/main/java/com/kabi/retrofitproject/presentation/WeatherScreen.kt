package com.kabi.retrofitproject.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kabi.retrofitproject.ui.theme.RetrofitProjectTheme
import com.kabi.retrofitproject.domain.Result
import com.kabi.retrofitproject.domain.model.WeatherResponse
import com.kabi.retrofitproject.presentation.util.asUiText
import com.kabi.retrofitproject.ui.theme.Black
import com.kabi.retrofitproject.ui.theme.DarkGray
import com.kabi.retrofitproject.ui.theme.DarkPurple
import com.kabi.retrofitproject.ui.theme.Purple
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherRoot(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WeatherScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun WeatherScreen(
    state: WeatherState,
    onAction: (WeatherAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = Modifier
            .padding(),
        topBar = {},
        contentWindowInsets = WindowInsets.safeGestures
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Purple,
                            DarkPurple,
                            Black
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = state.searchQuery,
                        onValueChange = {
                            onAction(WeatherAction.OnSearchQueryChange(it))
                        },
                        label = { Text("Search any Location") },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        placeholder = { Text("Search") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.DarkGray.copy(0.4f),
                            focusedTextColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = Color.White,
                            focusedIndicatorColor = DarkGray,
                            unfocusedIndicatorColor = Color.White,
                            cursorColor = Color.Black,
                            unfocusedTrailingIconColor = Color.White,
                            focusedTrailingIconColor = Color.White
                        ),
                        keyboardActions = KeyboardActions.Default,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    onAction(WeatherAction.LoadWeather(state.searchQuery))
                                    keyboardController?.hide()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    Modifier.size(24.dp)
                                )
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                when (val weatherResult = state.weatherResult) {
                    Result.Loading -> {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }

                    is Result.Error -> {
                        val errorMessage = weatherResult.error.asUiText().asString()
                        Text("Error: $errorMessage")
                    }

                    is Result.Success -> {
                        WeatherDetails(weatherResult.data)
                    }

                    null -> {
                        Text(
                            text = "Search a City name to see Weather data.",
                            fontSize = 16.sp,
                            lineHeight = 8.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(text = "${data.location.name}, ", fontSize = 30.sp)
            Text(text = data.location.country, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} °C",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            model = "https:${data.current.condition.icon}"
                .replace("64x64", "128x128"),
            //this replace makes the icon more clear from blur
            //as the 64x64 was in the api image dimensions
            //"icon": "//cdn.weatherapi.com/weather/64x64/night/122.png"
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )

        Text(
            text = data.current.condition.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            border = BorderStroke(
                width = 1.dp,
                color = Color.Black
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 50.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = DarkGray
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyValues(
                        key = "Humidity",
                        value = "${data.current.humidity}%"
                    )
                    WeatherKeyValues(
                        key = "Wind Speed",
                        value = "${data.current.wind_kph} km/h"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyValues(
                        key = "UV Index",
                        value = data.current.uv
                    )
                    WeatherKeyValues(
                        key = "Wind Direction",
                        value = data.current.wind_dir
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyValues(
                        key = "Local Time",
                        value = data.location.localtime.split(" ")[1]
                    )
                    WeatherKeyValues(
                        key = "Local Date",
                        value = data.location.localtime.split(" ")[0]
                    )
                }
            }
        }

    }
}

@Composable
fun WeatherKeyValues(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview
@Composable
private fun Preview() {
    RetrofitProjectTheme {
        WeatherScreen(
            state = WeatherState(),
            onAction = {}
        )
    }
}