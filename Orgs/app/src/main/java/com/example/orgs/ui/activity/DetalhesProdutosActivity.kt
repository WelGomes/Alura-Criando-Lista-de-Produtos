package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil3.load
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityDetalhesProdutosBinding
import com.example.orgs.model.Produtos
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProdutosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instanciar(this).produtoDao()
    }

    private var produtos: Produtos? = null
    private var produtosId: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recebendoInfo()
    }

    override fun onResume() {
        super.onResume()

        produtosId?.let {
            produtos = dao.get(it)
        }

        produtos?.let {
            preenchendoPagina(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detalhes_produtos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detalhes_produtos_editar -> {
                val intent = Intent(
                    this,
                    FormularioProdutosActivity::class.java
                ).apply {
                    putExtra("EDIT", produtos)
                }
                startActivity(intent)
                true
            }

            R.id.menu_detalhes_produtos_deletar -> {
                produtos?.let { dao.delete(it) }
                finish()
                true
            }

            else -> false
        }
    }

    private fun recebendoInfo() {
        intent.getParcelableExtra<Produtos>("KEY")?.let {
            produtosId = it.id
            preenchendoPagina(it)
        }
    }

    private fun preenchendoPagina(produtos: Produtos) {
        binding.activityDetalhesProdutosImageView.load(produtos.imagem ?: R.drawable.img)
        binding.activityDetalhesProdutosTitulo.text = produtos.nome
        binding.activityDetalhesPrdutosDescricao.text = produtos.descricao
        binding.activityDetalhesProdutosValor.text = produtos.valor?.let { converterParaReal(it) } ?: converterParaReal(BigDecimal("0.00"))
    }

    private fun converterParaReal(valor: BigDecimal): String {
        val real = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return real.format(valor)
    }
}