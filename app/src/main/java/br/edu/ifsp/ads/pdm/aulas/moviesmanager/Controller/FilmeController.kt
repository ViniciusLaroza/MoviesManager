package br.edu.ifsp.ads.pdm.aulas.moviesmanager.Controller

import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.dao.FilmeDao
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.database.FilmeDaoSqlite
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.view.MainActivity

class FilmeController (mainActivity: MainActivity) {
    private val filmeAOImp: FilmeDao = FilmeDaoSqlite(mainActivity)
    fun insertFilme(filme: Filme) = filmeAOImp.createFilme(filme)
    fun getFilme(id: Int) = filmeAOImp.retrieveFilme(id)
    fun getFilmes() = filmeAOImp.retrieveFilmes()
    fun editFilme(filme: Filme) = filmeAOImp.updateFilme(filme)
    fun removeFilme(id: Int) = filmeAOImp.deleteFilme(id)
}