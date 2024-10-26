package com.example.burgershop.di

interface Factory<T> {
    fun create(): T
}