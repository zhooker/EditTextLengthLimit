package com.example.lengthlimit.textwatcher

import android.text.Editable
import android.text.TextWatcher
import com.example.lengthlimit.util.TextLengthListener
import com.example.lengthlimit.util.Utils

/**
 *
 */
class TextLengthWatcher(private val maxLength: Int = Utils.MAX_LENGTH, val listener: TextLengthListener? = null) :
    TextWatcher {

    private var destCount: Int = 0
    private var dStart: Int = 0
    private var dEnd: Int = 0

    override fun afterTextChanged(s: Editable) {
        // count是输入后的字符长度
        val count = Utils.calcTextLength(s)
        if (count > maxLength) {
            // 超过了sum个字符，需要截取
            var sum = count - maxLength
            for (index in dEnd - 1 downTo dStart) {
                sum -= Utils.getCharTextCount(s[index])
                if (sum <= 0) {
                    // 输入字符超过了限制，截取
                    s.delete(index, dEnd)
                    break
                }
            }

            listener?.onTextLengthOutOfLimit()
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        destCount = Utils.calcTextLength(s)

        // 获取输入字符的起始位置
        dStart = start
        // 获取输入字符的个数
        dEnd = start + after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }
}