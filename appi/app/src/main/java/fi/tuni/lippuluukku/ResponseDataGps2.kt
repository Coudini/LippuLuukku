package fi.tuni.lippuluukku

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/*
@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDataGps2(
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class EmbeddedX(
        val venues: List<Venue>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Classification(
        val family: Boolean,
        val genre: Genre,
        val primary: Boolean,
        val segment: Segment,
        val subGenre: SubGenre
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
data class Sales(
        val `public`: Public
)

@JsonIgnoreProperties(ignoreUnknown = true)
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class Links(
        val self: Self
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Address(
        val line1: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class City(
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country(
        val countryCode: String,
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(
        val latitude: String,
        val longitude: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpcomingEvents(
        val _total: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Self(
        val href: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Genre(
        val id: String,
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Segment(
        val id: String,
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SubGenre(
        val id: String,
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Start(
        val dateTime: String,
        val localDate: String,
        val localTime: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Public(
        val endDateTime: String,
        val startDateTime: String,
        val startTBA: Boolean,
        val startTBD: Boolean
)

 */