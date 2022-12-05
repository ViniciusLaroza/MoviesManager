package br.edu.ifsp.ads.pdm.aulas.moviesmanager.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.R
import br.edu.ifsp.ads.pdm.aulas.moviesmanager.dao.FilmeDao
import java.sql.SQLException

class FilmeDaoSqlite (context: Context) : FilmeDao {

    companion object Constant {
        private const val FILME_DATABASE_FILE = "filmes"
        private const val FILME_TABLE = "filme"
        private const val ID_COLUMN = "id"
        private const val NOME_COLUMN = "nome"
        private const val ANO_COLUMN = "anoLancamento"
        private const val PRODUTORA_COLUMN = "produtora"
        private const val DURACAO_COLUMN = "duracao"
        private const val FLAG_COLUMN = "flagAssistido"
        private const val NOTA_COLUMN = "nota"
        private const val GENERO_COLUMN = "genero"

        private const val CREATE_FILME_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $FILME_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOME_COLUMN TEXT NOT NULL, " +
                    "$ANO_COLUMN TEXT NOT NULL, " +
                    "$PRODUTORA_COLUMN TEXT NOT NULL, " +
                    "$DURACAO_COLUMN TEXT NOT NULL, "+
                    "$FLAG_COLUMN TEXT NOT NULL, "+
                    "$NOTA_COLUMN TEXT NOT NULL, "+
                    "$GENERO_COLUMN TEXT NOT NULL );"
    }

    private val filmeSqliteDatabase: SQLiteDatabase

    init {
        filmeSqliteDatabase = context.openOrCreateDatabase(
            FILME_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            filmeSqliteDatabase.execSQL(CREATE_FILME_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    private fun Filme.toContentValues() = with(ContentValues()) {
        put(NOME_COLUMN, nome)
        put(ANO_COLUMN, anoLancamento)
        put(PRODUTORA_COLUMN, produtora)
        put(DURACAO_COLUMN, duracao)
        put(FLAG_COLUMN, flagAssistido)
        put(NOTA_COLUMN, nota)
        put(GENERO_COLUMN, genero)
        this
    }

    private fun Cursor.rowToFilme() = Filme(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NOME_COLUMN)),
        getString(getColumnIndexOrThrow(ANO_COLUMN)),
        getString(getColumnIndexOrThrow(PRODUTORA_COLUMN)),
        getString(getColumnIndexOrThrow(DURACAO_COLUMN)),
        getString(getColumnIndexOrThrow(FLAG_COLUMN)),
        getString(getColumnIndexOrThrow(NOTA_COLUMN)),
        getString(getColumnIndexOrThrow(GENERO_COLUMN))
    )

    override fun createFilme(filme: Filme) = filmeSqliteDatabase.insert(
        FILME_TABLE,
        null,
        filme.toContentValues()
    ).toInt()

    override fun retrieveFilme(id: Int): Filme? {
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM $FILME_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val filme = if (cursor.moveToFirst()) cursor.rowToFilme() else null

        cursor.close()
        return filme
    }

    override fun retrieveFilmes(): MutableList<Filme> {
        val filmeList = mutableListOf<Filme>()
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM $FILME_TABLE ORDER BY $NOME_COLUMN",
            null
        )
        while (cursor.moveToNext()) {
            filmeList.add(cursor.rowToFilme())
        }
        cursor.close()
        return filmeList
    }

    override fun updateFilme(filme: Filme) = filmeSqliteDatabase.update(
        FILME_TABLE,
        filme.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(filme.id.toString())
    )

    override fun deleteFilme(id: Int) =
        filmeSqliteDatabase.delete(
            FILME_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )

}