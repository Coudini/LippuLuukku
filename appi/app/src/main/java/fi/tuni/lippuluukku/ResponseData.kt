package fi.tuni.lippuluukku

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseData(
    val _embedded: Embedded
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Embedded(
    val events: List<Event>
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
    val _embedded: EmbeddedX,
    val classifications: List<Classification>,
    val dates: Dates,
    val images: List<Image>,
    val name: String,
    val priceRanges: List<PriceRange>,
    val url: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class EmbeddedX(
    val venues: List<Venue>
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Classification(
    val primary: Boolean,
    val segment: Segment
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Dates(
    val start: Start
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Image(
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(
    val city: City,
    val location: Location,
    val name: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class City(
    val name: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(
    val latitude: String,
    val longitude: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Segment(
    val id: String,
    val name: String
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Start(
    val dateTime: String,
    val localDate: String,
    val localTime: String
)
