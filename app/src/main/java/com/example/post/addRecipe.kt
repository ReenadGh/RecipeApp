package com.example.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class addRecipe : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var auther: EditText
    lateinit var ingredient : EditText
    lateinit var instruction: EditText
    lateinit var save: Button
    lateinit var view: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        title = findViewById(R.id.title1)
        auther = findViewById(R.id.auther)
        ingredient = findViewById(R.id.Ingredients)
        instruction = findViewById(R.id.Instruction)
        save = findViewById(R.id.save)
        view = findViewById(R.id.view)


        save.setOnClickListener {


            if (title.text.toString().isNotEmpty() && auther.text.toString().isNotEmpty()&& ingredient.text.toString().isNotEmpty()&&instruction.text.toString().isNotEmpty()) {
                addRecipe(title.text.toString(),auther.text.toString(),ingredient.text.toString(),instruction.text.toString() )


            } else {

                Toast.makeText(this, "Fill all fields !! ", Toast.LENGTH_SHORT).show()


            }


        }
        view.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }



    private fun addRecipe(title: String, author: String, ingredients: String, instructions: String) {


        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<BooksItem?> = api.addBook(BooksItem(author, ingredients, instructions , title ))!!.awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("dataPush", "Success : " + data.toString())
                withContext(Dispatchers.Main) {


                }

            } else {
                Log.d("dataPush", "failed !")

            }


        }
    }
}


