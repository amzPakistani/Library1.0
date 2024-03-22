package com.example.library10.ui.Screens

import android.widget.Space
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

import com.example.library10.R
import com.example.library10.network.BookItem
import com.example.library10.network.BookResponse
import com.example.library10.ui.BookViewModel
import com.example.library10.ui._uiState

@Composable
fun DetailScreen(detailID: String?, uiState: _uiState) {
    when (uiState) {
        is _uiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
        is _uiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is _uiState.Success -> DetailItem(detailid = detailID, book = uiState.books )
    }
}

@Composable
fun DetailItem(detailid: String?,modifier: Modifier = Modifier, book: BookResponse){
    val theItem = book.items.find { it.id == detailid }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize().padding(8.dp)
    ) {
        Box(modifier = modifier
            .padding(start = 48.dp, end = 48.dp, top = 40.dp, bottom = 32.dp)
            .fillMaxWidth()) {
            if (theItem != null) {
                PicItem(theItem = theItem)
            }
        }
        if (theItem != null) {
            TextScreen(theItem = theItem)
        }
    }
}

@Composable
fun PicItem(theItem:BookItem, modifier:Modifier=Modifier) {
//    val theItem = book.items.find { it.id == detailId }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        if (theItem != null) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(theItem.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.FillHeight
            )
        }
    }

}

@Composable
fun TextScreen(theItem: BookItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)) {
        theItem.volumeInfo.title?.let {
            Text(
                text = it,style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextItem(heading = "Author", desc = theItem.volumeInfo.allAuthorsx)
        theItem.volumeInfo.description?.let { TextItem(heading = "Description", desc = it) }
        theItem.volumeInfo.publishedDate?.let { TextItem(heading = "Published Date", desc = it) }
    }
}

@Composable
fun TextItem(heading: String, desc: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$heading:",style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = desc,style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}


//@Composable
//fun TextScreen(theItem:BookItem, modifier:Modifier=Modifier){
//    Column(modifier=modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)) {
//        theItem.volumeInfo.title?.let {
//            Text(
//                text = it,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//            )
//        }
//        Spacer(modifier = Modifier.height(32.dp))
//        TextItem(heading = "Author", desc = theItem.volumeInfo.allAuthorsx )
//        theItem.volumeInfo.description?.let { TextItem(heading = "Description", desc = it) }
//        theItem.volumeInfo.publishedDate?.let { TextItem(heading = "Published Date", desc = it) }
//
//    }
//}
//
//@Composable
//fun TextItem(heading:String, desc:String, modifier:Modifier=Modifier){
//    Row {
//        Text(text = "${heading}: ")
//        Text(text = desc)
//    }
//    Spacer(modifier = Modifier.height(20.dp))
//}