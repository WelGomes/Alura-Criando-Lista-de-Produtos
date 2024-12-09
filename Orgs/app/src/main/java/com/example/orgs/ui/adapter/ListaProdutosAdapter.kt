package com.example.orgs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.orgs.R
import com.example.orgs.databinding.CardRecyclerViewBinding
import com.example.orgs.model.Produtos
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosAdapter(
    private val context: Context,
    produto: List<Produtos> = emptyList(),
    var quandoClicarNoCard: (produtos: Produtos) -> Unit = {},
    var quandoClicarNoEditar: (produtos: Produtos) -> Unit = {},
    var quandoClicarNoDeletar: (produtos: Produtos) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produto.toMutableList()

    inner class ViewHolder(private val binding: CardRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produtos

        init {
            itemView.setOnClickListener {
                quandoClicarNoCard(produto)
            }
            itemView.setOnLongClickListener {
                PopupMenu(context, it).apply {
                    menuInflater.inflate(R.menu.menu_lista_produtos, menu)
                    show()
                }.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.menu_lista_produtos_editar -> {
                            quandoClicarNoEditar(produto)
                            true
                        }
                        R.id.menu_lista_produtos_deletar -> {
                            quandoClicarNoDeletar(produto)
                            true
                        }
                        else -> false
                    }
                }
                true
            }
        }




        fun salvar(produtos: Produtos) {
            this.produto = produtos
            binding.cardRecyclerViewImagem.load(produtos.imagem ?: R.drawable.img)
            binding.cardRecyclerViewNome.text = produtos.nome
            binding.cardRecyclerViewDescricao.text = produtos.descricao
            binding.cardRecyclerViewValor.text =
                converterParaReal(produtos.valor ?: BigDecimal.ZERO)
        }

        private fun converterParaReal(valor: BigDecimal): String {
            val real = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return real.format(valor)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardRecyclerViewBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.salvar(produtos[position])
    }

    fun vincular(produtos: List<Produtos>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

}
