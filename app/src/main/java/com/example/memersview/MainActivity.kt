package com.example.memersview

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

    var currentImage: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memeImage = findViewById<ImageView>(R.id.memeImage)
        loadMeme()
    }

    private fun loadMeme(){
        var myProgressBar = findViewById(R.id.progressBar) as ProgressBar
        myProgressBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        currentImage = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImage ,null,
            { response ->
                val url = response.getString("url")
                val memeImage = findViewById<ImageView>(R.id.memeImage)
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        myProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        myProgressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImage)
            },
            {  })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.putExtra(Intent.EXTRA_TEXT , "Hey check out this cool meme $currentImage")
        val chosser = Intent.createChooser(intent,"Share this meme by using...")
        startActivity(chosser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}