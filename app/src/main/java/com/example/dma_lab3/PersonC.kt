package com.example.dma_lab3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_str") val firstStr: String,
    @ColumnInfo(name = "second_str") val secondStr: String
)

data class PersonC(
    public val FirstStr : String,
    public val SecondStr : String,
): IBlockInRV {
    override fun getType(): Int {
        return IBlockInRV.PERSON_TYPE
    }
}