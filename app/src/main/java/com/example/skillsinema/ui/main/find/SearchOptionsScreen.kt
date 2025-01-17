package com.example.skillsinema.ui.main.find

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.RangeSlider
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.skillsinema.R
import java.time.Year
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue

@Composable
fun SearchOptionsScreen(
    viewModel: SearchViewmodel,
    navController: NavHostController
) {
    LazyColumn {
        item {
            SegmentedButton(viewModel)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }

        item {
            CountryRow(viewModel, navController)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }
        item {
            GenreRow(viewModel, navController)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }
        item {
            YearRow(viewModel, navController)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }
        // item { RatingRow() }
        item {
            Slider(viewModel)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }
        item {
            SegmentedSortedButton(viewModel = viewModel)
            Divider(color = Color(0x4DB5B5C9), thickness = 1.dp)
        }

    }

}


@Composable
fun CountryRow( viewModel: SearchViewmodel, navController:NavHostController) {
    Row(
        modifier = Modifier
            .clickable {
                viewModel.setCountryQuery(33, "Россия")
                //  viewModel.searchFilteredFilms
                navController.navigate(
                    SearchScreen.Country.name
                )
            }
            //.border(width = 1.dp, color = Color(0x4DB5B5C9))
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Страна",
            style = TextStyle(
                fontSize = 16.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF272727),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "Россия",
            style = TextStyle(
                fontSize = 14.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF838390),
                textAlign = TextAlign.End,
            )
        )
    }
}

@Composable
fun GenreRow(viewModel:SearchViewmodel, navController:NavHostController) {
    Row(
        modifier = Modifier
            .clickable { viewModel.setGenreQuery(6, "фантастика")
                navController.navigate(
                    SearchScreen.Genre.name
                )
            }
            //.border(width = 1.dp, color = Color(0x4DB5B5C9))
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Жанр",
            style = TextStyle(
                fontSize = 16.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF272727),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = viewModel.getGenre(),
            style = TextStyle(
                fontSize = 14.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF838390),
                textAlign = TextAlign.End,
            )
        )
    }
}

@Composable
fun YearRow(viewModel:SearchViewmodel, navController:NavHostController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    SearchScreen.Period.name
                )
            }
            //.border(width = 1.dp, color = Color(0x4DB5B5C9))
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Год",
            style = TextStyle(
                fontSize = 16.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF272727),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "${viewModel.getPeriod()}",
            style = TextStyle(
                fontSize = 14.sp,

                fontWeight = FontWeight(400),
                color = Color(0xFF838390),
                textAlign = TextAlign.End,
            )
        )
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Slider(viewModel:SearchViewmodel) {
    Column(

    ) {
        Row(
            modifier = Modifier
                //.border(width = 1.dp, color = Color(0x4DB5B5C9))
                .clickable { }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Рейтинг",
                style = TextStyle(
                    fontSize = 16.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "любой",
                style = TextStyle(
                    fontSize = 14.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF838390),
                    textAlign = TextAlign.End,
                )
            )
        }

        Row(
            modifier = Modifier


                .fillMaxWidth()
                //.height(150.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            var sliderValues by remember {
                mutableStateOf(0f..10f)
            }
            Column(

            ) {

                Row {
                    RangeSlider(
                        value = sliderValues,
                        onValueChange = { sliderValues_ ->
                            sliderValues = sliderValues_
                        },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            // this is called when the user completed selecting the value
                            Log.d(
                                "MainActivity",
                                "Start: ${sliderValues.start.toInt()}, End: ${sliderValues.endInclusive.toInt()}"
                            )
                            viewModel.setRating(sliderValues.start.toInt(), sliderValues.endInclusive.toInt())

                        },
                        steps = 9
                    )
                }

                Row(
                    modifier = Modifier

                        .width(360.dp)
                        .padding(start = 26.dp, end = 26.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        // text = String.format("%.1f", sliderValues.start),
                        text = sliderValues.start.toInt().toString(),

                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF838390),
                            textAlign = TextAlign.Start,
                        )
                    )
                    Text(

                        text = sliderValues.endInclusive.toInt().toString(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF838390),
                            textAlign = TextAlign.End,
                        )
                    )
                }
            }


        }
    }

}


@Composable
fun SegmentedButton(viewModel: SearchViewmodel) {
    Box(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.White),
        ) {
            Row {
                val items1 = listOf("Все", "Фильмы", "Сериалы")
                SegmentedControl(
                    items = items1,
                    defaultSelectedItemIndex = 0,
                    defaultFilmType = "All",
                    color = R.color.blue,
                    cornerRadius = 50
                ) {

                    viewModel.uiState.value.filmType = "ALL"
                    Log.e("CustomToggle", "Selected item : ${items1[it]}")
                    when (it) {
                        0 -> {
                            viewModel.setFilmType("ALL")
                        }

                        1 -> {
                            viewModel.setFilmType("FILM")
                        }

                        2 -> {
                            viewModel.setFilmType("TV_SERIES")
                        }
                    }

                }

            }
        }
    }
}


@Composable
fun SegmentedSortedButton(viewModel: SearchViewmodel) {
    Box(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.White),
        ) {
            Row {
                val items1 = listOf("Дата", "Популярность", "Рейтинг")
                SegmentedControl(
                    items = items1,
                    defaultSelectedItemIndex = 0,
                    defaultFilmType = "",
                    color = R.color.blue,
                    cornerRadius = 50
                ) {

                    viewModel.setOrder("")
                    Log.e("CustomToggle", "Selected item : ${items1[it]}")
                    when (it) {
                        0 -> {
                           viewModel.setOrder("YEAR")
                        }

                        1 -> {
                            viewModel.setOrder("NUM_VOTE")
                        }

                        2 -> {
                            viewModel.setOrder("RATING")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SegmentedControl(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    defaultFilmType: String = "ALL",
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {

                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize(unbounded = true)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        }
                    }

                    else -> {
                        if (useFixedWidth)
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        else Modifier
                            .wrapContentSize(unbounded = true)
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedIndex.value == index) 1f else 0f)
                    }
                },
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                },
                shape = when (index) {
                    /**
                     * left outer button
                     */
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    /**
                     * right outer button
                     */
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    /**
                     * middle button
                     */
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedIndex.value == index) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex.value == index) {
                    /**
                     * selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = colorResource(
                            id = color
                        )
                    )
                } else {
                    /**
                     * not selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                },
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex.value == index) {
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    },
                )
            }
        }
    }
}



@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun Preview() {
    //MyUI()
    // ButtonRow()
}




