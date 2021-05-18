package fi.tuni.lippuluukku.listModel

data class UserList(
        var locations: MutableList<Location>? = null,
        var keywords: MutableList<Keyword>? = null
)
