package com.example.skillsinema.domain

import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.entity.ModelStaffItem
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class GetStaffUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getStaff(): List<ModelStaffItem> = repository.getStaff(1)
}