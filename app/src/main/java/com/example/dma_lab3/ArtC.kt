package com.example.dma_lab3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "art_table",
    foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["person_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("person_id")]
)
data class ArtEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "person_id") val personId: Int,
    @ColumnInfo(name = "art_title") val artTitle: String
)

data class ArtC(
    val personId: Int,
    val artTitle: String
) : IBlockInRV {
    override fun getType(): Int {
        return IBlockInRV.ART_TYPE
    }
}