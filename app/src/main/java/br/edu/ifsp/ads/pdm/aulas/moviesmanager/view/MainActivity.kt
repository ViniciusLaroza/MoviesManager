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
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.VIEW_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val filmeList: MutableList<Filme> = mutableListOf()

    // Adapter
    private lateinit var filmeAdapter: FilmeAdapter

    private lateinit var parl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillFilmeList()

        filmeAdapter = FilmeAdapter(this, filmeList)
        amb.filmeLv.adapter = filmeAdapter

        filmeAdapter.notifyDataSetChanged()

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val pessoa = result.data?.getParcelableExtra<Filme>(EXTRA_FILME)

                pessoa?.let { _filme->
                    val position = filmeList.indexOfFirst { it.id == _filme.id }
                    if (position != -1) {
                        // Alterar na posição
                        filmeList[position] = _filme
                    }
                    else {
                        filmeList.add(_filme)
                    }
                    filmeAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.filmeLv)

        amb.filmeLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val pessoa = filmeList[position]
                val pessoaIntent = Intent(this@MainActivity, FilmeActivity::class.java)
                pessoaIntent.putExtra(EXTRA_FILME, pessoa)
                pessoaIntent.putExtra(VIEW_FILME, true)
                startActivity(pessoaIntent)
            }
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