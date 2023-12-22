package entities

import kotlinx.serialization.Serializable

@Serializable
class TimeOfDay(private val h: Int, private val m: Int) {

    val hours = h % 24
    val minutes = m % 60

    fun add(min: Int): TimeOfDay {
        var newMin = minutes + min
        val newHour = hours + newMin / 60
        newMin %= 60
        return TimeOfDay(newHour, newMin)
    }

    override fun toString(): String {
        return "${hours.toString()}:${minutes.toString()}"
    }
}