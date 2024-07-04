package com.example.buscabus.models

data class ParadaPrevisao(
    val cp: Int,
    val l: List<LinhasPrevisao>,
    val np: String,
    val px: Double,
    val py: Double
)