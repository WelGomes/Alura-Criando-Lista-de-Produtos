package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instanciar(this).produtoDao()
    }

    private val adapter = ListaProdutosAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Lista de Produtos")

        activityFormulario()
    }

    override fun onResume() {
        super.onResume()
        aparecerListaCard()
        adapter.vincular(dao.getAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lista_produtos_ordens, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_lista_produtos_ordens_nome_decrecente -> {
                adapter.vincular(dao.getNomeDesc())
                true
            }

            R.id.menu_lista_produtos_ordens_nome_acrescente -> {
                adapter.vincular(dao.getNomeAsc())
                true
            }

            R.id.menu_lista_produtos_ordens_descricao_decrecente -> {
                adapter.vincular(dao.getDescricaoDes())
                true
            }

            R.id.menu_lista_produtos_ordens_descricao_acrescente -> {
                adapter.vincular(dao.getDescricaoAsc())
                true
            }

            R.id.menu_lista_produtos_ordens_valor_decrecente -> {
                adapter.vincular(dao.getValorDesc())
                true
            }

            R.id.menu_lista_produtos_ordens_valor_acrescente -> {
                adapter.vincular(dao.getValorAsc())
                true
            }

            R.id.menu_lista_produtos_ordens_sem_ordenacao -> {
                adapter.vincular(dao.getAll())
                true
            }

            else -> false
        }
    }

    private fun aparecerListaCard() {
        binding.activityListaProdutosRecyclerView.adapter = adapter
        adapter.quandoClicarNoCard = {
            val intent = Intent(
                this,
                DetalhesProdutosActivity::class.java
            ).apply {
                putExtra("KEY", it)
            }
            startActivity(intent)
        }
        adapter.quandoClicarNoEditar = {
            val intent = Intent(
                this,
                FormularioProdutosActivity::class.java
            ).apply {
                putExtra("EDITLISTA", it)
            }
            startActivity(intent)
        }

        adapter.quandoClicarNoDeletar = {
            dao.delete(it)
            adapter.vincular(dao.getAll())
        }

    }

    private fun activityFormulario() {
        binding.activityListaProdutosFAB.setOnClickListener {
            val intent = Intent(
                this, FormularioProdutosActivity::class.java
            )
            startActivity(intent)
        }
    }

}