package com.danjcollins.gonopoly

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: MyPropertyRecyclerViewAdapter

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val btnRandom = findViewById<Button>(R.id.randomize)
        // set on-click listener
        btnRandom.setOnClickListener {
            val playerCountBox = findViewById<TextView>(R.id.num_of_players)
            val cardCountBox = findViewById<TextView>(R.id.num_of_cards)
            val playerCount = playerCountBox.text.toString().toInt()
            val cardCount = cardCountBox.text.toString().toInt()

            val service = Retrofit.Builder()
                .baseUrl("https://us-central1-idyllic-bloom-278618.cloudfunctions.net/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(GonopolyService::class.java)

            service.retrieveProperties(playerCount,cardCount)
                .enqueue(object : Callback<List<PlayerPropertySet>> {
                    override fun onResponse(call: Call<List<PlayerPropertySet>>, response: Response<List<PlayerPropertySet>>) {
                        val result = findViewById<TextView>(R.id.result)
                        result.movementMethod = ScrollingMovementMethod()
                        result.text = ""
                        response.body()?.forEach {
                            result.text = result.text.toString().plus(it.Player).plus("\n")
                            it.Props.forEach {
                                result.text = result.text.toString().plus("\t").plus(it.Name).plus(" - $").plus(it.Price).plus("\n")
                            }
                            result.text = result.text.toString().plus("\tTotal: $").plus(it.Total).plus("\n")
                        }
                    }

                    override fun onFailure(call: Call<List<PlayerPropertySet>>, t: Throwable) = t.printStackTrace()
                })
            it.hideKeyboard()
        }
    }
}
