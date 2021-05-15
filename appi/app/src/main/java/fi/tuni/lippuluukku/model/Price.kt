package fi.tuni.lippuluukku.model

data class Price(
        val type : String,
        val currency : String,
        val min : Double,
        val max : Double
)
