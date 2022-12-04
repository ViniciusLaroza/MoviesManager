package br.edu.ifsp.ads.pdm.aulas.moviesmanager.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.R
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme

class FilmeAdapter (
    context: Context,
    private val filmeList: MutableList<Filme>
): ArrayAdapter<Filme>(context, R.layout.tile_filme, filmeList) {
    private data class TileContactHolder(val nomeTv: TextView, val notaTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val filme = filmeList[position]
        var filmeTileView = convertView
        if (filmeTileView == null) {
            // Inflo uma nova célula
            filmeTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_filme,
                    parent,
                    false
                )

            val tileContactHolder = TileContactHolder(
                filmeTileView.findViewById(R.id.nomeTv),
                filmeTileView.findViewById(R.id.notaTv),
            )
            filmeTileView.tag = tileContactHolder
        }

        with(filmeTileView?.tag as TileContactHolder) {
            nomeTv.text = filme.nome
            notaTv.text = filme.nota

        }

        return filmeTileView
    }
}