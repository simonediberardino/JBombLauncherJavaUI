package data

import java.io.Serializable

data class PropertiesDataObject(
    @JvmField var version: String = "",
) : Serializable {
}