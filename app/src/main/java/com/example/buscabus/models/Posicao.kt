package com.example.buscabus.models

data class Posicao(
    val hr: String,
    val l: List<LinhasPosicao>

) {
    override fun toString(): String {
        return "Hr(hr='$hr', l=$l)"
    }
}