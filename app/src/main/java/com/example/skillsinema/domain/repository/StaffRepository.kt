package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Staff

interface StaffRepository {
    suspend fun getStaff(filmId: Int): List<Staff>
}