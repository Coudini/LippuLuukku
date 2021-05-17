package fi.tuni.lippuluukku.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class VenueCity(
        val name : String?
)
