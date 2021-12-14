package com.example.ch18internalstorage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.StringBuilder
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    val log = Logger.getLogger(MainActivity::class.java.name)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnsecondactivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun saveData() {
        val filename = "ourfile.txt"
        Thread(Runnable {
            try {
                val out = openFileOutput(filename, Context.MODE_PRIVATE)
                out.use {
                    out.write(txtinput.text.toString().toByteArray())
                }
                runOnUiThread(Runnable {
                    Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                })
            }
            catch (ioe:IOException) {
                log.warning("Error while saving ${filename} : ${ioe}")
            }

        }).start()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {

        val filename = "ourfile.txt"
        Thread(Runnable {
            try {
                val input = openFileInput(filename)
                input.use {
                    var buffer = StringBuilder()
                    var bytes_read = input.read()

                    while (bytes_read != -1) {
                        buffer.append(bytes_read.toChar())
                        bytes_read = input.read()
                    }
                    runOnUiThread(Runnable {
                        txtinput.setText(buffer.toString())
                    })
                }
            }
            catch (fnfe: FileNotFoundException) {
                log.warning("file not found, occurs only once")
            }
            catch (ioe: IOException) {
                log.warning("IOException : $ioe")
            }
        }).start()
    }
}