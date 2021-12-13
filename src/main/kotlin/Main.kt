import java.lang.IllegalArgumentException

open class ArmaDeFuego(
    nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String
) {
    val nombre = nombre
    protected var municion = municion
        set(value) = if (value >= 0) field =
            value else throw IllegalArgumentException("El valor de la munición no puede ser menor a 0.")
    protected var municionARestar = municionARestar
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

    //Devuelve en un pair las balas gastadas en el primer par y en el segundo la munición tras disparar.
    open fun dispara(): Pair<Int, Int> {
        val municionARestar = municionARestar
        val balasGastadas = if (municion >= municionARestar) municionARestar else municion
        municion -= balasGastadas
        return Pair(balasGastadas, municion)
    }

    fun recarga(municion: Int): Int {
        this.municion += municion
        return this.municion
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

fun main() {
    val revolver = Pistola("Revolver", 16, 1, "Bala", 6, "Pequeño")
    val ak = Rifle("AK-47", 60, 3, "Bala", 4, "Amplio")
    val rpg = Bazooka("RPG", 9, 1, "Cohete", 16, "Amplio")

    val mapArmas = mutableMapOf<String, ArmaDeFuego>()

    for (i in 1..6) {
        listOf(revolver, ak, rpg).random().let {
            var nombre = it.nombre
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
        if (key.size == 1) println("Se ha disparado el arma ${key[0]} por primera vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas en el cargador.")
        else println("Se ha disparado el arma ${key[0]} por ${key[1]}a vez, se han gastado ${disparo.first} balas y quedan ${disparo.second} balas en el cargador.")
    }

}