package fi.tuni.lippuluukku.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class Price(
        val type : String? = null,
        val currency : String? = null,
        val min : Double? = null,
        val max : Double? = null
)
