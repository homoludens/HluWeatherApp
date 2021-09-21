package net.droopia.HluWeatherApp

import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class getWeatherPage(val city: String) {

    val url = "https://www.yr.no/en/details/table/2-792680/Serbia/Central%20Serbia/Belgrade/Belgrade"

    fun onCreate(url: String?) {
        val url = url
    }


    fun getHtmlPage(mainActivity: MainActivity, dayList: MutableList<List<Any>>) {


        var hourList = mutableListOf("time", "weather", "temp", "percip", "dew point")

        Thread(Runnable {

            val doc = Jsoup.connect("$url").get()

            val tables_time = doc.select("div.details-page__table").select("h2")

            for (table_time: Element in tables_time) {
                println(table_time.text())

                var date = table_time.select("time").attr("datetime")

                var table_header = table_time.nextElementSibling()
                    .select("div.hourly-weather-table table thead tr")

                    .forEach {

                        var time = it.select("th:nth-child(1)").text()
                        var weather = it.select("th:nth-child(2)").text()
                        var temp = it.select("th:nth-child(3)").text()
                        var percip = it.select("th:nth-child(5)").text().replace(". mm","")
                        var dew_point = it.select("th:nth-child(9)").text()

                        var timeList = mutableListOf("Date: ", date)
                        dayList.add(timeList)
                        hourList = mutableListOf(time, weather, temp, percip, dew_point)

                        dayList.add(hourList)
                    }

                var table_row = table_time.nextElementSibling()
                    .select("div.hourly-weather-table table tbody tr")

                    .forEach {

                        var time = it.select("td:nth-child(1)").text() + "h"
                        var weather = it.select("td:nth-child(2) span span div img").attr("alt")
                        weather = weather.let {
                            if(it.length > 14 ) {
                                println("it take 10: ${it.take(14)}")
                                return@let it.take(14)
                            } else {
                                return@let it
                            }
                        }
                        var temp = it.select("td:nth-child(3)").text().replace("Temperature","")
                        var percip = it.select("td:nth-child(5)").text()
                        var dew_point = it.select("td:nth-child(9)").text().replace("Temperature","")

                        hourList = mutableListOf(time, weather, temp, percip, dew_point)
                        dayList.add(hourList)
                    }
            }

            // try to touch View of UI thread
            mainActivity.runOnUiThread(Runnable {
                val table = mainActivity.findViewById<View>(R.id.weather_table) as TableLayout
                var text = mainActivity.findViewById<View>(R.id.textTest) as TextView
                text.text = ""

                dayList.forEach{

                    val row = TableRow(mainActivity)

                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )

                    params.rightMargin = 10
                    row.layoutParams = params

                    it.forEach{
                        val param = it
                        val cell = TextView(mainActivity)
                        cell.text = it.toString()

                        row.addView(cell,params)

                    }

                    table.addView(row, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT))
                }
            })
        }).start()
    }


}