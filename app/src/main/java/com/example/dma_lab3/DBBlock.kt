import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dma_lab3.AdEntity
import com.example.dma_lab3.ArtEntity
import com.example.dma_lab3.PersonEntity

@Dao
interface BlockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity): Long

    @Query("DELETE FROM person_table WHERE id = :personId")
    suspend fun clearPersonById(personId: Int)

    @Query("SELECT * FROM person_table")
    suspend fun getAllPersons(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAd(ad: AdEntity)

    @Query("SELECT * FROM ad_table")
    suspend fun getAllAds(): List<AdEntity>

    @Query("DELETE FROM person_table")
    suspend fun clearPersonTable()

    @Query("DELETE FROM ad_table")
    suspend fun clearAdTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: ArtEntity)

    @Query("SELECT * FROM art_table")
    suspend fun getAllArt(): List<ArtEntity>

    @Query("SELECT * FROM art_table WHERE person_id = :personId")
    suspend fun getArtsByPersonId(personId: Int): List<ArtEntity>

}

@Database(entities = [PersonEntity::class, AdEntity::class,ArtEntity::class], version = 1, exportSchema = false)
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

