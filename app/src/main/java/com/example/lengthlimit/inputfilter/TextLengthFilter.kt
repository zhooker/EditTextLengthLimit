package com.example.lengthlimit.inputfilter

import android.text.InputFilter
import android.text.Spanned
import com.example.lengthlimit.util.TextLengthListener
import com.example.lengthlimit.util.Utils

/**
 *
 */
class TextLengthFilter(private val maxLength: Int = Utils.MAX_LENGTH, val listener: TextLengthListener? = null) :
    InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        if (source.isNullOrEmpty()) {
            return ""
        }

        // bug fixed.
        // val source: CharSequence = source.subSequence(start, end)
        var sum = Utils.calcTextLength(dest as CharSequence, dstart, dend) + Utils.calcTextLength(source) - maxLength
        if (sum > 0) {
            val delete = Utils.getDeleteIndex(source, 0, source.length, sum)
            listener?.onTextLengthOutOfLimit()
            // 输入字符超过了限制，截取
            return if (delete > 0) source.subSequence(0, delete) else ""
        }

        // 没有超过限制，直接返回source
        return source
    }
}