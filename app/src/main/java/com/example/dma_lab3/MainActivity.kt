package com.example.dma_lab3

import BlockDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var BlockRVArr: ArrayList<IBlockInRV> = ArrayList()
    private lateinit var blockDatabase: BlockDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        blockDatabase = BlockDatabase.getDatabase(this)

        val recView: RecyclerView = findViewById(R.id.mRecyclerView)


        val mAdapter = Block_RVAdapter(BlockRVArr,
            onDeletePerson = { person ->
            lifecycleScope.launch(Dispatchers.IO) {
                // Удаление Person из базы данных
                val personEntity = blockDatabase.blockDao().getAllPersons()
                    .firstOrNull { it.firstStr == person.FirstStr}

                personEntity?.let {
                    blockDatabase.blockDao().clearPersonById(it.id)
                }

                Log.d("AppRepo", "Fetched art: ${blockDatabase.blockDao().getAllArt().size}")
                loadDataFromDatabase()
            }
        },
            onAddArt = { person ->
                lifecycleScope.launch(Dispatchers.IO) {
                    // Генерация нового арта для выбранного человека
                    val personEntity = blockDatabase.blockDao().getAllPersons()
                        .firstOrNull { it.firstStr == person.FirstStr}
                    if(personEntity!=null)
                    {
                        val art = ArtEntity(personId = personEntity.id, artTitle = "New art for ${personEntity.firstStr}")
                        blockDatabase.blockDao().insertArt(art)
                    }

                    Log.d("AppRepo", "Fetched art: ${blockDatabase.blockDao().getAllArt().size}")

                    loadDataFromDatabase()
                }
            }
        )


        recView.adapter = mAdapter
        recView.layoutManager = LinearLayoutManager(this)


        recView.adapter = mAdapter
        recView.layoutManager = LinearLayoutManager(this)

        val delAllButton = findViewById<Button>(R.id.button3)
        val addTestObj = findViewById<Button>(R.id.button2)
        val addAllObj = findViewById<Button>(R.id.button)

        delAllButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                blockDatabase.blockDao().clearPersonTable()
                blockDatabase.blockDao().clearAdTable()

                Log.d("AppRepo", "Fetched art: ${blockDatabase.blockDao().getAllArt().size}")
                loadDataFromDatabase()
            }
        }

        addTestObj.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                CoroutineScope(Dispatchers.IO).launch {
                    val personId = blockDatabase.blockDao().insertPerson(
                        PersonEntity(firstStr = Random.nextInt(1,1000).toString(), secondStr = Random.nextInt(1,1000).toString())
                    ).toInt()

                    val j = Random.nextInt(2,10);
                    for(i in 1 .. j)
                    {
                        val art = ArtEntity(personId = personId, artTitle = "Art ${i} for ${personId}")
                        blockDatabase.blockDao().insertArt(art)
                    }

                    // Додаємо AdEntity
                    // blockDatabase.blockDao().insertAd(AdEntity(firstStr = "AdTest"))

                    Log.d("AppRepo", "Fetched art: ${blockDatabase.blockDao().getAllArt().size}")

                    // Оновлюємо RecyclerView
                    loadDataFromDatabase()
                }
            }
        }

        addAllObj.setOnClickListener {

            setUpBlockArray()
        }

        CoroutineScope(Dispatchers.IO).launch {
            loadDataFromDatabase()
        }
    }

    suspend fun loadDataFromDatabase() {
        val persons = blockDatabase.blockDao().getAllPersons()
        val ads = blockDatabase.blockDao().getAllAds()

        withContext(Dispatchers.Main) {
            BlockRVArr.clear()

            for (person in persons) {
                BlockRVArr.add(PersonC(person.firstStr, person.secondStr))

                val arts = blockDatabase.blockDao().getArtsByPersonId(person.id)
                for (art in arts) {
                    BlockRVArr.add(ArtC(person.id, art.artTitle))
                }
            }

            for (ad in ads) {
                BlockRVArr.add(AdC(ad.firstStr))
            }

            findViewById<RecyclerView>(R.id.mRecyclerView).adapter?.notifyDataSetChanged()
        }
    }

    fun setUpBlockArray() {
        // Populate initial data and save to database
        CoroutineScope(Dispatchers.IO).launch {
            val firstStrs = resources.getStringArray(R.array.mFirstStr)
            val secondStrs = resources.getStringArray(R.array.mSecondStr)
            for (i in firstStrs.indices) {
                val personEntity = PersonEntity(firstStr = firstStrs[i], secondStr = secondStrs[i])
                blockDatabase.blockDao().insertPerson(personEntity)
            }

            val adStr = resources.getStringArray(R.array.mADStr)
            for (i in adStr.indices) {
                val adEntity = AdEntity(firstStr = adStr[i])
                blockDatabase.blockDao().insertAd(adEntity)
            }

            loadDataFromDatabase()
        }
    }
}