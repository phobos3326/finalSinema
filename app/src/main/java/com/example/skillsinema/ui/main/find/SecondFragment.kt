package com.example.skillsinema.ui.main.find

import android.content.Context
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentSecondBinding
import com.example.skillsinema.entity.Film
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.skillsinema.entity.Genre


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

enum class SearchScreen(@StringRes val title: Int) {
    Start(title = R.string.search_screen),
    SearchOptions(title = R.string.search_options),
    Country(title = R.string.country_screen),
    Genre(title = R.string.genre_screen),
    Period(title = R.string.period_screen)
}

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*   arguments?.let {
               param1 = it.getString(ARG_PARAM1)
               param2 = it.getString(ARG_PARAM2)
           }*/
    }

    //private val mainViewModel: SearchViewmodel = hiltViewmodel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        //
        //newInstance(param1, param2)

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            //  setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {

                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Row {
                            //SearchBar()
                            // SearchApp()
                        }
                        Row {
                            //BarkHomeContent()
                            SearchApp()

                        }

                    }

                }
            }


        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchAppBar(

        currentScreen: SearchScreen,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = { Text(stringResource(currentScreen.title)) },
            /* colors = TopAppBarDefaults.mediumTopAppBarColors(
                 containerColor = MaterialTheme.colorScheme.primaryContainer
             ),*/
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    androidx.compose.material.IconButton(onClick = navigateUp) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
    }


    @Composable
    fun SearchApp(
        viewModel: SearchViewmodel = viewModel(),
        navController: NavHostController = rememberNavController()
    ) {

        val backStackEntry by navController.currentBackStackEntryAsState()
        // Get the name of the current screen
        val currentScreen = SearchScreen.valueOf(
            backStackEntry?.destination?.route ?: SearchScreen.Start.name
        )

        Scaffold(
            topBar = {
                SearchAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        ) { innerPadding ->
            val uiState by viewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = SearchScreen.Start.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = SearchScreen.Start.name) {
                    SearchScreen(
                        viewModel,
                        navController
                    )
                }
                composable(route = SearchScreen.SearchOptions.name) {
                    val context: Context
                    SearchOptionsScreen(viewModel = viewModel, navController = navController)
                }
            }
        }
    }

    @Composable
    fun FilmListItem(film: Film) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)

                .fillMaxWidth(),
            elevation = 0.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(4.dp))

        ) {
            Row {
                FilmImage(film)
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    film.nameRu?.let { Text(text = it, style = typography.h6) }
                    Text(text = film.genres?.first()?.genre.toString(), style = typography.caption)

                }
            }
        }
    }


    @Composable
    private fun FilmImage(film: Film) {
        Box {
            Image(
                painter = rememberImagePainter(film.posterUrlPreview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //.padding(8.dp)
                    .width(96.dp)
                    .height(132.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(4.dp)))
            )
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(17.dp)
                    .height(9.91211.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(color = Color.White)
            ) {
                Text(

                    text = film.ratingImdb.toString(),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.Black,
                )
            }

        }
    }


    @Composable
    fun SearchScreen(
        viewModel: SearchViewmodel,
        navController: NavHostController,
        //pager: Pager<Int, Film>
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row {

                val searchText by viewModel.searchQuery.collectAsState()
                val textState = remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = searchText,
                    onValueChange = {
                        //viewModel.onSearchTextChange(it)
                        viewModel.setSearchQuery(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(0.dp),
                    placeholder = { Text("Фильмы, актеры, режисёры") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        Row {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(52.dp),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.rectangle_31),
                                    alignment = Alignment.Center,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                            Column(
                                modifier = Modifier.width(50.dp)
                            ) {
                                IconButton(

                                    onClick = {

                                        navController.navigate(SearchScreen.SearchOptions.name)
                                    },
                                    content = {
                                        Icon(
                                            painterResource(R.drawable.find_settings),
                                            contentDescription = null
                                        )
                                    }
                                )
                            }

                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = TextFieldDefaults.textFieldColors(

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,

                        ),

                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                )
            }
            Row {
                //val data by viewModel.keyWordsFilms.collectAsState()
                /*val data by viewModel.keyWordsFilms.collectAsState()
                LazyColumn(modifier = Modifier.fillMaxHeight(1f)) {
                    items(data) { Film ->
                        //viewModel.keyWordsFilms()
                        //viewModel.searchFilteredFilms
                        FilmListItem(film = Film)

                    }
                }*/
                //  val pagingData: PagingData<Film> by viewModel.searchFilteredFilms.collectAsState()

                val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()

                LazyColumn {
                    items(
                        lazyPagingItems.itemCount,
                        // key = lazyPagingItems.itemKey { it }
                    ) { index ->
                        val film = lazyPagingItems[index]
                        if (film != null) {

                            FilmListItem(film)
                            lazyPagingItems.refresh()
                        }
                    }
                }
                /*LazyColumn{
                    items(items=lazyPagingItems){ film:Film ->
                        FilmListItem( ind)
                    }
                }*/
            }
        }
    }

    @Composable
    fun GenreScreen(
        viewModel: SearchViewmodel,
        navController: NavHostController
    ) {
        Text(text = "parametr screen")
    }


    @Preview
    @Composable
    fun preview() {
        // SearchBar()
        // BarkHomeContent()
        //ImageWithOverlay()
        //FilmListItem1()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        /*   @JvmStatic
           fun newInstance(param1: String, param2: String) =
               SecondFragment().apply {
                   arguments = Bundle().apply {
                       putString(ARG_PARAM1, param1)
                       putString(ARG_PARAM2, param2)
                   }
               }*/
    }
}