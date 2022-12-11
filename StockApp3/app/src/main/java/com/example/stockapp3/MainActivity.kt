package com.example.stockapp3

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.stockapp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StockAdapter
    private var items : MutableList<Stock> = mutableListOf();
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Recycling View
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        items.add(Stock("Apple", "AAPL", 115.69));

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        val adapter = StockAdapter(items)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            var input = findViewById(R.id.stockInput) as EditText
            var test = findViewById(R.id.testView) as TextView


            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + input.text  + "&apikey=RTG25IAA4LFO2FUJ",
                null,
                { response ->
                    items.add(Stock(
                        response.getJSONObject("Global Quote").getString("01. symbol"),
                        response.getJSONObject("Global Quote").getString("03. high"),
                        response.getJSONObject("Global Quote").getString("05. price").toDouble(),
                    ))

                    adapter.notifyItemInserted(items.size - 1)
                },
                { error ->
                    Log.e("Scraper","Error $error")
                }
            )
            requestQueue.add(jsonObjectRequest)

            /*
            val deleteButton = findViewById<Button>(R.id.deleteButton)
            deleteButton.setOnClickListener {
                var input = findViewById(R.id.nameView) as TextView
                var test = findViewById(R.id.testView) as TextView


            }
            */

            test.text = input.text
        }

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupRecycler(items : MutableList<Stock>) {
        adapter = StockAdapter(items)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )
        recyclerView!!.adapter = adapter
    }
}