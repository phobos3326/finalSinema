package com.example.skillsinema.domain.usecase.staff

import com.example.skillsinema.domain.model.Staff
import com.example.skillsinema.domain.repository.StaffRepository
import javax.inject.Inject

class GetStaffUseCase @Inject constructor(
    private val staffRepository: StaffRepository
) {
    suspend operator fun invoke(filmId: Int): List<Staff> {
        return staffRepository.getStaff(filmId)
    }
}