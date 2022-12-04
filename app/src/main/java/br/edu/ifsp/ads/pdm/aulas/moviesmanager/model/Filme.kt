package br.edu.ifsp.ads.pdm.aulas.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filme (
    var id: Int,
    var nome: String,
    var anoLancamento: String,
    var produtora: String,
    var duracao: String,
    var flagAssistido: String,
    var nota: String,
    var genero: String,
): Parcelable