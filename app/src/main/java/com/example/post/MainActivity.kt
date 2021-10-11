package com.example.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


 lateinit var goToAdd : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToAdd = findViewById(R.id.add)

        showInfo()

        goToAdd.setOnClickListener {

            val intent = Intent(this, addRecipe::class.java)
            startActivity(intent)
        }




    }


    private fun showInfo() {



        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<List<BooksItem>?> = api.getBookinfo()!!.awaitResponse()
            if (response.isSuccessful) {
                val dataBody = response.body()!!
                Log.d("dataShow" , "Success :  " + dataBody.toString())

                withContext(Dispatchers.Main) {
                    val myRV = findViewById<RecyclerView>(R.id.rvMain)
                    myRV.adapter = RecyclerViewAdapter(this@MainActivity ,
                        dataBody as ArrayList<BooksItem>
                    )
                    myRV.layoutManager = LinearLayoutManager(this@MainActivity)
                    myRV.adapter!!.notifyDataSetChanged()


                }

            }


            else {
                Log.d("dataShow" , "failed !")

            }


        }
    }





}