package com.droidcon.book_store_notes

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
import com.droidcon.book_store_notes.ui.theme.Book_store_notesTheme
import com.droidcon.book_store_notes.view.BookDetailScreen
import com.droidcon.book_store_notes.view.BookstoreScreen
import com.droidcon.book_store_notes.view.CollectionScreen
import com.droidcon.book_store_notes.viewmodel.BooksApiViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Book_store_notesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    BookstoreScaffold(
                        navController = navController,
                        booksViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun BookstoreScaffold(
    navController: NavHostController,
    bvm: BooksApiViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BookstoreBottomNav(navController = navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                BookstoreScreen(navController, bvm, paddingValues)
            }
            composable(Destination.Collection.route) { CollectionScreen() }
            composable(Destination.BookDetails.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("bookId")
                if (id == null)
                    Toast.makeText(ctx, "Book id is required", Toast.LENGTH_SHORT).show()
                else {
                    bvm.getSingleBook(id)
                    BookDetailScreen(
                        bvm = bvm,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }}
        }
    }
}