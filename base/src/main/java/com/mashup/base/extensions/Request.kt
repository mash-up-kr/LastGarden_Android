package com.mashup.base.extensions

import android.content.Context
import android.net.Uri
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


private const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"

private val gson = GsonBuilder().create()
private val gsonWithNulls = GsonBuilder().serializeNulls().create()

/**
 * Convert Map object to json string by gson which not serialize nulls.
 */
fun <K, V> Map<K, V>.toJson(): String = gson.toJson(this)

/**
 * Convert Map object to json string by gson which serialize nulls.
 */
fun <K, V> Map<K, V>.toJsonWithNulls(): String = gsonWithNulls.toJson(this)

/**
 * Convert json string to [okhttp3.RequestBody]
 */
fun String.toJsonRequestBody(): RequestBody = toRequestBody(MEDIA_TYPE_JSON.toMediaType())

/**
 * Convert Map object to [okhttp3.RequestBody]
 */
fun <K, V> Map<K, V>.toJsonRequestBody(serializeNulls: Boolean): RequestBody = if (serializeNulls) {
    toJsonWithNulls().toJsonRequestBody()
} else {
    toJson().toJsonRequestBody()
}

/**
 * ## Usage
 * ```
 * jsonRequestBodyOf("group" to groupName)
 * ```
 *
 * <br /><br />
 *
 * ## Result
 * ```
 * {
 *     "group": grouName
 * }
 * ```
 *
 * <br /><br />
 *
 * ## Usage
 * ```
 * jsonRequestBodyOf("group" to mapOf("domain" to domain))
 * ```
 *
 * <br /><br />
 *
 * ## Result
 * ```
 * {
 *     "group": {
 *         "domain": domain
 *     }
 * }
 * ```
 */
fun <K, V> jsonRequestBodyOf(serializeNulls: Boolean, vararg pairs: Pair<K, V>): RequestBody =
    mapOf(*pairs).toJsonRequestBody(serializeNulls)

/**
 * Class for convenience syntax of creating Json
 */
@Suppress("MemberVisibilityCanBePrivate")
class JsonObject {

    private val map = mutableMapOf<String, Any?>()

    fun toJsonRequestBody(serializeNulls: Boolean) = map.toJsonRequestBody(serializeNulls)

    fun put(name: String, value: Any?) {
        map[name] = value
    }

    fun put(name: String, value: Map<String, Any>) {
        map[name] = value
    }

    fun put(name: String, value: JsonObject) {
        map[name] = value.map
    }

    fun put(name: String, value: JsonObject.() -> Unit) {
        put(name, JsonObject().also { it.value() })
    }

    fun put(name: String, value: Array<JsonObject>) {
        map[name] = value.map { it.map }
    }

    fun put(name: String, value: List<JsonObject.() -> Unit>) {
        put(name, value.map { JsonObject().also { obj -> obj.it() } }.toTypedArray())
    }

    infix fun String.to(value: Any?) = put(this, value)
    infix fun String.to(value: JsonObject) = put(this, value)
    infix fun String.to(value: JsonObject.() -> Unit) = put(this, value)

    infix fun String.to(value: Array<JsonObject>) = put(this, value)
    infix fun String.to(value: List<JsonObject.() -> Unit>) = put(this, value)
    fun String.to(vararg value: JsonObject.() -> Unit) = put(this, value.toList())

    infix fun String.toArray(value: Array<JsonObject>) = put(this, value)
    infix fun String.toArray(value: List<JsonObject.() -> Unit>) = put(this, value)
    fun String.toArray(vararg value: JsonObject.() -> Unit) = put(this, value.toList())

    fun jsonArrayOf(vararg elements: JsonObject.() -> Unit): List<JsonObject.() -> Unit> =
        elements.toList()
}

inline fun jsonObjectOf(block: JsonObject.() -> Unit): JsonObject = JsonObject().also { it.block() }

/**
 * ## Usage
 * ```
 * requestBodyOf {
 *     put("group") {
 *         put("domain", domain)
 *     }
 * }
 * ```
 *
 * <br /><br />
 *
 * ## is equal to
 * ```
 * requestBodyOf {
 *     "group" to {
 *         "domain" to domain
 *     }
 * }
 * ```
 *
 * <br /><br />
 *
 * ## Result
 * ```
 * {
 *     "group": {
 *         "domain": domain
 *     }
 * }
 * ```
 */
inline fun requestBodyOf(
    serializeNulls: Boolean = false,
    body: JsonObject.() -> Unit
): RequestBody = jsonObjectOf(body).toJsonRequestBody(serializeNulls)

fun Uri.toMultipartBody(context: Context, name: String): MultipartBody.Part? =
    if (scheme == "content") {
        toContent(context)?.toMultipartBody(name)
    } else {
        path?.let {
            val file = File(it)
            if (file.exists()) {
                file.toMultipartBody(name)
            } else {
                null
            }
        }
    }

fun ByteArray.toMultipartBody(name: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = name,
        filename = "image.jpg",
        body = toRequestBody(contentType = MultipartBody.FORM)
    )

fun File.toMultipartBody(name: String): MultipartBody.Part? {
    // When uploading an image file which file name contains Korean, an error occurred on the server.
    // The server does not require file name of the image so we do not send it.
    // TODO 위 코멘트 빙글때와 마찬가지로 같은지 확인 필요
    return MultipartBody.Part.createFormData(
        name = name,
        filename = null,
        body = asRequestBody(MultipartBody.FORM)
    )
}

inline fun <reified T> JsonDeserializationContext.deserialize(json: JsonElement): T =
    deserialize(json, T::class.java)
