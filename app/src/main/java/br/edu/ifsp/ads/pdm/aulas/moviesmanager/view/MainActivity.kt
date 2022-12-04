package br.edu.ifsp.ads.pdm.aulas.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.adapter.FilmeAdapter
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.EXTRA_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val filmeList: MutableList<Filme> = mutableListOf()

    // Adapter
    private lateinit var filmeAdapter: FilmeAdapter

    //private lateinit var parl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillFilmeList()

        filmeAdapter = FilmeAdapter(this, filmeList)
        amb.filmeLv.adapter = filmeAdapter

        filmeAdapter.notifyDataSetChanged()


    }

    private fun fillFilmeList() {
        for (i in 1..3) {
            filmeList.add(
                Filme(
                    id = i,
                    nome = "Nome $i",
                    anoLancamento = "Ano lancamento $i",
                    produtora = "Produtora $i",
                    duracao = "Duracao $i",
                    flagAssistido = "Assistido $i",
                    nota = "Nota $i",
                    genero = "Genero $i",
                )
            )
        }
    }
}