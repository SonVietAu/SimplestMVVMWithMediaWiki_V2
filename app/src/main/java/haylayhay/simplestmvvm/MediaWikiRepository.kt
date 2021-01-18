/*
 * Copyright (c) 2017 Son Viet Au
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package haylayhay.simplestmvvm

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.MutableLiveData
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


/**
 * Created by Son Au on 18/03/2018. Modified/Extracted from RayWenderlich.com, 'Dependency Injection in Android with Dagger 2 and Kotlin' tutorial by Joe Howard and Dario Coletto. Thanks Ray, Joe and Dario :)
 */
class MediaWikiRepository {

    private val PROTOCOL = "https"
    private val LANGUAGE = "en"
    private val BASE_URL = "wikipedia.org/w/api.php"

    val dataToDisplay: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    // Search Wiki and then change the dataToDisplay on obtaining a response or a failure
    fun searchWiki(query: String) {

        val client = OkHttpClient()
        val requestBuilder = HttpUrl.parse("${PROTOCOL}://${LANGUAGE}.${BASE_URL}")?.newBuilder()

        val urlBuilder = requestBuilder
                ?.addQueryParameter("action", "query")
                ?.addQueryParameter("list", "search")
                ?.addQueryParameter("format", "json")
                ?.addQueryParameter("srsearch", query)

        Request.Builder()
                .url(urlBuilder?.build())
                .get()
                .build()
                .let {
                    client.newCall(it)
                }
                .enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        //Everything is ok, show the result if not null
                        if (response?.isSuccessful == true) {

                            response.body()?.string()?.let {
                                JSONObject(it)
                                        .getJSONObject("query")
                                        .getJSONArray("search")
                                        .let { array ->

                                            val entriesSB = StringBuffer()
                                            (0 until array.length()).map {
                                                val wikiData = array.getJSONObject(it)
                                                entriesSB.append("${wikiData.getString("title")}: ${wikiData.getString("snippet").parseHtml()}, \n")
                                            }
                                            dataToDisplay.postValue("response is successful: \n${entriesSB}")

                                        }
                            }

                        } else {
                            dataToDisplay.postValue("response is not successful: ${response?.message()}")
                        }
                    }

                    override fun onFailure(call: Call?, t: IOException?) {

                        dataToDisplay.postValue(t?.message)
                        t?.printStackTrace()

                    }
                })

    }
}

fun String?.parseHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("deprecation")
        return Html.fromHtml(this)
    }
}

