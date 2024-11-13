package com.example.dma_lab3

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface BlockDao {
    // For PersonEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Query("SELECT * FROM person_table")
    suspend fun getAllPersons(): List<PersonEntity>

    // For AdEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAd(ad: AdEntity)

    @Query("SELECT * FROM ad_table")
    suspend fun getAllAds(): List<AdEntity>

    @Query("DELETE FROM person_table")
    suspend fun clearPersonTable()

    @Query("DELETE FROM ad_table")
    suspend fun clearAdTable()
}

@Database(entities = [PersonEntity::class, AdEntity::class], version = 1, exportSchema = false)
abstract class BlockDatabase : RoomDatabase() {
    abstract fun blockDao(): BlockDao

    companion object {
        @Volatile
        private var INSTANCE: BlockDatabase? = null

        fun getDatabase(context: Context): BlockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BlockDatabase::class.java,
                    "block_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


interface IBlockInRV {
    fun getType() : Int
    companion object{
        const val PERSON_TYPE = 1
        const val AD_TYPE = 2
    }
}