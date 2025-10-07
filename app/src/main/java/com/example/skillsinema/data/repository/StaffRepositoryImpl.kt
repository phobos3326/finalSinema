package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.StaffApi
import com.example.skillsinema.data.mapper.StaffMapper
import com.example.skillsinema.domain.model.Staff
import com.example.skillsinema.domain.repository.StaffRepository
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val api: StaffApi,
    private val mapper: StaffMapper
) : StaffRepository {

    override suspend fun getStaff(filmId: Int): List<Staff> {
        val response = api.getStaff(filmId)
        return mapper.mapStaffList(response)
    }
}