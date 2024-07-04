package com.example.buscabus.adapters.marker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.buscabus.databinding.MarcadorParadaBinding
import com.example.buscabus.models.Parada
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class ParadasMapAdapter(private val context: Context) : InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        val parada = marker.tag as? Parada ?: return null
        val layoutInflater = LayoutInflater.from(context)
        val binding = MarcadorParadaBinding.inflate(layoutInflater, null, false)

        binding.textNomeParada.text = parada.np
        binding.textEnderecoParada.text = parada.ed
        binding.textCodigoParada.text = parada.cp.toString()

        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}