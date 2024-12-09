package com.example.orgs.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.orgs.model.Produtos

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produtos")
    fun getAll(): List<Produtos>

    @Query("SELECT * FROM Produtos WHERE id = :id")
    fun get(id: Long): Produtos?

    @Insert
    fun insertAll(vararg produtos: Produtos)

    @Delete
    fun delete(vararg produtos: Produtos)

    @Update
    fun updateProdutos(vararg produtos: Produtos)

    @Query("SELECT * FROM Produtos ORDER BY nome DESC")
    fun getNomeDesc(): List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY nome ASC")
    fun getNomeAsc(): List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY descricao DESC")
    fun getDescricaoDes(): List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY descricao ASC")
    fun getDescricaoAsc(): List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY valor DESC")
    fun getValorDesc(): List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY valor ASC")
    fun getValorAsc(): List<Produtos>
}