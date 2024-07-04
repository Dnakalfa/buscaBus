package com.example.buscabus.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.buscabus.R
import com.example.buscabus.adapters.marker.ParadasMapAdapter
import com.example.buscabus.adapters.marker.VeiculosMapAdapter
import com.example.buscabus.adapters.marker.VeiculosPrevisaoMapAdapter
import com.example.buscabus.apis.OlhoVivoAPI
import com.example.buscabus.apis.RetrofitHelper
import com.example.buscabus.databinding.ActivityMapsBinding
import com.example.buscabus.models.LinhasPosicao
import com.example.buscabus.models.Parada
import com.example.buscabus.models.VeiculosPosicao
import com.example.buscabus.models.VeiculosPrevisao
import com.example.buscabus.rederers.ParadaRenderer
import com.example.buscabus.rederers.VeiculoPrevisaoRenderer
import com.example.buscabus.rederers.VeiculoRenderer
import com.example.buscabus.utils.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var retrofit: Retrofit
    private lateinit var listaLinhas: List<LinhasPosicao>
    private var execucao = false

    private lateinit var listaParadas: List<Parada>

    private val paradaIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.parada)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_parada_onibus_64, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        inicializarComponentes()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isBuildingsEnabled = false
        val saoPaulo = LatLng(-23.53, -46.62)
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0F))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(saoPaulo))
        carregarDados()
    }

    private fun inicializarComponentes() {
        binding.switchVeiculos.setOnClickListener {
            recarregar()
        }
        binding.switchParada.setOnClickListener {
            recarregar()
        }
        binding.fabCarregar.setOnClickListener {
            recarregar()
            carregarDados()
        }
    }

    private fun carregarDados() {
        val extras = intent.extras
        if (extras != null) {
            binding.switchVeiculos.visibility = View.GONE
            binding.switchParada.visibility = View.GONE
            mMap.clear()

            val parada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getParcelable("parada", Parada::class.java)
            } else {
                extras.getParcelable("parada")
            }
            if (parada != null) {
                mMap.setInfoWindowAdapter(ParadasMapAdapter(this))
                val marker = mMap.addMarker(
                    MarkerOptions().position(parada.position).icon(paradaIcon).title(parada.title)
                )
                marker?.tag = parada

                CoroutineScope(Dispatchers.IO).launch {
                    val veiculos = async { buscarPrevisaoParadas(parada.cp) }.await()
                    withContext(Dispatchers.Main) {
                        if (!execucao) {
                            val bounds = LatLngBounds.builder()
                            bounds.include(parada.position)
                            veiculos.forEach {
                                bounds.include(LatLng(it.py, it.px))
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
                            execucao = true
                        }
                        addClusteredVeiculosPrevisao(mMap, veiculos)
                    }
                }
            }
        }
    }

    private fun recarregar() {
        if (binding.switchVeiculos.isChecked) {
            val listaVeiculos = mutableListOf<VeiculosPosicao>()
            CoroutineScope(Dispatchers.IO).launch {
                listaLinhas = async { carregarVeiculos() }.await()
                listaLinhas.forEach { linha ->
                    linha.vs.forEach {
                        listaVeiculos.add(it)
                    }
                }
                withContext(Dispatchers.Main) {
                    addClusteredVeiculos(mMap, listaVeiculos)
                }
            }
        }
        if (binding.switchParada.isChecked) {
            CoroutineScope(Dispatchers.IO).launch {
                listaParadas = async { carregarParadas("") }.await()
                withContext(Dispatchers.Main) {
                    addClusteredParadas(mMap, listaParadas)
                }
            }
        }
        if (binding.switchParada.visibility == View.VISIBLE && binding.switchVeiculos.visibility == View.VISIBLE) {
            mMap.clear()
        }
    }

    private suspend fun carregarVeiculos(): List<LinhasPosicao> {
        val linhas = mutableListOf<LinhasPosicao>()
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).posicaoVeiculos()
            if (resultado.isSuccessful) {
                val hr = resultado.body()
                hr?.l?.forEach { linha ->
                    linhas.add(linha)
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, R.string.text_no_internet, Toast.LENGTH_LONG)
                    .show()
            }
        }
        return linhas
    }

    private suspend fun carregarParadas(nome: String): List<Parada> {
        val paradas = mutableListOf<Parada>()
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarParadas(nome)
            if (resultado.isSuccessful) {
                val parada = resultado.body()
                parada?.forEach {
                    paradas.add(it)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, R.string.text_no_internet, Toast.LENGTH_LONG)
                    .show()
            }
        }
        return paradas
    }

    private fun addClusteredParadas(googleMap: GoogleMap, paradas: List<Parada>) {
        val clusterManager = ClusterManager<Parada>(this, googleMap)
        clusterManager.renderer =
            ParadaRenderer(
                this,
                googleMap,
                clusterManager
            )

        clusterManager.markerCollection.setInfoWindowAdapter(ParadasMapAdapter(this))

        clusterManager.addItems(paradas)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

    private fun addClusteredVeiculos(googleMap: GoogleMap, veiculos: List<VeiculosPosicao>) {
        val clusterManager = ClusterManager<VeiculosPosicao>(this, googleMap)
        clusterManager.renderer =
            VeiculoRenderer(
                this,
                googleMap,
                clusterManager
            )

        clusterManager.markerCollection.setInfoWindowAdapter(VeiculosMapAdapter(this))

        clusterManager.addItems(veiculos)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

    private fun addClusteredVeiculosPrevisao(
        googleMap: GoogleMap,
        veiculos: List<VeiculosPrevisao>,
    ) {
        val clusterManager = ClusterManager<VeiculosPrevisao>(this, googleMap)
        clusterManager.renderer =
            VeiculoPrevisaoRenderer(
                this,
                googleMap,
                clusterManager
            )

        clusterManager.markerCollection.setInfoWindowAdapter(VeiculosPrevisaoMapAdapter(this))

        clusterManager.addItems(veiculos)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

    private suspend fun buscarPrevisaoParadas(codigo: Int): List<VeiculosPrevisao> {
        val previsoes = mutableListOf<VeiculosPrevisao>()
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarPrevisaoParadas(codigo)
            if (resultado.isSuccessful) {
                val previsao = resultado.body()
                previsao?.p?.l?.forEach { linhasPrevisao ->
                    linhasPrevisao.vs.forEach { veiculosPrevisao ->
                        previsoes.add(veiculosPrevisao)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, R.string.text_no_internet, Toast.LENGTH_LONG)
                    .show()
            }
        }
        return previsoes
    }
}