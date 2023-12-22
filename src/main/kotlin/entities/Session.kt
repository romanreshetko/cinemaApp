package entities

import kotlinx.serialization.Serializable

const val k = 9

@Serializable
class Session(val film: Film, val start: TimeOfDay) {
    val hall = mutableListOf<MutableList<Int>>()
    private var inited = false

    init {
        if (!inited) {
            inited = true
            for (i in 0..k) {
                hall.add(mutableListOf())
                for (j in 0..k) {
                    hall[i].add(0);
                }
            }
        }
    }

    val end: TimeOfDay get() = start.add(film.duration)

    fun showHall() {
        for (i in 0..k) {
            println(hall[i])
        }
    }
}