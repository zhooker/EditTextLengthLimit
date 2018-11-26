package com.example.lengthlimit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.lengthlimit.inputfilter.TextLengthFilter
import com.example.lengthlimit.textwatcher.TextLengthWatcher
import com.example.lengthlimit.util.TextLengthListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextLengthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_inputfilter.filters = arrayOf(TextLengthFilter(listener = MainActivity@ this))

        edit_textwatcher.addTextChangedListener(TextLengthWatcher(listener = MainActivity@ this))

        edit_inputconnection.setTextLengthListener(MainActivity@ this)
    }

    override fun onTextLengthOutOfLimit() {
        Toast.makeText(MainActivity@ this, "onTextLengthOutOfLimit", Toast.LENGTH_SHORT).show()
    }
}
