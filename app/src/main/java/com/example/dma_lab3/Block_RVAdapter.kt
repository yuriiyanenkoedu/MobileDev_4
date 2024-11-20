package com.example.dma_lab3

import BlockDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Block_RVAdapter(private val blockInRVArr: ArrayList<IBlockInRV>,
                      private val onDeletePerson: (PersonC) -> Unit,
                      private val onAddArt: (PersonC) -> Unit,)
    :RecyclerView.Adapter<Block_RVAdapter.BlockViewHolder>() {


    abstract class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: IBlockInRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return when (viewType) {
            IBlockInRV.PERSON_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_view_row, parent, false)
                PersonHolder(view,onDeletePerson,onAddArt)
            }
            IBlockInRV.AD_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_view_row, parent, false)
                ADHolder(view)
            }
            IBlockInRV.ART_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.art_view_row, parent, false)
                ArtHolder(view)
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
    class PersonHolder(itemView: View,
                       private val onDeletePerson: (PersonC) -> Unit,
                       private val onAddArt: (PersonC) -> Unit) : BlockViewHolder(itemView) {
        private val _firstTextName: TextView = itemView.findViewById(R.id.textView)
        private val _secondTextName: TextView = itemView.findViewById(R.id.textView2)
        private val _image : ImageView = itemView.findViewById(R.id.imageView)

        override fun bind(item: IBlockInRV,) {
            if (item is PersonC) {
                _firstTextName.text = item.FirstStr
                _secondTextName.text = item.SecondStr

                _image.setOnClickListener{
                    onAddArt(item)
                }

                itemView.setOnClickListener{
                   onDeletePerson(item)
                }
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

    class ArtHolder(itemView: View) : BlockViewHolder(itemView) {
        private val artTitleTextView: TextView = itemView.findViewById(R.id.artTitleTextView)

        override fun bind(item: IBlockInRV) {
            if (item is ArtC) {
                artTitleTextView.text = item.artTitle
            }
        }
    }
}