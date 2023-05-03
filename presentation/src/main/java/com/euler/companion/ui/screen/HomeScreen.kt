package com.euler.companion.ui.screen

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
import coil.compose.AsyncImage
import com.euler.companion.intent.UsersIntent
import com.euler.companion.viewmodel.HomeViewModel
import com.euler.data.impl.PageHandler
import com.euler.data.uistate.UserUiState
import com.euler.domain.model.Result
import java.lang.Float.min

val url = "https://image.tmdb.org/t/p/w342"
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        viewModel =  viewModel,
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: UserUiState,
    onIntent: (UsersIntent) -> Unit, ) {
    HomeMovieGrid(viewModel, uiState, onIntent)
}

@Composable
fun HomeMovieGrid(
    viewModel: HomeViewModel,
    uiState: UserUiState,
    onIntent: (UsersIntent) -> Unit
) {
    val scrollState = rememberLazyGridState()
    val scrollOffset = remember {
        derivedStateOf {
            min(1f, 1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
        }
    }
    val list = remember{ mutableStateListOf<com.euler.domain.model.Result>()}
    val horiList = remember{ mutableStateListOf<com.euler.domain.model.Result>()}
    val showProgressBarState = remember { mutableStateOf(false) }
//    if (showProgressBarState.value) { Loader() }
    uiState.data?.let { list.addAll(it.results) }
    SetupLayout(list, scrollState, onIntent)
}



//@Composable
//fun ShowProgressBar() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//        CircularProgressIndicator()
//    }
//}
//
@Composable
fun SetupLayout(
    list: List<Result>,
    scrollState: LazyGridState,
    onIntent: (UsersIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()
    ) {

      //  HoriRow(scrollOffset, horiList)
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
                    ItemLayout(destination, index)
                }
            }
        }
    }
}
//
//@Composable
//fun HoriRow(scrollOffset: State<Float>, list: SnapshotStateList<Result>) {
//    val lazyState = rememberLazyListState()
//    val imageSize by animateDpAsState(targetValue = max(0.dp, 230.dp * scrollOffset.value.toFloat()))
//    val value = remember {
//        derivedStateOf {
//            (max(0f, (1f * scrollOffset.value.toFloat())))
//        }
//    }
//    var movietext = "Movie"
//
//    if (imageSize == 0.dp){
//        movietext = "Now Showing"
//    }
//
//    Box(contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(0.dp, 10.dp, 0.dp, 12.dp)){
//        Text(text = movietext, fontSize = 16.sp, color = Color.White)
//    }
//
//
//    Column(modifier = Modifier
//        .graphicsLayer {
//            alpha = value.value
//        }
//        .height(imageSize)
//        .fillMaxWidth()) {
//        LazyRow(
//            state = lazyState,
//            verticalAlignment = Alignment.CenterVertically,
//            contentPadding = PaddingValues(start = 5.dp, end = 5.dp),
//            horizontalArrangement = Arrangement.spacedBy(5.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        ){
//            items(list, key = { it.id }) { item ->
//                Child(
//                    item,
//                    modifier = Modifier
//                        .size(290.dp, 200.dp)
//                        .graphicsLayer {
//                            val value =
//                                1 - (lazyState.layoutInfo.normalizedItemPosition(item.id).absoluteValue * 0.05F)
//                            alpha = value
//                            scaleX = value
//                            scaleY = value
//                        },
//                    imageModifier = Modifier.requiredWidth(290.dp)
//                )
//            }
//        }
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            Text(text = "Now Showing", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 4.dp))
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Child(item: Result, modifier: Modifier, imageModifier: Modifier,) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = modifier,
//    ) {
//        Box{
//            AsyncImage(
//                model = url+item.poster_path,
//                contentDescription = item.title,
//                modifier = imageModifier,
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}
//
//@Composable
//fun Loader() {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.bis.moviecompose.R.raw.loading))
//    val progress by animateLottieCompositionAsState(composition)
//    LottieAnimation(
//        modifier = Modifier.fillMaxSize(),
//        alignment = Alignment.Center,
//        composition = composition,
//        progress = { progress })
//}

@Composable
fun ItemLayout(destination: com.euler.domain.model.Result, index: Int) {
    destination.apply {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable {
                    //navController.navigate("details/$title/$release_date$poster_path/$original_language/${vote_average.toString()}/$overview")
                }
        ) {
            val showProgressBarState = remember { mutableStateOf(false) }
//            if (showProgressBarState.value) { ShowProgressBar() }
            AsyncImage(
                onLoading = {
                    showProgressBarState.value = true
                },
                onSuccess = {
                    showProgressBarState.value = false
                },
                model = url+poster_path,
                alignment = Alignment.Center,
                contentDescription = destination.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.defaultMinSize(125.dp, 180.dp)
            )
        }
    }
}