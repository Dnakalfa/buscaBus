package com.example.buscabus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buscabus.R
import com.example.buscabus.activities.CorredoresActivity
import com.example.buscabus.activities.LinhasActivity
import com.example.buscabus.activities.MapsActivity
import com.example.buscabus.activities.ParadasActivity
import com.example.buscabus.apis.OlhoVivoAPI
import com.example.buscabus.apis.RetrofitHelper
import com.example.buscabus.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit
        CoroutineScope(Dispatchers.IO).launch {
            autenticar()
        }
        inicializarComponentes()
    }

    private fun inicializarComponentes() {
        binding.buttonMapa.setOnClickListener {
            intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLinhas.setOnClickListener {
            intent = Intent(this, LinhasActivity::class.java)
            startActivity(intent)
        }

        binding.buttonParadas.setOnClickListener {
            intent = Intent(this, ParadasActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCorredores.setOnClickListener {
            intent = Intent(this, CorredoresActivity::class.java)
            startActivity(intent)
        }

    }

    private suspend fun autenticar() {
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).autenticar()
            withContext(Dispatchers.Main) {
                if (resultado) {
                    Toast.makeText(applicationContext, "Conectado a API!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Falha ao conectar a API!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    applicationContext,
                    R.string.text_no_internet,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}