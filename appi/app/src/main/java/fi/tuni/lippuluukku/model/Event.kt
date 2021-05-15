package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//@JsonIgnoreProperties(ignoreUnknown = true)


import kotlinx.serialization.*
import kotlinx.serialization.json.*

//@Serializable
data class Event(
        val name : String,
        val url : String,
        val images : List<EventImage>,
        val dates : Start,
        val classifications : List<Classification>,
        val priceRanges : List<Price>
)
