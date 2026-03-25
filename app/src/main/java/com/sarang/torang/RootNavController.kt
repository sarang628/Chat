package com.sarang.torang

class RootNavController {
    fun profile(it: Int) {

    }

    fun modReview(): (Int) -> Unit {
        return object : (Int) -> Unit {
            override fun invoke(p1: Int) {
            }
        }
    }
}