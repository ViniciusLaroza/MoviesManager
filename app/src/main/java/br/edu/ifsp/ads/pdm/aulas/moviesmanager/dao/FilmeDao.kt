package br.edu.ifsp.ads.pdm.aulas.moviesmanager.dao

import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme

interface FilmeDao {
    fun createFilme(filme: Filme): Int
    fun retrieveFilme(id: Int): Filme?
    fun retrieveFilmes(): MutableList<Filme>
    fun updateFilme(filme: Filme): Int
    fun deleteFilme(id: Int): Int
}