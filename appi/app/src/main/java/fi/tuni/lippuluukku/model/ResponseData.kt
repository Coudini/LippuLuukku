package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//@JsonIgnoreProperties(ignoreUnknown = true)


import kotlinx.serialization.*
import kotlinx.serialization.json.*

data class ResponseData(
        val events: MutableList<Event>,
)