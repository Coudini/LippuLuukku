package fi.tuni.lippuluukku

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


//@JsonIgnoreProperties(ignoreUnknown = true)
//data class StarWarsJsonObject(var results: MutableList<Person>? = null)

import kotlinx.serialization.*
import kotlinx.serialization.json.*

//@Serializable
@JsonIgnoreProperties(ignoreUnknown = true)
data class MyJsonObject2(var results: MutableList<ResponseData>? = null)
