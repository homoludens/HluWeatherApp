package net.droopia.HluWeatherApp

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class getWebPage {

    val wiki = "https://en.wikipedia.org"

    fun onCreate(url: String?) {
        val url = url
    }


    fun getHtmlPage() {
        Thread(Runnable {
            val doc =
                Jsoup.connect("$wiki/wiki/List_of_films_with_a_100%25_rating_on_Rotten_Tomatoes")
                    .get()    // <1>
            doc.select(".wikitable:first-of-type tr td:first-of-type a")    // <2>
                .map { col -> col.attr("href") }    // <3>
                .parallelStream()    // <4>
                .map { extractMovieData(it) }    // <5>
                .filter { it != null }
                .forEach { println(it) }
        }).start()
    }

    fun extractMovieData(url: String): Movie? { // <1>
        val doc: Document
        try {
            doc = Jsoup.connect("$wiki$url").get()  // <2>
        }catch (e: Exception){
            return null
        }

        val movie = Movie() // <3>
        doc.select(".infobox tr")   // <4>
            .forEach { ele ->   // <5>
                when {
                    ele.getElementsByTag("th")?.hasClass("summary") ?: false -> {   // <6>
                        movie.title = ele.getElementsByTag("th")?.text()
                    }
                    else -> {
                        val value: String? = if (ele.getElementsByTag("li").size > 1)
                            ele.getElementsByTag("li")
                                .map(Element::text)
                                .filter(String::isNotEmpty)
                                .joinToString(", ") else
                            ele.getElementsByTag("td")?.first()?.text() // <7>

                        when (ele.getElementsByTag("th")?.first()?.text()) {    // <8>
                            "Directed by" -> movie.directedBy = value ?: ""
                            "Produced by" -> movie.producedBy = value ?: ""
                            "Written by" -> movie.writtenBy = value ?: ""
                            "Starring" -> movie.starring = value ?: ""
                            "Music by" -> movie.musicBy = value ?: ""
                            "Release date" -> movie.releaseDate = value ?: ""
                            "title" -> movie.title = value ?: ""
                        }
                    }
                }
            }
        return movie
    }
}