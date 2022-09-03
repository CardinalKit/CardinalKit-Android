package edu.stanford.cardinalkit.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    BottomNavigation {
        val COLOR_NORMAL = MaterialTheme.colorScheme.inversePrimary
        val COLOR_SELECTED = MaterialTheme.colorScheme.primary
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        Row() {
            TabBarItems.BarItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        BadgedBox(badge = {
                            if (navItem.route == Screens.TasksScreen.route) {
                                val pendingTasks = tasksViewModel.totalTasksToday.value - tasksViewModel.totalTasksCompleteToday.value
                                if (pendingTasks > 0) {
                                    Badge {
                                        Text(
                                            text = pendingTasks.toString()
                                        )
                                    }
                                }
                            }
                        }) {
                            Icon(
                                imageVector = navItem.image,
                                contentDescription = navItem.title,
                            )
                        }
                    },
                    label = {
                        Text(text = navItem.title, color = Color.Gray)
                    },
                    selectedContentColor = COLOR_SELECTED,
                    unselectedContentColor = COLOR_NORMAL
                )
            }
        }
    }
}
