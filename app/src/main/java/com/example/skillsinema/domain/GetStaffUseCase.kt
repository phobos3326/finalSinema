package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelStaff

import com.example.skillsinema.repository.Repository
import com.example.skillsinema.repository.RepositoryStaff
import javax.inject.Inject

class GetStaffUseCase @Inject constructor(
    private val repository: RepositoryStaff,
    private val dataRepository: DataRepository
) {

    suspend fun getStaff(): List<ModelStaff.ModelStaffItem>? {

        return repository.getStaff(dataRepository.id)
    }
}