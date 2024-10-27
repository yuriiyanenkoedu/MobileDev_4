package com.example.dma_lab3

interface IBlockInRV {
    fun getType() : Int
    companion object{
        const val PERSON_TYPE = 1
        const val AD_TYPE = 2
    }
}