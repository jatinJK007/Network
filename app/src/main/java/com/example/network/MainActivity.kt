package com.example.network

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.network.Models.User
import com.example.network.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvId.visibility = View.GONE
            tvUserId.visibility = View.GONE
            tvBody.visibility = View.GONE
            tvTitle.visibility = View.GONE
        }
//        getRequest()
//        postRequest()
        postFRequest()


    }


    private fun postFRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("TAG", "onCreate: coroutines launched succesfully")
            val response = try {
                Instance.api.fpostreq(23,"hello","hjmahdajdajdhaja")

            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http Error${e.message}", Toast.LENGTH_LONG)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "I/O app Error${e.message}", Toast.LENGTH_LONG)
                    .show()
                return@launch
            }


            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    Log.d("TAG", "data load ok")
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvId.visibility = View.VISIBLE
                        tvUserId.visibility = View.VISIBLE
                        tvBody.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE

                        tvId.text = "id: ${response.body()!!.id}"
                        tvUserId.text = "user id: ${response.body()!!.userId}"
                        tvTitle.text = "title :${response.body()!!.title}"
                        tvBody.text = "body:${response.body()!!.body}"
                    }
                }
            }
        }
    }

    private fun getRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("TAG", "onCreate: coroutines launched succesfully")
            val response = try {
                Instance.api.getUSer()

            } catch (e: HttpException) {
                Toast.makeText(applicationContext,"http Error${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }catch (e: IOException) {
                Toast.makeText(applicationContext,"I/O app Error${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }


            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    Log.d("TAG", "data load ok")
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvId.visibility = View.VISIBLE
                        tvUserId.visibility = View.VISIBLE
                        tvBody.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE

                        tvId.text = "id: ${ response.body()!!.id}"
                        tvUserId.text = "user id: ${ response.body()!!.userId}"
                        tvTitle.text = "title :${response.body()!!.title}"
                        tvBody.text = "body:${response.body()!!.body}"
                    }
                }
            }
        }
    }

    private fun postRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("TAG", "onCreate: coroutines launched succesfully")
            val response = try {
                val user = User("new body", null, "new title", 77)

                Instance.api.createPost(user)

            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http Error${e.message}", Toast.LENGTH_LONG)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "I/O app Error${e.message}", Toast.LENGTH_LONG)
                    .show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root,"${response.code()}",Snackbar.LENGTH_INDEFINITE).show()
                    Log.d("TAG", "data load ok")
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvId.visibility = View.VISIBLE
                        tvUserId.visibility = View.VISIBLE
                        tvBody.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE

                        tvId.text = "id: ${response.body()!!.id}"
                        tvUserId.text = "user id: ${response.body()!!.userId}"
                        tvTitle.text = "title :${response.body()!!.title}"
                        tvBody.text = "body:${response.body()!!.body}"
                    }
                }
            }
        }
    }


}