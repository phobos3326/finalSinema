package com.example.skillsinema.ui.main.find

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodScreen(viewModel: SearchViewmodel) {
    /*   val calendar = Calendar.getInstance()
       calendar.set(1990, 0, 22) // add year, month (Jan), date

       // set the initial date
       val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

       DatePicker(

           state = datePickerState
       )

       val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ROOT)
       Text(
           text = "Selected date: ${formatter.format(Date(datePickerState.selectedDateMillis!!))}"
       )*/
    LazyVerticalGridDemo(viewModel)

}


@Composable
fun YearChoice() {
    val years = (1900..2023).toList()
    val displayedYears = years.take(10)

    // Display the years here
    // You can use Jetpack Compose components like LazyColumn, Text, etc.
    LazyColumn(
        modifier = Modifier

    ) {
        item {
            displayedYears.forEach { year ->
                Text(text = year.toString())
            }
        }
    }    // Example usage of Text to display the years

}

@Composable
fun LazyColumnGridExample() {
    val items = listOf(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10",
        "Item 11",
        "Item 12"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items.size) { item ->
            GridItem(item = item.toString())
        }
    }
}

@Composable
fun GridItem(item: String) {
    Text(
        text = item,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun LazyVerticalGridDemo(
    viewModel: SearchViewmodel
) {
val itemsPerPage =12
    val currentPage by viewModel.currentPage.collectAsState()
    val visibleList = remember(currentPage, itemsPerPage) {
        viewModel.getCurrentPageItems(itemsPerPage)
    }
    Column {


        Row {
            Column {


                Text(text = buildString {

                })
            }

            Column {

             /*   val buttonDecrementEnabledState by viewModel.buttonDecrementEnabledState.collectAsState()
                val buttonIncrementEnabledState by viewModel.buttonIncrementEnabledState.collectAsState()*/

                Button(
                    onClick = {
                        viewModel.decrementPage()

                    },
                    enabled = currentPage>0

                ) {
                    Text("<", fontSize = 15.sp)
                }

                Button(
                    onClick = {
                        viewModel.incrementPage()

                    },
                    enabled = visibleList.size>=itemsPerPage

                ) {
                    Text(">", fontSize = 15.sp)
                }
            }
        }
        Row {
           // val gridItems by viewModel.showYear.collectAsState()



            LazyVerticalGrid(
                columns = GridCells.Fixed(3),



                // content padding
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
                                .fillMaxWidth(),

                            ) {
                            Text(
                                text = visibleList[index].toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            )

        }
    }
}

fun copyElementsToTargetArray(
    currentIndex: Int,
    startIndex: Int,
    endIndex: Int,
    list: List<Int>,
    temporaryList: MutableList<Int>
) {
    var index = currentIndex
    val endIndex = index + 12
    temporaryList.clear()

    temporaryList.addAll(list.slice(index..endIndex).toMutableList())




    index + 12
    // Копирование элементов из sourceArray в targetArray
    /* for (i in startIndex until endIndex) {
         if (i < list.size) {  // Проверка, что индекс в пределах массива
             temporaryList.add(list[i])
         }

     }
     index += 12*/
    // Увеличение индекса для следующей части массива

    // Если currentIndex выходит за пределы массива, сбросить конечный индекс и очистить targetArray
    if (index > list.size) {
        index = 0
        temporaryList.clear()
    }
}

@Preview(showBackground = true)
@Composable
fun review( ) {
    //YearChoice()



//    LazyVerticalGridDemo()
        //PeriodScreen()

}