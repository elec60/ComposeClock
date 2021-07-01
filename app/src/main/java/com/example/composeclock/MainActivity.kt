package com.example.composeclock

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AnalogClock()
            }
        }
    }
}


@Composable
fun AnalogClock() {
    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        var initHour by remember {
            mutableStateOf(0f)
        }
        var initMinute by remember {
            mutableStateOf(0f)
        }
        var initSecond by remember {
            mutableStateOf(0f)
        }
        LaunchedEffect(key1 = 0) {
            val calendar = Calendar.getInstance()

            val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    calendar.timeInMillis = System.currentTimeMillis()
                    val h = calendar.get(Calendar.HOUR)
                    val m = calendar.get(Calendar.MINUTE)
                    val s = calendar.get(Calendar.SECOND)

                    initHour = 360f * (h + m / 60f) / 12
                    initMinute = 360f * m / 60
                    initSecond = 360f * s / 60

                    handler.postDelayed(this, 1000)
                }
            }
            handler.post(runnable)
        }

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                ) {
                    append("J")
                }
                append("etpack")
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                ) {
                    append("C")
                }
                append("ompose")

            },
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        Canvas(modifier = Modifier.size(200.dp)) {
            for (angle in 0..354 step 6) {
                rotate(angle.toFloat()) {
                    drawLine(
                        color = Color.Blue,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(
                            size.width / 2,
                            if (angle % 5 == 0) 60.dp.value else 30.dp.value
                        ),
                        strokeWidth = if (angle % 5 == 0) 4.dp.value else 2.dp.value
                    )
                }

                rotate(initHour) {
                    drawLine(
                        color = Color.Black,
                        start = this.center,
                        end = Offset(size.width / 2, 160.dp.value),
                        strokeWidth = 8.dp.value
                    )
                }

                rotate(initMinute) {
                    drawLine(
                        color = Color.Black,
                        start = this.center,
                        end = Offset(size.width / 2, 75.dp.value),
                        strokeWidth = 4.dp.value
                    )
                }

                rotate(initSecond) {
                    drawLine(
                        color = Color.Red,
                        start = this.center,
                        end = Offset(size.width / 2, 65.dp.value),
                        strokeWidth = 4.dp.value
                    )
                }

                drawCircle(
                    color = Color.Black,
                    radius = 10.dp.value
                )

            }


        }
    }
}