package net.droopia.HluWeatherApp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TableLayout


class MainActivity : AppCompatActivity() {

    var hourList = mutableListOf("time", "temp", "dew point")
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

//        val time = "11"
//        val temp = "13"
//        val dew_point = "32"
//        val row = TableRow(this)
//        val cell_1 = TextView(this)
//        cell_1.text = time
//        val cell_2 = TextView(this)
//        cell_2.text = temp
//        val cell_3 = TextView(this)
//        cell_3.text = dew_point
//
//        row.addView(cell_1)
//        row.addView(cell_2)
//        row.addView(cell_3)
//        table.addView(row, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT))



    }


}