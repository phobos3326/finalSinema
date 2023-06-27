package com.example.skillsinema

import javax.inject.Inject

class DataRepository @Inject constructor() {
     var id:Int =0
     fun getValue(): Int {
          return id
     }

     fun setValue(value: Int) {
          id = value
     }
}