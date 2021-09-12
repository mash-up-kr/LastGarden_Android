package com.mashup.base.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

fun Uri.toContent(context: Context): ByteArray? = context.contentResolver?.let {
    toContentFromFileDescriptor(it) ?: toContentFromInputStream(it)
}

fun Uri.toContentFromFileDescriptor(contentResolver: ContentResolver): ByteArray? {
    val descriptor = try {
        contentResolver.openFileDescriptor(this, "r") ?: return null
    } catch (e: FileNotFoundException) {
        return null
    }

    val fileInputStream = FileInputStream(descriptor.fileDescriptor)
    return try {
        fileInputStream.readBytes()
    } catch (e: IOException) {
        null
    } finally {
        descriptor.close()
        fileInputStream.close()
    }
}

fun Uri.toContentFromInputStream(contentResolver: ContentResolver): ByteArray? {
    val inputStream = try {
        contentResolver.openInputStream(this) ?: return null
    } catch (e: FileNotFoundException) {
        return null
    }

    return try {
        inputStream.readBytes()
    } catch (e: IOException) {
        null
    } finally {
        inputStream.close()
    }
}
