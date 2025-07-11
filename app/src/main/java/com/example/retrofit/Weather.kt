package com.example.retrofit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.retrofit.api.NetworkResponce
import com.example.retrofit.api.WeatherModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.*
import androidx.compose.runtime.*



@Composable
fun Weather(modifier: Modifier = Modifier, viewModel: ViewModel) {

    var input by remember { mutableStateOf("") }

    val weaterResult = viewModel.weaterResult.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Fill the screen nicely
            modifier = Modifier.fillMaxSize()
        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(modifier = Modifier.weight(1f),
                value = input,
                onValueChange = { input = it },
                shape = RoundedCornerShape(16.dp),
                label = { Text("Enter Location", color = Color.White)  },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )


            )

            IconButton({
                viewModel.getData(input)
                input = ""
            }) { Icon(Icons.Default.Search, "", modifier = Modifier.size(40.dp)) }
        }

        when (val result = weaterResult.value) {
            is NetworkResponce.Error -> {
                Text(text = result.messagee)
            }

            NetworkResponce.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponce.Success -> {
                WeatherDetail(result.data)
            }

            null -> {}
        }
    }
}
}

@Composable
fun WeatherDetail(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                Icons.Default.LocationOn,
                "",
                modifier = Modifier.size(40.dp)
            )
            Text(
                data.location.name,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                data.location.country,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} °c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.size(156.dp),
            model = "https:${data.current.condition.icon}"
                .replace("64x64", "128x128"),
            contentDescription = ""
        )

        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(36.dp))

        Card(elevation = CardDefaults.cardElevation(8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextUI(key = "Humadity", value = data.current.humidity)
                    TextUI(key = "Wind Speed", value = data.current.wind_kph)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextUI(key = "Humadity", value = data.current.humidity)
                    TextUI(key = "Wind Speed", value = data.current.wind_kph)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextUI(key = "UV", value = data.current.uv)
                    TextUI(key = "Praticipation", value = data.current.precip_mm)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextUI(key = "Local Time", value = data.location.localtime.split(" ")[1])
                    TextUI(key = "Local date", value = data.location.localtime.split(" ")[0])
                }
            }
        }
    }

}

@Composable
fun TextUI(key: String, value: String) {

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center

        )

        Text(
            text = key,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}