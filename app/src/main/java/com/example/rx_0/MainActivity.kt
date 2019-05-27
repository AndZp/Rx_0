package com.example.rx_0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testObserver()
    }


}





fun toHumanDate(time: Long, withDate: Boolean = false): String
{
    val dateFormat = "dd/MM/yyyy - "
    val timeFormat = "HH:mm:ss:SSS"

    val format: String = if (withDate) dateFormat + timeFormat else timeFormat
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(time)
}


fun Long.toHumanReadableTime(withDate: Boolean = false): String {
    return toHumanDate(this, withDate)
}