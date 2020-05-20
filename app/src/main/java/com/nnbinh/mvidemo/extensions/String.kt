package com.nnbinh.mvidemo.extensions

import java.security.MessageDigest
import java.util.regex.Pattern

fun String.hashText(type: String = "SHA-512"): String {
    val HEX_CHARS = "0123456789ABCDEF"
    val bytes = MessageDigest
        .getInstance(type)
        .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}

fun String.isEmail(): Boolean {
  if (this.isNullOrEmpty()) return false

  return Pattern.compile(
      "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
          + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
          + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
          + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
          + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
          + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
  ).matcher(this).matches()
}