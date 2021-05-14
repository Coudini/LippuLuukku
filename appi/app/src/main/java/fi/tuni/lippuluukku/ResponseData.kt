package fi.tuni.lippuluukku

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable
/*
@Serializable
data class ResponseData(
    val _embedded: Embedded?,
    val _links : Links,
    val page: Page
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
    val images: List<Image>,
    val name: String,
    val priceRanges: List<PriceRange>,
    val url: String
)

@Serializable
data class EmbeddedX(
    val venues: List<Venue>
)

@Serializable
data class Classification(
    val primary: Boolean,
    val segment: Segment
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
data class Venue(
    val city: City,
    val location: Location,
    val name: String
)

@Serializable
data class City(
    val name: String
)

@Serializable
data class Location(
    val latitude: String,
    val longitude: String
)

@Serializable
data class Segment(
    val id: String,
    val name: String
)

@Serializable
data class Start(
    val dateTime: String,
    val localDate: String,
    val localTime: String
)
*/