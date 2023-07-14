package com.example.skillsinema

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@InstallIn(MyComponent::class)
@EntryPoint
interface MyEntryPoint {
    fun getDataRepository(): DataRepository
}