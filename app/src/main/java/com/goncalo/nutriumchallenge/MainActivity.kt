package com.goncalo.nutriumchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.Screens
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.screens.ProfessionalDetailScreen
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel.ProfessionalDetailViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.screens.ProfessionalListScreen
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalListViewModel
import com.goncalo.nutriumchallenge.ui.theme.NutriumChallengeTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriumChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        val listViewModel: ProfessionalListViewModel = getViewModel()
                        val detailViewModel: ProfessionalDetailViewModel = getViewModel()

                        NavHost(
                            navController = navController,
                            startDestination = Screens.ProfessionalList
                        ) {

                            composable<Screens.ProfessionalList>(
                                exitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left,
                                        tween(1000)
                                    )
                                },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        tween(1000)
                                    )
                                }
                            ) {
                                ProfessionalListScreen(
                                    viewModel = listViewModel,
                                    navController = navController
                                )
                            }

                            composable<Screens.ProfessionalDetails>(
                                enterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left,
                                        tween(1000)
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right,
                                        tween(1000)
                                    )
                                }
                            ) {
                                val professionalUniqueId =
                                    it.toRoute<Screens.ProfessionalDetails>().professionalUniqueId
                                ProfessionalDetailScreen(
                                    professionalUniqueId = professionalUniqueId,
                                    navController = navController,
                                    viewModel = detailViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NutriumChallengeTheme {
    }
}