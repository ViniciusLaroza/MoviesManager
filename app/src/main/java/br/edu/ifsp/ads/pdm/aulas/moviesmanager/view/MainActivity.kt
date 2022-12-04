package br.edu.ifsp.ads.pdm.aulas.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.R
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.adapter.FilmeAdapter
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.EXTRA_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.VIEW_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val filmeList: MutableList<Filme> = mutableListOf()

    // Adapter
    private lateinit var filmeAdapter: FilmeAdapter

    private lateinit var parl: ActivityResultLauncher<Intent>

    private fun ordenacaoListaNome(){
        filmeList.sortBy { it.nome }
        filmeAdapter.notifyDataSetChanged()
    }

    private fun ordenacaoListaNota(){
        filmeList.sortBy { it.nota.toDouble() }
        filmeAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        //fillFilmeList()

        filmeAdapter = FilmeAdapter(this, filmeList)
        amb.filmeLv.adapter = filmeAdapter

        filmeAdapter.notifyDataSetChanged()

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val filme = result.data?.getParcelableExtra<Filme>(EXTRA_FILME)

                filme?.let { _filme->
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
                val filme = filmeList[position]
                val filmeIntent = Intent(this@MainActivity, FilmeActivity::class.java)
                filmeIntent.putExtra(EXTRA_FILME, filme)
                filmeIntent.putExtra(VIEW_FILME, true)
                startActivity(filmeIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addFilmeMi -> {
                parl.launch(Intent(this, FilmeActivity::class.java))
                true
            }

            R.id.orderAlfabeticaMi -> {
                Log.i("Log", "ordena")
                //Chama a funcao de ordenação pelo Nome
                ordenacaoListaNome()
                true
            }
            R.id.orderNumberMi -> {
                Log.i("Log", "ordena 2")
                //Chama a funcao de ordenação pelo Number
                ordenacaoListaNota()
                true
            }


            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removeFilmeMi -> {
                // Remove o filme
                filmeList.removeAt(position)
                filmeAdapter.notifyDataSetChanged()
                true
            }
            R.id.editFilmeMi -> {
                // Chama a tela para editar o filme
                val contact = filmeList[position]
                val contactIntent = Intent(this, FilmeActivity::class.java)
                contactIntent.putExtra(EXTRA_FILME, contact)
                contactIntent.putExtra(VIEW_FILME, false)
                parl.launch(contactIntent)
                true
            }

            else -> { false }

        }
    }


//    private fun fillFilmeList() {
//        for (i in 1..3) {
//            filmeList.add(
//                Filme(
//                    id = i,
//                    nome = "Nome $i",
//                    anoLancamento = "Ano lancamento $i",
//                    produtora = "Produtora $i",
//                    duracao = "Duracao $i",
//                    flagAssistido = "Assistido $i",
//                    nota = "$i",
//                    genero = "Genero $i",
//                )
//            )
//        }
//    }
}