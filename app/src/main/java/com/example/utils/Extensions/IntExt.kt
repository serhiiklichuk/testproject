package com.example.utils.Extensions

fun Int.toLongOrNull() = try {
	this.toLong()
} catch (e: Exception) {
	null
}