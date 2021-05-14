package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


//@JsonIgnoreProperties(ignoreUnknown = true)
//data class StarWarsJsonObject(var results: MutableList<Person>? = null)

import kotlinx.serialization.*
import kotlinx.serialization.json.*

//@Serializable
//@JsonIgnoreProperties(ignoreUnknown = true)

//@Serializable
data class JsonObject(var results: List<ResponseData>)
