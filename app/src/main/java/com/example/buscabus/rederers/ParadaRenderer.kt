package com.example.buscabus.rederers

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.buscabus.R
import com.example.buscabus.models.Parada
import com.example.buscabus.utils.BitmapHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ParadaRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Parada>
) : DefaultClusterRenderer<Parada>(context, map, clusterManager) {

    private val paradaIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context, R.color.parada)
        BitmapHelper.vectorToBitmap(context, R.drawable.ic_parada_onibus_64, color)
    }

    override fun onBeforeClusterItemRendered(item: Parada, markerOptions: MarkerOptions) {
        markerOptions.title(item.title).position(item.position).icon(paradaIcon)
    }

    override fun onClusterItemRendered(clusterItem: Parada, marker: Marker) {
        marker.tag = clusterItem
    }
}