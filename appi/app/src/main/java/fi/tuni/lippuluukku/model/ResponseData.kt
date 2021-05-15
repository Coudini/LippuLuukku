package fi.tuni.lippuluukku.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseData(
        val events: MutableList<Event>? = null,
)