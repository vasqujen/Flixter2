package com.example.Flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.Flixter.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val Person_SEARCH_URL =
    "https://api.themoviedb.org/3/person/popular\n?api_key=${SEARCH_API_KEY}"


class MainActivity : AppCompatActivity() {

    private val people = mutableListOf<Person>()
    private lateinit var personRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        personRecyclerView = findViewById(R.id.list)
        val articleAdapter = PersonAdapter(this, people)
        personRecyclerView.adapter = articleAdapter

        personRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar

        progressBar.show()

        val client = AsyncHttpClient()
        client.get(Person_SEARCH_URL , object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")

                try {
                    val resultsJson = json.jsonObject.getJSONArray("results")

                    people.addAll(Person.fromJasonArray(resultsJson))

                    // Reload the screen
                    progressBar.hide()
                    articleAdapter.notifyDataSetChanged()


                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }

//                val resultsJson = json.jsonObject.get("results").toString()
//
//                val gson = Gson()
//                val arrayPersonType = object : TypeToken<List<Person>>() {}.type
//
//                val models: List<Person> = gson.fromJson(resultsJson, arrayPersonType)
//
//                personRecyclerView.adapter = PersonAdapter(this@MainActivity ,models)
//
//                // Reload the screen
//                articleAdapter.notifyDataSetChanged()


            }

        })

    }
}