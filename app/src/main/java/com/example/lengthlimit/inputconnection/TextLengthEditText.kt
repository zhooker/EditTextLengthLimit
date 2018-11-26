package com.example.lengthlimit.inputconnection

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import com.example.lengthlimit.util.TextLengthListener
import com.example.lengthlimit.util.Utils

/**
 *
 */
class TextLengthEditText : AppCompatEditText, TextLengthListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var listener: TextLengthListener? = null

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return TextLengthInputConnecttion(super.onCreateInputConnection(outAttrs), listener = TextLengthEditText@ this)
    }

    override fun onTextLengthOutOfLimit() {
        listener?.onTextLengthOutOfLimit()
    }

    fun setTextLengthListener(l: TextLengthListener) {
        listener = l
    }

    inner class TextLengthInputConnecttion(
        val target: InputConnection,
        private val maxLength: Int = Utils.MAX_LENGTH,
        val listener: TextLengthListener? = null
    ) : InputConnectionWrapper(target, false) {

        override fun commitText(source: CharSequence, newCursorPosition: Int): Boolean {
            val count = Utils.calcTextLength(source)
            val destCount = Utils.calcTextLength(text as CharSequence, selectionStart, selectionEnd)
            if (count + destCount > maxLength) {
                // 超过了sum个字符，需要截取
                var sum = count + destCount - maxLength
                // 输入字符超过了限制，截取
                val delete = Utils.getDeleteIndex(source, 0, source.length, sum)
                if (delete >= 0) {
                    listener?.onTextLengthOutOfLimit()
                    return super.commitText(if (delete > 0) source.subSequence(0, delete) else "", newCursorPosition)
                }
            }
            return super.commitText(source, newCursorPosition)
        }
    }
}