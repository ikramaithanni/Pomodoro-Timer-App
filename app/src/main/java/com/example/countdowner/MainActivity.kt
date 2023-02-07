package com.example.countdowner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val TIME_START : Long= 25 * 60 * 1000
    var timeL = TIME_START
    var timee : CountDownTimer ?= null
    var isTimeRun = false

    lateinit var title : TextView
    lateinit var time : TextView
    lateinit var start_Btn : Button
    lateinit var reset_Txt : TextView
    lateinit var progBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = findViewById(R.id.textView1)
        time = findViewById(R.id.textView2)
        reset_Txt = findViewById(R.id.textView3)
        start_Btn = findViewById(R.id.button)
        progBar = findViewById(R.id.progressBar)

        start_Btn.setOnClickListener{
            if(!isTimeRun){
                startTimer(TIME_START)
                title.text = resources.getText(R.string.keep)

            }


    }
        reset_Txt.setOnClickListener{
            resetTimer()

        }
}

    private fun startTimer(startTime : Long) {
        timee = object : CountDownTimer(TIME_START, 1000) {
            override fun onTick(p0: Long) {
                timeL = p0
                setTime()
                progBar.progress = timeL.toDouble().div(TIME_START.toDouble()).times(100).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finish", Toast.LENGTH_SHORT).show()
                isTimeRun = false
            }

        }.start()
        isTimeRun = true

    }
    private fun resetTimer(){
        timee?.cancel()
        timeL = TIME_START
        setTime()
        title.text = resources.getText(R.string.firstOne)
        isTimeRun = false
        progBar.progress = 100
    }
    private fun setTime(){
        val minutes = timeL.div(1000).div(60)
        val seconds = timeL.div(1000)%60
        val forme = String.format("%02d : %02d", minutes, seconds)
        time.setText(forme)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("time restant ", timeL)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedTime = savedInstanceState.getLong("time restant ")
        if (savedTime != TIME_START){
            startTimer(savedTime)
        }
    }


    }

