package com.example.yazuyazuya.mykotlinapp2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View要素を変数に代入
        val timeText = findViewById(R.id.timeText) as TextView
        val startButton = findViewById(R.id.start) as Button
        val stopButton = findViewById(R.id.stop) as Button
        val resetButton = findViewById(R.id.reset) as Button

        // 1秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    timeText.text = it
                }

                handler.postDelayed(this, 10)
            }
        }

        // start
        startButton.setOnClickListener{
            handler.post(runnable)
        }

        // stop
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                timeText.text = it
            }
        }

    }

    // 数値を00:00:00形式の文字列に変換する関数
    // 引数timeにはデフォルト値0を設定、返却する型はnullableなString?型
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00:00"
        } else {
            //val h = time / 3600           // delayMills: 1000の時
            val h = time / 216000
            //val m = time % 3600 / 60      // delayMills: 1000の時
            val m = (time / 3600) % 60      // delayMills: 10の時
            //val s = time % 60             // delayMills: 1000の時
            //val s = (time % 600) / 10     // delayMills: 100の時
            val s = time % 3600 / 60        // delayMills: 10の時
            //val ms = (time / 60) % 100
            //val ms = (time / 6) % 10
            val ms = ((time * 10) / 6) % 100  // delayMills: 10の時

            //"%1$02d:%2$02d:%3$02d".format(h, m, s)
            //"%1$02d:%2$02d:%3$02d".format(m, s, ms)
            "%1$02d:%2$02d:%3$02d:%4$02d".format(h, m, s, ms)
        }

    }

}
