package com.example.lengthlimit.util

/**
 *
 */

object Utils {

    @JvmField
    val MAX_LENGTH = 10

    @JvmStatic
    fun isChinese(c: Char): Boolean {
        return c.toInt() in 0x4E00..0x9FA5
    }

    @JvmStatic
    fun calcTextLength(charSequence: CharSequence?): Int {
        if (charSequence.isNullOrBlank()) {
            return 0;
        }

        var sum = 0
        for (c in charSequence) {
            sum += Utils.getCharTextCount(c)
        }
        return sum
    }

    @JvmStatic
    fun getCharTextCount(c: Char) = if (Utils.isChinese(c)) 2 else 1
}

interface TextLengthListener {
    fun onTextLengthOutOfLimit()
}