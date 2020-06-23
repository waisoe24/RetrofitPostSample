package com.example.retrofitpostsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.retrofitpostsample.api.ApiInterface
import com.example.retrofitpostsample.model.VoteResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val BASE_URL = "https://ucsmonywaonlinevote.000webhostapp.com/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{
            var code = edit_code.text.toString()
            var id = edit_id.text.toString()

            if (TextUtils.isEmpty(code)){
                edit_code.setError("Require")
            }

            if (TextUtils.isEmpty(id)){
                edit_id.setError("Require")
            }

            if(!code.isEmpty() && !id.isEmpty()){

            voteKing(code, id)
            }
        }


    }

    fun voteKing(code: String, id: String) {

        var retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var retrofitService = retrofit.create(ApiInterface::class.java)

        var apiCall = retrofitService.voteKing(
                code, id

        )

        apiCall.enqueue(object : Callback<VoteResponse> {
            override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                Toast.makeText(applicationContext,
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<VoteResponse>, response: Response<VoteResponse>) {

                Toast.makeText(applicationContext,
                response.body().toString(),
                Toast.LENGTH_LONG).show()

//                text.text = response.body()?.message.toString()
            }

        })
    }
}