package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var btn30:Button
    lateinit var btn60:Button
    lateinit var btn90:Button
    lateinit var btn120:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 5000L
    var timeTicks = 1000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
        }

        btn30 = findViewById<Button>(R.id.setbtn30)
        btn30.setOnClickListener(){
            setbtn30()
        }

        btn60 = findViewById<Button>(R.id.setbtn60)
        btn60.setOnClickListener(){
            setbtn60()
        }

        btn90 = findViewById<Button>(R.id.setbtn90)
        btn90.setOnClickListener(){
            setbtn90()
        }

        btn120 = findViewById<Button>(R.id.setbtn120)
        btn120.setOnClickListener(){
            setbtn120()
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }


    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun setbtn30(){
        timeToCountDownInMs = 1800000L
    }

    fun setbtn60(){
        timeToCountDownInMs = 3600000L
    }

    fun setbtn90(){
        timeToCountDownInMs = 5400000L
    }

    fun setbtn120(){
        timeToCountDownInMs = 7200000L
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}