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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
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
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue

@Composable
fun SearchOptionsScreen(
    viewModel: SearchViewmodel,
    navController: NavHostController
) {
    Text(text = "parametr screen")

    SegmentedControlPage(viewModel)
}


//@Preview
@Composable
fun SegmentedControlPage(viewModel: SearchViewmodel) {


    Box(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.White),
        ) {

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
                        viewModel.uiState.value.filmType = "ALL"
                    }

                    1 -> {
                        viewModel.uiState.value.filmType = "FILM"
                    }

                    2 -> {
                        viewModel.uiState.value.filmType = "TV_SERIES"
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonRow() {
    Column {
        Row(
            modifier = Modifier
                .clickable { }
                .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .width(360.dp)
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
        Row(
            modifier = Modifier
                .clickable { }
                .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .width(360.dp)
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
                text = "Комедия",
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
                .clickable { }
                .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .width(360.dp)
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
                text = "с 1998 до 2017",
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

               // .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .width(360.dp)
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

               // .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .width(360.dp)
                .height(50.dp)
                .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        )

        {

            Column {
                Row{
                    var sliderPosition by remember { mutableStateOf(0f..100f) }
                    RangeSlider(
                        modifier = Modifier.semantics { contentDescription = "Localized Description" },
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..100f,
                        steps = 10,
                        onValueChangeFinished = {
                            // launch some business logic update with the state you hold
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                        },
                    )
                }
                Row(
                    modifier = Modifier

                        // .border(width = 1.dp, color = Color(0x4DB5B5C9))
                        .width(360.dp)
                        .height(50.dp)
                        .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "0",
                        style = TextStyle(
                            fontSize = 16.sp,

                            fontWeight = FontWeight(400),
                            color = Color(0xFF272727),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "10",
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

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun Preview() {

    ButtonRow()
}

