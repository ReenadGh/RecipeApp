package com.example.post

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface apiInterface {
    @POST("/recipes/")
    fun addBook(@Body book :BooksItem ) : Call<BooksItem?>


    @GET("/recipes/")
    fun getBookinfo() :Call <List<BooksItem>?>
}