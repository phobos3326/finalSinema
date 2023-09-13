package com.example.skillsinema.ui.main.find

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.skillsinema.R

@Composable
fun CountryScreen(
    viewModel: SearchViewmodel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            CountrySearchTextField(viewModel, navController)

        }

        Row {
            ListOfCountry(viewModel, navController)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }


    }

}

@Composable
fun CountrySearchTextField(
    viewModel: SearchViewmodel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {


        val countries by viewModel.searchCountry.collectAsState(emptyList())
        var searchQuery by remember { mutableStateOf("") }
        val filteredCountries = countries.filter { country ->
            country.country.contains(searchQuery, ignoreCase = true)
        }
        Row {
            TextField(
                value = searchQuery,
                onValueChange = {
                   // viewModel.onSearchTextChange(it)
                    searchQuery=it
                    viewModel.loadCountries()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(0.dp),
                placeholder = { Text("Введите страну") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },

                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )
        }

        Row{

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredCountries) {item ->
                    ItemRow(viewModel =viewModel, navController = navController, countryName=item.country, countryId = item.id)
                }
            }
        }
    }
}

@Composable
fun ListOfCountry(viewModel: SearchViewmodel, navController: NavHostController) {


}


@Composable
fun ItemRow(viewModel: SearchViewmodel, navController: NavHostController, countryName:String, countryId: Int) {
    Column(
        //modifier = Modifier.padding(top = 26.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(countryId)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = countryName,
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
        Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)

    }
}

/*@Composable
fun RussiaRow(viewModel: SearchViewmodel, navController: NavHostController) {
    Column(
        modifier = Modifier.padding(top = 26.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(34)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Россия",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
        Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(5)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Великобритания",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
        Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(9)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Германия",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
        Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(1)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "США",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
        Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        Row(
            modifier = Modifier
                .clickable {
                    viewModel.setCountryQuery(3)
                    //  viewModel.searchFilteredFilms
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Франция",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )

        }
    }*/

