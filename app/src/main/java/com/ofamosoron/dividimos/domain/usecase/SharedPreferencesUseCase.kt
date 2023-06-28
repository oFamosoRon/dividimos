package com.ofamosoron.dividimos.domain.usecase

import kotlin.reflect.KClass

interface SharedPreferencesUseCase {
    fun <T> write(key: String, data: T)
    fun <T: Any> read(key: String, classType: KClass<T>): T?
}