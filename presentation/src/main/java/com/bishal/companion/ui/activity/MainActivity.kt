package com.bishal.companion.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bishal.companion.ui.screen.HomeRoute
import com.bishal.companion.ui.screen.MovieDetailsScreen
import com.bishal.companion.ui.theme.ShepherdCompanionMviComposeTheme
import com.bishal.companion.viewmodel.HomeViewModel
import com.bishal.data.api.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShepherdCompanionMviComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyVerticalGridActivityScreen()
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun LazyVerticalGridActivityScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeRoute(navController)
        }
        composable(route = "details/{title}/{release_date}/{poster_path}/{lan}/{star}/{syn}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("release_date") {
                    type = NavType.StringType
                },
                navArgument("poster_path") {
                    type = NavType.StringType
                },
                navArgument("lan") {
                    type = NavType.StringType
                },
                navArgument("star") {
                    type = NavType.StringType
                },
                navArgument("syn") {
                    type = NavType.StringType
                },
            )
        ) {
            val title = it.arguments?.getString("title")!!
            val release = it.arguments?.getString("release_date")!!
            val poster = it.arguments?.getString("poster_path")!!
            val language = it.arguments?.getString("lan")!!
            val star = it.arguments?.getString("star")!!
            val synopsis = it.arguments?.getString("syn")!!

            Log.d("LogPoster", poster.toString())

            MovieDetailsScreen(
                poster,
                title,
                release,
                language,
                star,
                synopsis
            )
        }
    }
}