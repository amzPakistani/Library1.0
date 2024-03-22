package com.example.library10.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.library10.network.BookResponse
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.library10.R
import com.example.library10.network.BookItem
import com.example.library10.ui._uiState


@Composable
fun ListScreen(uiState: _uiState, modifier: Modifier = Modifier, navController: NavController) {
    when (uiState) {
        is _uiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        is _uiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is _uiState.Success -> BooksList(bookResponse = uiState.books, navController = navController)
    }
}


@Composable
fun BooksList(bookResponse: BookResponse, modifier: Modifier = Modifier, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        items(bookResponse.items) { item ->
            ListItem(data = item, onClick = {
                navController.navigate("detail_screen/${item.id}")
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(data: BookItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(data.volumeInfo.imageLinks?.httpsThumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp) // SpaceEvenly to distribute space evenly

                ){
                    data.volumeInfo.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    if(data.volumeInfo.allAuthorsx!= null){
                        Text(
                            text = data.volumeInfo.allAuthorsx,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

