package com.brayskiy.example.rest

import java.io.IOException
import java.util.NoSuchElementException
import java.util.Properties

/**
 * Created by brayskiy on 01/31/19.
 */

object Keys {
    private val knownKeys = Properties()

    init {
        try {
            knownKeys.load(Keys::class.java.getResourceAsStream("/keys.properties.default"))
        } catch (e: IOException) {
            throw IllegalStateException("keys.properties not found on the classpath")
        }

    }

    enum class Types {
        the_movie_db
    }

    fun keyFor(toRetrieve: Types): String {
        if (!knownKeys.containsKey(toRetrieve.name)) {
            throw NoSuchElementException(
                String.format(
                    "Unsupported key type %s. " + "Please configure keys.properties in src/main/resources",
                    toRetrieve.name
                )
            )
        }
        return knownKeys.getProperty(toRetrieve.name)
    }
}

