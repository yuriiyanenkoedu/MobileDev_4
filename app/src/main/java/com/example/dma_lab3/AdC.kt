package com.example.dma_lab3

class AdC(
    public val FirstStr : String) : IBlockInRV {
    override fun getType(): Int {
        return IBlockInRV.AD_TYPE
    }
}