package com.example.woof.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.woof.R

data class Dog(
    @StringRes val name: Int,
    val age: Int,
    @DrawableRes val imageResourceId: Int
)

val dogs = listOf(
    Dog(R.string.dog_name_koda, 2, R.drawable.koda),
    Dog(R.string.dog_name_lola, 16, R.drawable.lola),
    Dog(R.string.dog_name_frankie, 2, R.drawable.frankie),
    Dog(R.string.dog_name_nox, 8, R.drawable.nox),
    Dog(R.string.dog_name_faye, 8, R.drawable.faye),
    Dog(R.string.dog_name_bella, 14, R.drawable.bella),
    Dog(R.string.dog_name_moana, 2, R.drawable.moana),
    Dog(R.string.dog_name_tzeitel, 7, R.drawable.tzeitel),
    Dog(R.string.dog_name_leroy, 4, R.drawable.leroy)
)
