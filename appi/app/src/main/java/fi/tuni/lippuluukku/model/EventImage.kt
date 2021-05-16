package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class EventImage(
        val url : String? = null,
        val width : Int? = null
)