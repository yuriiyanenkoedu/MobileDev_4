package com.example.dma_lab3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ad_table")
data class AdEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_str") val firstStr: String
)

class AdC(
    public val FirstStr : String) : IBlockInRV {
    override fun getType(): Int {
        return IBlockInRV.AD_TYPE
    }
}