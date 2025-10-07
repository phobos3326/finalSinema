package com.example.skillsinema.data.mapper

import com.example.skillsinema.data.dto.StaffDto
import com.example.skillsinema.domain.model.Staff
import javax.inject.Inject

class StaffMapper @Inject constructor() {

    fun mapStaff(dto: StaffDto): Staff {
        return Staff(
            staffId = dto.staffId,
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            description = dto.description,
            posterUrl = dto.posterUrl,
            professionText = dto.professionText,
            professionKey = dto.professionKey
        )
    }

    fun mapStaffList(dtos: List<StaffDto>): List<Staff> {
        return dtos.map { mapStaff(it) }
    }
}