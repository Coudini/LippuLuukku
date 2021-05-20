package fi.tuni.lippuluukku.model

data class Price(
        val type : String? = null,
        val currency : String? = null,
        val min : Double? = null,
        val max : Double? = null
)
