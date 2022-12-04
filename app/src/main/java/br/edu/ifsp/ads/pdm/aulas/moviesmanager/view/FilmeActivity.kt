package br.edu.ifsp.ads.pdm.aulas.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.databinding.ActivityFilmeBinding
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.EXTRA_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Constant.VIEW_FILME
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.model.Filme
import kotlin.random.Random

class FilmeActivity : AppCompatActivity(){
    private val afb: ActivityFilmeBinding by lazy {
        ActivityFilmeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val recebPessoa = intent.getParcelableExtra<Filme>(EXTRA_FILME)
        recebPessoa?.let{ _recebFilme ->
            with(afb) {
                with(_recebFilme) {
                    nomeEt.setText(nome)
                    anoLancamentoEt.setText(anoLancamento)
                    produtoraEt.setText(produtora)
                    duracaoEt.setText(duracao)
                    flagAssistidoSt.setText(flagAssistido)
                    notaEt.setText(nota)
                    generoSp.selectedItem.toString()
                }
            }
        }
        val viewPerson = intent.getBooleanExtra(VIEW_FILME, false)
        if (viewPerson) {
            afb.nomeEt.isEnabled = false
            afb.anoLancamentoEt.isEnabled = false
            afb.produtoraEt.isEnabled = false
            afb.duracaoEt.isEnabled = false
            afb.flagAssistidoSt.isEnabled = false
            afb.notaEt.isEnabled = false
            afb.generoSp.isEnabled = false
            afb.saveBt.visibility = View.GONE
        }

        afb.saveBt.setOnClickListener {
            val person = Filme(
                id = recebPessoa?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = afb.nomeEt.text.toString(),
                anoLancamento = afb.anoLancamentoEt.text.toString(),
                produtora = afb.produtoraEt.text.toString(),
                duracao = afb.duracaoEt.text.toString(),
                flagAssistido = afb.flagAssistidoSt.text.toString(),
                nota = afb.notaEt.text.toString(),
                genero = afb.generoSp.selectedItem.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_FILME, person)
            setResult(RESULT_OK, resultIntent)
            finish()

        }
    }
}



