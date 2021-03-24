package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var workTimer:CountDownTimer
    lateinit var pauseTimer:CountDownTimer
    lateinit var countdownDisplay:TextView
    lateinit var startButton:Button
    lateinit var setNumberOfRepetitions: EditText

    lateinit var workTimeTextDisplay: TextView
    lateinit var setWorkTimeSeekBar: SeekBar
    lateinit var pauseTimeTextDisplay: TextView
    lateinit var setPauseTimeSeekBar: SeekBar

    var timeToCountDownInMs = 5000L
    var pauseTimeInMs = 10000L
    var timeTicks = 1000L
    var numberOfRepetitions = 0

    var workTimeInProgress = false
    var pauseTimeInProgress = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownDisplay = findViewById(R.id.countDownView)
        workTimeTextDisplay = findViewById(R.id.setWorkTimeView)
        pauseTimeTextDisplay = findViewById(R.id.setPauseTimeView)
        setNumberOfRepetitions = findViewById(R.id.setNumberOfRepetitions)

        startButton = findViewById(R.id.startCountdownButton)
        startButton.setOnClickListener {
            numberOfRepetitions = setNumberOfRepetitions.text.toString().toInt()
            startCountDown(it)
        }

        setWorkTimeSeekBar = findViewById(R.id.WorkTimeSeekBar)
        setWorkTimeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                workTimeTextDisplay.text = progress.toString()
                timeToCountDownInMs = progress * 60 * timeTicks
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        setPauseTimeSeekBar = findViewById(R.id.PauseTimeSeekBar)
        setPauseTimeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pauseTimeTextDisplay.text = progress.toString()
                pauseTimeInMs = progress * 60 * timeTicks
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }


    fun startCountDown(v: View){

        if(workTimeInProgress){
            workTimer.cancel()
        }
        if (pauseTimeInProgress){
            pauseTimer.cancel()
        }

        workTimer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                if (numberOfRepetitions == 0){
                    Toast.makeText(this@MainActivity, "Ferdig!!!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@MainActivity, "Time for a break", Toast.LENGTH_SHORT).show()
                    workTimeInProgress = false
                    startPauseCountdown(v)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        workTimeInProgress = true
        workTimer.start()
    }


    fun startPauseCountdown(v: View){

        pauseTimer = object : CountDownTimer(pauseTimeInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Back to work!", Toast.LENGTH_SHORT).show()
                pauseTimeInProgress = false
                numberOfRepetitions -= 1
                startCountDown(v)
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        pauseTimeInProgress = true
        pauseTimer.start()
    }


    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}