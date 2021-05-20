package fi.tuni.lippuluukku.model

data class Venue(
        val name : String? = null,
        val city : VenueCity? = null,
        val address: VenueAddress? = null,
        val location: VenueLocation? = null
)
