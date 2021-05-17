package fi.tuni.lippuluukku.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class VenueLocation(
        val longitude : String? = null,
        val latitude : String? = null
)
