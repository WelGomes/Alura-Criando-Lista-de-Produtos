package com.example.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import coil3.load
import com.example.orgs.R
import com.example.orgs.databinding.DialogFormularioProdutosBinding

class Dialog(
    private val context: Context
) {

    fun dialog(
        url: String?,
        quandoClicarEmConfirmar: (imagem: String) -> Unit
    ) {
        DialogFormularioProdutosBinding.inflate(
            LayoutInflater.from(
                context
            )
        ).apply {
            if(url.isNullOrBlank()) {
                dialogFormularioProdutosImagem.load(R.drawable.img)
            } else {
                dialogFormularioProdutosImagem.load(url)
                dialogFormularioProdutosFormularioUrl.setText(url)
            }

            dialogFormularioProdutosBotaoCarregar.setOnClickListener {
                if(dialogFormularioProdutosFormularioUrl.text.isBlank()) {
                    dialogFormularioProdutosImagem.load(R.drawable.img)
                } else {
                    dialogFormularioProdutosImagem.load(dialogFormularioProdutosFormularioUrl.text.toString())
                }
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") {_,_ ->
                    quandoClicarEmConfirmar(dialogFormularioProdutosFormularioUrl.text.toString())
                }
                .setNegativeButton("Cancelar") {_,_ ->}
                .show()
        }
    }

}