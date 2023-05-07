package com.bishal.companion.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bishal.companion.intent.UsersIntent
import com.bishal.companion.viewmodel.HomeViewModel
import com.bishal.data.impl.PageHandler
import com.bishal.data.uistate.UserUiState
import com.bishal.domain.model.Result
import java.lang.Float.min

val url = "https://image.tmdb.org/t/p/w342"
@Composable
fun HomeRoute(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        viewModel =  viewModel,
        uiState = uiState,
        navController = navController,
        onIntent = viewModel::acceptIntent,
    )
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: UserUiState,
    navController: NavHostController,
    onIntent: (UsersIntent) -> Unit,
) {
    HomeMovieGrid(viewModel, navController, uiState, onIntent)
}

@Composable
fun HomeMovieGrid(
    viewModel: HomeViewModel,
    navController: NavHostController,
    uiState: UserUiState,
    onIntent: (UsersIntent) -> Unit
) {
    val scrollState = rememberLazyGridState()
    val list = remember{ mutableStateListOf<Result>()}
    uiState.data?.let { list.addAll(it.results) }
    SetupLayout(list, navController, scrollState, onIntent)
}


@Composable
fun SetupLayout(
    list: List<Result>,
    navController: NavHostController,
    scrollState: LazyGridState,
    onIntent: (UsersIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(125.dp),
            contentPadding = PaddingValues(8.dp),
            state = scrollState
        ) {
            itemsIndexed(list) { index, destination ->
                Row(Modifier.padding(5.dp)) {
                    if(index == list.lastIndex){
                        PageHandler.page++
                        onIntent(UsersIntent.GetUserData)
                    }
                    ItemLayout(destination, index, navController)
                }
            }
        }
    }
}


@Composable
fun ItemLayout(destination: Result, index: Int, navController: NavHostController) {
    destination.apply {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable {
                    navController.navigate("details/$title/$release_date$poster_path/$original_language/${vote_average.toString()}/$overview")
                }
        ) {
            AsyncImage(
                model = url+poster_path,
                alignment = Alignment.Center,
                contentDescription = destination.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.defaultMinSize(125.dp, 180.dp)
            )
        }
    }
}