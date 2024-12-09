package com.example.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Produtos(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val imagem: String?,
    val nome: String?,
    val descricao: String?,
    val valor: BigDecimal?
) : Parcelable