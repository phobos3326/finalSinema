package com.example.skillsinema

import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope

@MyScope
@DefineComponent(parent = SingletonComponent::class)
interface MyComponent {

}