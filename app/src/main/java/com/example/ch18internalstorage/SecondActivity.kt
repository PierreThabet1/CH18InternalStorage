package com.example.ch18internalstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*
import java.lang.StringBuilder

class SecondActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val filename = "ourfile.txt"
        Thread(Runnable {
            val input = openFileInput(filename)
            input.use {
                var buffer = StringBuilder()
                var bytes_read = input.read()
                while (bytes_read != -1) {
                    buffer.append(bytes_read.toChar())
                    bytes_read = input.read()
                }
                runOnUiThread(Runnable {
                    txtoutput.setText(buffer.toString())
                })
            }
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btnmainactivity.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}