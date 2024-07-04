package com.example.buscabus.adapters.marker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.buscabus.databinding.MarcadorVeiculoBinding
import com.example.buscabus.models.VeiculosPosicao
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class VeiculosMapAdapter(private val context: Context) : InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        val veiculo = marker.tag as? VeiculosPosicao ?: return null
        val layoutInflater = LayoutInflater.from(context)
        val binding = MarcadorVeiculoBinding.inflate(layoutInflater, null, false)

        binding.textCodigoVeiculo.text = veiculo.p.toString()
        binding.textHorarioVeiculo.text = veiculo.ta

        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}