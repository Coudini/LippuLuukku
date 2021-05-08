package fi.tuni.lippuluukku
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventInfo(var info : String? = null){

}
