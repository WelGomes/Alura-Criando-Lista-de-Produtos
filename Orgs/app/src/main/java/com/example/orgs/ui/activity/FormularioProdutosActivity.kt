package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil3.load
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioProdutosBinding
import com.example.orgs.model.Produtos
import com.example.orgs.ui.dialog.Dialog
import java.math.BigDecimal

class FormularioProdutosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instanciar(this).produtoDao()
    }

    private var url: String? = null
    private var produtoId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Formulário Produtos")

        salvandoNoBancoDeDados()
        dialog()
        pegandoOsDadosDoDetalhesProdutos()
        pegandoOsDadosDoListaProdutos()
    }

    private fun pegandoOsDadosDoDetalhesProdutos() {
        intent.getParcelableExtra<Produtos>("EDIT")?.let {
            produtoId = it.id
            url = it.imagem.toString()
            binding.activityFormularioProdutosImagem.load(it.imagem)
            binding.activityFormularioProdutosNome.setText(it.nome.toString())
            binding.activityFormularioProdutosDescricao.setText(it.descricao.toString())
            binding.activityFormularioProdutosValor.setText(it.valor?.toPlainString())
        }
    }

    private fun pegandoOsDadosDoListaProdutos() {
        intent.getParcelableExtra<Produtos>("EDITLISTA")?.let {
            produtoId = it.id
            url = it.imagem.toString()
            binding.activityFormularioProdutosImagem.load(it.imagem)
            binding.activityFormularioProdutosNome.setText(it.nome.toString())
            binding.activityFormularioProdutosDescricao.setText(it.descricao.toString())
            binding.activityFormularioProdutosValor.setText(it.valor?.toPlainString())
        }
    }

    private fun salvandoNoBancoDeDados() {
        binding.activityFormularioProdutosBotaoSalvar.setOnClickListener {
            if(produtoId > 0) {
                dao.updateProdutos(pegandoDadosDoFormulario())
            } else {
                dao.insertAll(pegandoDadosDoFormulario())
            }
            finish()
        }
    }

    private fun dialog() {
        binding.activityFormularioProdutosImagem.setOnClickListener {
            Dialog(this).dialog(url) {
                url = it
                url?.let { carregarImagem(it) } ?: carregarImagem("")
            }
        }
    }

    private fun carregarImagem(imagem: String) {
        if(imagem.isEmpty()) {
            binding.activityFormularioProdutosImagem.load(R.drawable.img)
        } else {
            binding.activityFormularioProdutosImagem.load(imagem)
        }
    }

    private fun pegandoDadosDoFormulario(): Produtos {
        val nome = binding.activityFormularioProdutosNome.text.toString()
        val descricao = binding.activityFormularioProdutosDescricao.text.toString()
        val valor = if(binding.activityFormularioProdutosValor.text.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(binding.activityFormularioProdutosValor.text.toString())
        }

        return Produtos(
            id = produtoId,
            imagem = url,
            nome = nome,
            descricao = descricao,
            valor = valor
        )

    }

}