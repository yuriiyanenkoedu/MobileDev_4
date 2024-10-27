package com.example.dma_lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Block_RVAdapter(private val blockInRVArr: ArrayList<IBlockInRV>):RecyclerView.Adapter<Block_RVAdapter.BlockViewHolder>() {


    abstract class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: IBlockInRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return when (viewType) {
            IBlockInRV.PERSON_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_view_row, parent, false)
                PersonHolder(view)
            }
            IBlockInRV.AD_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_view_row, parent, false)
                ADHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return blockInRVArr.size
    }

    override fun getItemViewType(position: Int): Int {
        return blockInRVArr[position].getType()
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.bind(blockInRVArr[position])
    }

    // ViewHolder для PersonC
    class PersonHolder(itemView: View) : BlockViewHolder(itemView) {
        private val firstTextName: TextView = itemView.findViewById(R.id.textView)
        private val secondTextName: TextView = itemView.findViewById(R.id.textView2)

        override fun bind(item: IBlockInRV) {
            if (item is PersonC) {
                firstTextName.text = item.FirstStr
                secondTextName.text = item.SecondStr
            }
        }
    }

    // ViewHolder для AdC
    class ADHolder(itemView: View) : BlockViewHolder(itemView) {
        private val ADTextName: TextView = itemView.findViewById(R.id.textView3)

        override fun bind(item: IBlockInRV) {
            if (item is AdC) {
                ADTextName.text = item.FirstStr
            }
        }
    }
}