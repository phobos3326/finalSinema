package com.example.skillsinema.data.mapper



import com.example.skillsinema.data.dto.EpisodeDto
import com.example.skillsinema.data.dto.SeasonsDto
import com.example.skillsinema.domain.model.Episode
import com.example.skillsinema.domain.model.Season
import javax.inject.Inject

class SeasonsMapper @Inject constructor() {

    fun mapSeasons(dto: SeasonsDto): List<Season> {
        return dto.items.map { seasonDto ->
            Season(
                number = seasonDto.number,
                episodes = seasonDto.episodes.map { mapEpisode(it) }
            )
        }
    }

    private fun mapEpisode(dto: EpisodeDto): Episode {
        return Episode(
            seasonNumber = dto.seasonNumber,
            episodeNumber = dto.episodeNumber,
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            synopsis = dto.synopsis,
            releaseDate = dto.releaseDate
        )
    }
}