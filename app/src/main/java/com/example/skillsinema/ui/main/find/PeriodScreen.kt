package com.example.skillsinema.ui.main.find

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PeriodScreen(viewModel: SearchViewmodel) {
    LazyVerticalGridDemo(viewModel)
}

@Composable
fun LazyVerticalGridDemo(
    viewModel: SearchViewmodel
) {

    val currentPage by viewModel.currentPage.collectAsState()
    val visibleList = remember(currentPage) {
        viewModel.getCurrentPageItems()
    }
    Column(Modifier.padding(12.dp)) {
        Row {
               TextButton(
                    onClick = {
                        viewModel.decrementPage()
                    },
                    enabled = currentPage > 0
                ) {
                    Text("<",
                        fontSize = 20.sp,
                        color = Color.Black)
                }
                TextButton(
                    onClick = {
                        viewModel.incrementPage()
                    },
                    enabled = visibleList.size >= viewModel.itemsPerPage
                ) {
                    Text(">",
                        fontSize = 20.sp,
                        color = Color.Black
                        )
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

                            ) {

                            Text(
                                text = visibleList[index].toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp),

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