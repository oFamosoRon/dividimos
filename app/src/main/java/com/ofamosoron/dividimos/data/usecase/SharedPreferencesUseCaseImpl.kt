package com.ofamosoron.dividimos.data.usecase

import android.content.Context
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import kotlin.reflect.KClass

private const val SHARED_PREFERENCES_NAME = "dividimos_preferences"

@Suppress("UNCHECKED_CAST")
class SharedPreferencesUseCaseImpl(
    private val context: Context
) : SharedPreferencesUseCase {

    override fun <T> write(key: String, data: T) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when (data) {
            is Boolean -> {
                editor.putBoolean(key, data)
            }
            is Float -> {
                editor.putFloat(key, data)
            }
            is Int -> {
                editor.putInt(key, data)
            }
            is String -> {
                editor.putString(key, data)
            }
            is Long -> {
                editor.putLong(key, data)
            }
        }
        editor.apply()
    }

    override fun <T : Any> read(key: String, classType: KClass<T>): T? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        return when (classType.simpleName) {
            TypeName.TypeFloat.name -> {
                sharedPreferences.getFloat(key, 0F) as T
            }
            TypeName.TypeInt.name -> {
                sharedPreferences.getInt(key, 0) as T
            }
            TypeName.TypeString.name -> {
                sharedPreferences.getString(key, "") as T
            }
            TypeName.TypeLong.name -> {
                sharedPreferences.getLong(key, 0L) as T
            }
            TypeName.TypeBoolean.name -> {
                sharedPreferences.getBoolean(key, false) as T
            }
            else -> {
                null
            }
        }

    }

    override fun clear() {
        context.deleteSharedPreferences(SHARED_PREFERENCES_NAME)
    }

    sealed class TypeName(val name: String) {
        object TypeInt: TypeName(name = "Int")
        object TypeLong: TypeName(name = "Long")
        object TypeFloat: TypeName(name = "Float")
        object TypeString: TypeName(name = "String")
        object TypeBoolean: TypeName(name = "Boolean")
    }
}
