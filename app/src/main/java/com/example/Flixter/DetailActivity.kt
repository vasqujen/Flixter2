package com.example.Flixter

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var faceView: ImageView
    private lateinit var movieView: ImageView
    private lateinit var movieName: TextView
    private lateinit var nameView: TextView
    private lateinit var detailsView: TextView
    private lateinit var movie: Movie



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        faceView = findViewById(R.id.faceImage)
        movieView = findViewById(R.id.movieView)
        nameView = findViewById(R.id.name)
        movieName = findViewById(R.id.movie)
        detailsView = findViewById(R.id.movie_details)

        // TODO: Get the extra from the Intent
        val person = intent.getSerializableExtra(PERSON_EXTRA) as Person


        // TODO: Set the title, byline, and abstract information from the article
        nameView.text = person.name

        val knownForString=  person.knownFor

        val gson = Gson()
        val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
        val models : List<Movie> = gson.fromJson(knownForString, arrayMovieType)

        movie = models[0]
        movieName.text = movie.title
        detailsView.text = movie.description


        // Load the media images
        Glide.with(this)
            .load(person.profileImageUrl)
            .into(faceView)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movie.movieImageUrl)
            .into(movieView)
    }
}