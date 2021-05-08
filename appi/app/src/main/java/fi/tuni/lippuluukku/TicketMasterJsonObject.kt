package fi.tuni.lippuluukku

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class TicketMasterJsonObject(var results: MutableList<EventInfo>? = null)
