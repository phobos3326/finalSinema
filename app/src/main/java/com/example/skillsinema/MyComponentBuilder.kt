package com.example.skillsinema

import dagger.hilt.DefineComponent

@DefineComponent.Builder
interface MyComponentBuilder {
    fun build(): MyComponent
}