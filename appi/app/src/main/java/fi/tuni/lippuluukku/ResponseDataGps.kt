package fi.tuni.lippuluukku

data class ResponseDataGps(
        val events: List<Event>
)

data class Event(
        val _embedded: Embedded,
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

data class Embedded(
        val venues: List<Venue>
)

data class Classification(
        val family: Boolean,
        val genre: Genre,
        val primary: Boolean,
        val segment: Segment,
        val subGenre: SubGenre
)

data class Dates(
        val start: Start
)

data class Image(
        val fallback: Boolean,
        val height: Int,
        val ratio: String,
        val url: String,
        val width: Int
)

data class PriceRange(
        val currency: String,
        val max: Double,
        val min: Double,
        val type: String
)

data class Sales(
        val `public`: Public
)

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

data class Links(
        val self: Self
)

data class Address(
        val line1: String
)

data class City(
        val name: String
)

data class Country(
        val countryCode: String,
        val name: String
)

data class Location(
        val latitude: String,
        val longitude: String
)

data class UpcomingEvents(
        val _total: Int
)

data class Self(
        val href: String
)

data class Genre(
        val id: String,
        val name: String
)

data class Segment(
        val id: String,
        val name: String
)

data class SubGenre(
        val id: String,
        val name: String
)

data class Start(
        val dateTime: String,
        val localDate: String,
        val localTime: String
)

data class Public(
        val endDateTime: String,
        val startDateTime: String,
        val startTBA: Boolean,
        val startTBD: Boolean
)
