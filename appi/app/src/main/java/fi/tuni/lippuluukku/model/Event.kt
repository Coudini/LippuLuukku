package fi.tuni.lippuluukku.model

data class Event(
        val name : String? = null,
        val url : String? = null,
        val images : MutableList<EventImage>? = null,
        val dates : EventDate? = null,
        val classifications : MutableList<Classification>? = null,
        val priceRanges : MutableList<Price>? = null,
        val _embedded : EventEmbedded? = null
)
