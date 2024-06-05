package com.obando.test.core.utils

import com.google.gson.Gson
import java.lang.reflect.Type

object JsonUtil {

    fun fromObjectToJson(value: Any): String = Gson().toJson(value)

    fun <T> fromJsonToObject(rawJson: String, resultClass: Class<T>): T {
        return Gson().fromJson(rawJson, resultClass as Type)
    }

    fun <T> fromJsonToListObject(rawJson: String, resultClass: Class<T>): ArrayList<T> {
        return Gson().fromJson(rawJson, ListOfSomething(resultClass))
    }


    fun fromListObjectToJson(value: Any): String = Gson().toJson(value)
}