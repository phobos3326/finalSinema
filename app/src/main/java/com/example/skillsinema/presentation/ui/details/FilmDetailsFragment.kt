package com.example.skillsinema.presentation.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.skillsinema.domain.model.Staff
import com.example.skillsinema.presentation.ui.search.FilmListItem

@Composable
fun FilmDetailsScreen(
    state: FilmDetailsUiState,
    onBack: () -> Unit,
    onFilmClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        state.details?.let { details ->
            Text(
                text = details.nameRu ?: details.nameEn.orEmpty(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = details.year?.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            details.posterUrl?.let { url ->
                AsyncImage(model = url, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp))
            }
            Text(
                text = details.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Text(text = "Съёмочная группа", style = MaterialTheme.typography.titleMedium)
        LazyRow {
            items(state.staff) { staff ->
                StaffListItem(staff = staff) { /*  обработка клика */ }
            }
        }

        Text(text = "Похожие фильмы", style = MaterialTheme.typography.titleMedium)
        LazyRow {
            items(state.similar) { film ->
                FilmListItem(film = film, onClick = { onFilmClick(film.kinopoiskId) })
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }

        state.error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun StaffListItem(
    staff: Staff,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(6.dp)
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = staff.posterUrl,
                contentDescription = staff.nameRu ?: staff.nameEn,
                modifier = Modifier
                    .size(width = 96.dp, height = 120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = staff.nameRu ?: staff.nameEn.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
            )
            Text(
                text = staff.professionText.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}