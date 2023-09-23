package com.example.skillsinema.ui.main.find

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
/*import androidx.compose.material3.Card*/
/*import androidx.compose.material3.Text
import androidx.compose.material3.TextButton*/
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillsinema.R


@Composable
fun PeriodScreen(viewModel: SearchViewmodel) {
    YearPeriodFrom(viewModel)
}

@Composable
fun YearPeriodFrom(
    viewModel: SearchViewmodel
) {

    val currentPage by viewModel.currentPage.collectAsState()
    val visibleList = remember(currentPage) {
        viewModel.getCurrentPageItems()
    }
    Column(
        Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(size = 8.dp))
            .fillMaxWidth(1f)
            .height(224.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                Modifier.padding(start = 24.dp)
            ) {
                Text(
                    text = "${visibleList.first()} - ${visibleList.last()}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,

                        fontWeight = FontWeight(500),
                        color = Color(0xFF3D3BFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column {
                Row {
                    IconButton(
                        enabled = currentPage>0,
                        onClick = { viewModel.decrementPage() })


                    {
                        Icon(
                            Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        enabled = visibleList.size >= viewModel.itemsPerPage,
                        onClick = { viewModel.incrementPage() })


                    {
                        Icon(
                            Icons.Filled.KeyboardArrowRight,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }



                 /*   Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,

                        modifier = Modifier
                            .clickable { viewModel.decrementPage() }
                            .width(48.dp)
                            .height(48.dp)

                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,

                        modifier = Modifier
                            .clickable {

                                viewModel.incrementPage()
                            }
                            .width(48.dp)
                            .height(48.dp)


                    )*/


                    /*      TextButton(
                              onClick = {
                                  viewModel.incrementPage()
                              },
                              enabled = visibleList.size >= viewModel.itemsPerPage
                          ) {
                              Text(
                                  ">",
                                  style = TextStyle(
                                      fontSize = 20.sp,
                                      color = Color.Black,
                                      textAlign = TextAlign.Right
                                  )
                              )
                          }*/
                }
            }


        }
        Row {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    items(visibleList.size) { index ->
                        Card(


                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .clickable { viewModel.setPeriodFrom(visibleList[index]) },
                            elevation = 0.dp

                        ) {

                            Text(
                                text = visibleList[index].toString(),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF272727),
                                    textAlign = TextAlign.Center,
                                )

                            )
                        }
                    }
                }
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun review() {


}