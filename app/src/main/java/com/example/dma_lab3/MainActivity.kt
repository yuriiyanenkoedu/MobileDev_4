package com.example.dma_lab3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var BlockRVArr : ArrayList<IBlockInRV> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recView : RecyclerView = findViewById(R.id.mRecyclerView)

        setUpBlockArray()

        val mAdapter : Block_RVAdapter = Block_RVAdapter(BlockRVArr)
        recView.adapter = mAdapter
        recView.layoutManager = LinearLayoutManager(this)
    }

    fun setUpBlockArray()
    {

        val firstStrs = resources.getStringArray(R.array.mFirstStr)
        val secondStrs = resources.getStringArray(R.array.mSecondStr)

        for (i in firstStrs.indices) {
            BlockRVArr.add(PersonC(firstStrs[i], secondStrs[i]))
        }

        val adStr = resources.getStringArray(R.array.mADStr)
        for (i in adStr.indices) {
            BlockRVArr.add(AdC(adStr[i]))
        }

        BlockRVArr.shuffle()
    }
}