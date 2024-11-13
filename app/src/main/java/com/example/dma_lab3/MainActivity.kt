package com.example.dma_lab3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var BlockRVArr: ArrayList<IBlockInRV> = ArrayList()
    private lateinit var blockDatabase: BlockDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        blockDatabase = BlockDatabase.getDatabase(this)

        val recView: RecyclerView = findViewById(R.id.mRecyclerView)


        // Initialize RecyclerView Adapter
        val mAdapter: Block_RVAdapter = Block_RVAdapter(BlockRVArr)
        recView.adapter = mAdapter
        recView.layoutManager = LinearLayoutManager(this)

        val delAllButton =findViewById<Button>(R.id.button3)
        val addTestObj =findViewById<Button>(R.id.button2)
        val addAllObj =findViewById<Button>(R.id.button)

        delAllButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
            blockDatabase.blockDao().clearPersonTable()
            blockDatabase.blockDao().clearAdTable()
                loadDataFromDatabase()
            }
        }

        addTestObj.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                blockDatabase.blockDao().insertPerson(PersonEntity(firstStr = "Test1", secondStr = "Test2"))
                blockDatabase.blockDao().insertAd(AdEntity(firstStr = "AdTest"))
                    loadDataFromDatabase()
            }
        }

        addAllObj.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                setUpBlockArray()
                    loadDataFromDatabase()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            loadDataFromDatabase()
        }
    }

    private suspend fun loadDataFromDatabase() {
        val persons = blockDatabase.blockDao().getAllPersons()
        val ads = blockDatabase.blockDao().getAllAds()

        // Convert data from entities to displayable format
        withContext(Dispatchers.Main) {
            BlockRVArr.clear()
            for (person in persons) {
                BlockRVArr.add(PersonC(person.firstStr, person.secondStr))
            }
            for (ad in ads) {
                BlockRVArr.add(AdC(ad.firstStr))
            }
            BlockRVArr.shuffle()
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
        }
    }
}