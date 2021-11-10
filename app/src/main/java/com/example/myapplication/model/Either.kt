package com.example.myapplication.model

sealed class Either<L, R>(val left: L?, val right: R?) {
    class Right<L, R>(value: R) : Either<L, R>(left = null, right = value)
    class Left<L, R>(value: L) : Either<L, R>(left = value, right = null)
}