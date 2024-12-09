package com.example.orgs.database

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?): Double? = valor?.toDouble()

    @TypeConverter
    fun doubleParaBigDecimal(valor: Double?): BigDecimal = valor?.toBigDecimal() ?: BigDecimal.ZERO

}