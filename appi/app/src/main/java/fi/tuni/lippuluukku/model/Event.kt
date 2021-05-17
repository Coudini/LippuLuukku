package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
        val name : String? = null,
        val url : String? = null,
        val images : MutableList<EventImage>? = null,
        val dates : Start? = null,
        val classifications : MutableList<Classification>? = null,
        val priceRanges : MutableList<Price>? = null,
        val _embedded : EventEmbedded? = null
)
