package fi.tuni.lippuluukku.listModel

data class UserList(
        val locations: MutableList<Location>? = null,
        val keywords: MutableList<Keyword>? = null
)
