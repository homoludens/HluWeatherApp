package net.droopia.HluWeatherApp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TableLayout


class MainActivity : AppCompatActivity() {

    var hourList = mutableListOf("time", "weather", "temp", "percip", "dew point")
    var dayList = mutableListOf<List<Any>>(hourList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getHtmlPage()

    }


    fun getHtmlPage() {
        val table = findViewById<View>(R.id.weather_table) as TableLayout

        val getWebPageObject: getWeatherPage = getWeatherPage("Belgrade")
        getWebPageObject.getHtmlPage(this, dayList)


    }


}