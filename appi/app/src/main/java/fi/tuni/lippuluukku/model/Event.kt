package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//@JsonIgnoreProperties(ignoreUnknown = true)


import kotlinx.serialization.*
import kotlinx.serialization.json.*

//@Serializable
data class Event(
        val name : String
)
