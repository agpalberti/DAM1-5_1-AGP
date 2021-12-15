package ej5_2

import java.lang.IllegalArgumentException

interface Dispara {
    var municion: Int
    var municionARestar: Int
    fun dispara(): Pair<Int, Int> {
        val balasGastadas = if (municion >= municionARestar) municionARestar else municion
        municion -= balasGastadas
        return Pair(balasGastadas, municion)
    }
}

open class ArmaDeFuego(
    nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String
) : Dispara {
    val nombre = nombre
    override var municion = municion
        set(value) = if (value >= 0) field =
            value else throw IllegalArgumentException("El valor de la munición no puede ser menor a 0.")
    override var municionARestar = municionARestar
        set(value) = if (value >= 0) field =
            value else throw IllegalArgumentException("El valor de la munición a restar no puede ser menor a 0.")
    private val tipoDeMunicion = tipoDeMunicion
    private val danio = danio
    private val radio = radio

    private companion object {
        val radioPermitido = listOf("Pequeño", "Amplio")
    }

    init {
        require(tipoDeMunicion.isNotEmpty()) { "El tipo de munición no debe estar vacío." }
        require(nombre.isNotEmpty()) { "El nombre no debe estar vacío." }
        require(radio in radioPermitido) { "El radio debe estar en las siguientes opciones: $radioPermitido." }
        require(danio >= 0) { "El daño no puede ser menor a 0" }
    }

    fun recarga(municion: Int): Int {
        this.municion += municion
        return this.municion
    }

    override fun toString(): String {
        return nombre
    }
}

class Pistola(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    override fun dispara(): Pair<Int, Int> {
        val municionARestar = municionARestar * 1
        val balasGastadas = if (municion >= municionARestar) municionARestar else municion
        municion -= balasGastadas
        return Pair(balasGastadas, municion)
    }
}

class Rifle(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    override fun dispara(): Pair<Int, Int> {
        val municionARestar = municionARestar * 2
        val balasGastadas = if (municion >= municionARestar) municionARestar else municion
        municion -= balasGastadas
        return Pair(balasGastadas, municion)
    }
}

class Bazooka(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    override fun dispara(): Pair<Int, Int> {
        val municionARestar = municionARestar * 3
        val balasGastadas = if (municion >= municionARestar) municionARestar else municion
        municion -= balasGastadas
        return Pair(balasGastadas, municion)
    }
}

class Casa(
    val direccion: String, private val dueño: String, private val precio: Double, override var municion: Int,
    override var municionARestar: Int
) : Dispara{
    override fun toString(): String {
        return direccion
    }
}

class Coche(val matricula: String, var gasolina: Int, override var municion: Int, override var municionARestar: Int) :
    Dispara{
    override fun toString(): String {
        return matricula
    }
    }

class Bocadillo(
    val contenido: String,
    val precio: Double,
    override var municion: Int,
    override var municionARestar: Int
) : Dispara{
    override fun toString(): String {
        return contenido
    }
}

fun main() {
    val revolver = Pistola("Revolver", 16, 1, "Bala", 6, "Pequeño")
    val ak = Rifle("AK-47", 60, 3, "Bala", 4, "Amplio")
    val rpg = Bazooka("RPG", 9, 1, "Cohete", 16, "Amplio")
    val bocadillo = Bocadillo("chorizo",2.5,40,2)
    val casa = Casa("Avenida los Gallos 2","Alex",10000.0,50,5)
    val coche = Coche("AIO1841",50,80,8)

    val mapArmas = mutableMapOf<String, Dispara>()

    for (i in 1..6) {
        listOf(revolver, ak, rpg, bocadillo, casa, coche).random().let {
            var nombre = it.toString()
            if (mapArmas.any { armaMapa -> armaMapa.key == nombre }) {
                var counter = 2
                while (mapArmas.any { armaMapa -> armaMapa.key == nombre }) {
                    if (mapArmas.none { armaMapa -> armaMapa.key == nombre + "_${counter}" }) {
                        nombre += "_$counter"
                    } else counter++
                }
            }
            mapArmas[nombre] = it
        }

    }
    mapArmas.forEach {
        val disparo = it.value.dispara()
        val key = it.key.split("_")
        if (key.size == 1) when(it.value){
            is ArmaDeFuego -> println("Se ha disparado el arma ${key[0]} por primera vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas en el cargador.")
            is Bocadillo -> println("Se ha disparado el bocadillo de ${key[0]} por primera vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
            is Casa -> println("Se ha disparado la casa con dirección ${key[0]} por primera vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
            is Coche -> println("Se ha disparado el coche con matrícula ${key[0]} por primera vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
        }
        else when(it.value){
            is ArmaDeFuego -> println("Se ha disparado el arma ${key[0]} por ${key[1]}a vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas en el cargador.")
            is Bocadillo -> println("Se ha disparado el bocadillo de ${key[0]} por ${key[1]}a vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
            is Casa -> println("Se ha disparado la casa con dirección ${key[0]} por ${key[1]}a vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
            is Coche -> println("Se ha disparado el coche con matrícula ${key[0]} por ${key[1]}a vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas.")
        }
    }

}