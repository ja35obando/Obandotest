package com.obando.test.core.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ListOfSomething<X>(
    private val wrapped: Class<X>?
) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type?> {
        return arrayOf(wrapped)
    }

    override fun getRawType(): Type {
        return MutableList::class.java
    }

    override fun getOwnerType(): Type? {
        return null
    }

}