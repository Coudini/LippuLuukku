package fi.tuni.lippuluukku

/*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


//@JsonIgnoreProperties(ignoreUnknown = true)
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class ResponseDataGps(
        val _embedded: Embedded
)

@Serializable
data class Embedded(
        val events: List<Event>
)

@Serializable
data class Event(
        val _embedded: EmbeddedX,
        val classifications: List<Classification>,
        val dates: Dates,
        val distance: Double,
        val id: String,
        val images: List<Image>,
        val locale: String,
        val name: String,
        val priceRanges: List<PriceRange>,
        val sales: Sales,
        val test: Boolean,
        val type: String,
        val units: String,
        val url: String
)

@Serializable
data class EmbeddedX(
        val venues: List<Venue>
)

@Serializable
data class Classification(
        val family: Boolean,
        val genre: Genre,
        val primary: Boolean,
        val segment: Segment,
        val subGenre: SubGenre
)

@Serializable
data class Dates(
        val start: Start
)

@Serializable
data class Image(
        val fallback: Boolean,
        val height: Int,
        val ratio: String,
        val url: String,
        val width: Int
)

@Serializable
data class PriceRange(
        val currency: String,
        val max: Double,
        val min: Double,
        val type: String
)

@Serializable
data class Sales(
        val `public`: Public
)

@Serializable
data class Venue(
        val _links: Links,
        val address: Address,
        val city: City,
        val country: Country,
        val distance: Double,
        val id: String,
        val locale: String,
        val location: Location,
        val name: String,
        val postalCode: String,
        val test: Boolean,
        val timezone: String,
        val type: String,
        val units: String,
        val upcomingEvents: UpcomingEvents,
        val url: String
)

@Serializable
data class Links(
        val self: Self
)

@Serializable
data class Address(
        val line1: String
)

@Serializable
data class City(
        val name: String
)

@Serializable
data class Country(
        val countryCode: String,
        val name: String
)

@Serializable
data class Location(
        val latitude: String,
        val longitude: String
)

@Serializable
data class UpcomingEvents(
        val _total: Int
)

@Serializable
data class Self(
        val href: String
)

@Serializable
data class Genre(
        val id: String,
        val name: String
)

@Serializable
data class Segment(
        val id: String,
        val name: String
)

@Serializable
data class SubGenre(
        val id: String,
        val name: String
)

@Serializable
data class Start(
        val dateTime: String,
        val localDate: String,
        val localTime: String
)

@Serializable
data class Public(
        val endDateTime: String,
        val startDateTime: String,
        val startTBA: Boolean,
        val startTBD: Boolean
)
*/
