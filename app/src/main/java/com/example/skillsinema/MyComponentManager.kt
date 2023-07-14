package com.example.skillsinema

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyComponentManager @Inject constructor(
    val myComponentBuilder: MyComponentBuilder
) {

    private var myComponent: MyComponent? = null

    fun create() {
        myComponent = myComponentBuilder.build()
    }

    fun get() = myComponent

    fun destroy() {
        myComponent = null
    }

}