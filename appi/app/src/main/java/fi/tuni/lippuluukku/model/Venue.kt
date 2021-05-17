package fi.tuni.lippuluukku.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(
        val name : String? = null,
        val city : VenueCity? = null,
        val address: VenueAddress? = null,
        val location: VenueLocation? = null
)
