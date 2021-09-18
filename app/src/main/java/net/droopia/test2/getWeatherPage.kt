package net.droopia.test2

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class getWeatherPage(val city: String) {

    val url = "https://www.yr.no/en/details/table/2-792680/Serbia/Central%20Serbia/Belgrade/Belgrade"

    fun onCreate(url: String?) {
        val url = url
    }


    fun getHtmlPage(mainActivity: MainActivity, dayList: MutableList<List<Any>>) {


        var hourList = mutableListOf("time", "temp", "dew point")

        Thread(Runnable {
            println("START")
            val doc = Jsoup.connect("$url").get()

            val tables_time = doc.select("div.details-page__table").select("h2")
//            var table_row =
            for (table_time: Element in tables_time) {
                println(table_time.text())
                var date = table_time.select("time").text()
                println("date: $date")
                var table_header = table_time.nextElementSibling()
                    .select("div.hourly-weather-table table thead tr")

                    .forEach {

                        var time = it.select("th:nth-child(1)").text()
                        var temp = it.select("th:nth-child(3)").text()
                        var dew_point = it.select("th:nth-child(9)").text()

//                        hourList[0] = time
//                        hourList[1] = temp
//                        hourList[2] = dew_point
                        var timeList = mutableListOf("Date: ", date, "")
                        dayList.add(timeList)
                        hourList = mutableListOf(time, temp,dew_point)
                        println("HEADER hourList: $hourList")
                        dayList.add(hourList)
                        println("HEADER dayList: $dayList")

                    }

//                table tbody tr td:nth-child(9)
                var table_row = table_time.nextElementSibling()
                    .select("div.hourly-weather-table table tbody tr")

                    .forEach {
                        println(it.select("td:nth-child(1)").text() + "h") //time
//                        println(it.select("td:nth-child(3)").text().replace("Temperature","")) //dew point
//                        println(it.select("td:nth-child(9)").text().replace("Temperature","")) //dew point

                        var time = it.select("td:nth-child(1)").text() + "h"
                        var temp = it.select("td:nth-child(3)").text().replace("Temperature","")
                        var dew_point = it.select("td:nth-child(9)").text().replace("Temperature","")

//                        println(time)
//                        println(temp)
//                        println(dew_point)

//                        hourList[0] = time
//                        hourList[1] = temp
//                        hourList[2] = dew_point
                        hourList = mutableListOf(time, temp,dew_point)
                        println("hourList: $hourList")
                        dayList.add(hourList)
                        println("dayList: $dayList")
                    }

                println("daylist:  $dayList")
            }

            // try to touch View of UI thread
            mainActivity.runOnUiThread(Runnable {
                val table = mainActivity.findViewById<View>(R.id.weather_table) as TableLayout
                var text = mainActivity.findViewById<View>(R.id.textTest) as TextView
                text.text = ""

                dayList.forEach{

                    val time = it[0]
                    val temp = it[1]
                    val dew_point = it[2]
                    val row = TableRow(mainActivity)
                    val cell_1 = TextView(mainActivity)
                    cell_1.text = time.toString()
                    val cell_2 = TextView(mainActivity)
                    cell_2.text = temp.toString()
                    val cell_3 = TextView(mainActivity)
                    cell_3.text = dew_point.toString()
                    row.addView(cell_1)
                    row.addView(cell_2)
                    row.addView(cell_3)
                    table.addView(row, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT))


                }
            })
        }).start()
    }


}