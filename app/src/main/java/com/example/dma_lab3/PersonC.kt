package com.example.dma_lab3

class PersonC(
    public val FirstStr : String,
    public val SecondStr : String,
): IBlockInRV {
    override fun getType(): Int {
        return IBlockInRV.PERSON_TYPE
    }
}