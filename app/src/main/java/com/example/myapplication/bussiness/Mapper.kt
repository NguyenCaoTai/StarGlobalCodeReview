package com.example.myapplication.bussiness

interface Mapper<in I, out O> {
    fun transform(input: I): O
}
