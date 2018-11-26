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

        val source: CharSequence = source.subSequence(start, end)
        var sum = maxLength - getCharSequenceLength(dest as CharSequence, dstart, dend)
        if (sum > 0) {
            source.forEachIndexed { index, c ->
                val count = Utils.getCharTextCount(c)
                if (sum - count < 0) {
                    listener?.onTextLengthOutOfLimit()
                    return source.subSequence(0, index) // 输入字符超过了限制，截取
                }

                sum -= count
            }
            // 没有超过限制，直接返回source
            return source
        } else {
            // 已经超过了限制，直接提示
            listener?.onTextLengthOutOfLimit()
            return ""
        }
    }

    /**
     * 计算除了[dstart,dend]外的字符数
     */
    private fun getCharSequenceLength(source: CharSequence, dstart: Int, dend: Int): Int {
        var count = 0
        source.forEachIndexed { index, c ->
            if (index !in dstart..dend) {
                count += Utils.getCharTextCount(c)
            }
        }
        return count
    }
}