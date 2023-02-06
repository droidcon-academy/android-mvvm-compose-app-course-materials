package com.droidcon.bookstorenotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.bookstorenotes.ui.theme.BookstoreNotesTheme
import com.droidcon.bookstorenotes.view.BookDetailScreen
import com.droidcon.bookstorenotes.view.BookstoreBottomNav
import com.droidcon.bookstorenotes.view.BookstoreScreen
import com.droidcon.bookstorenotes.view.CollectionScreen
import com.droidcon.bookstorenotes.viewmodel.BooksApiViewModel
import com.droidcon.bookstorenotes.viewmodel.CollectionDbViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Library : Destination("library")
    object Collection : Destination("collection")
    object BookDetails : Destination("book/{bookId}") {
        fun createRoute(bookId: String?) = "book/$bookId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val booksViewModel by viewModels<BooksApiViewModel>()
    private val collectionsViewModel by viewModels<CollectionDbViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookstoreNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    BookstoreScaffold(
                        navController = navController,
                        bvm = booksViewModel,
                        cvm = collectionsViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun BookstoreScaffold(
    navController: NavHostController,
    bvm: BooksApiViewModel,
    cvm: CollectionDbViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BookstoreBottomNav(navController = navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                BookstoreScreen(
                    navController,
                    bvm,
                    paddingValues
                )
            }
            composable(Destination.Collection.route) { CollectionScreen(cvm, navController) }
            composable(Destination.BookDetails.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("bookId")
                if (id == null)
                    Toast.makeText(ctx, "Book id is required", Toast.LENGTH_SHORT).show()
                else {
                    bvm.getSingleBook(id)
                    BookDetailScreen(
                        bvm = bvm,
                        cvm = cvm,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}