import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.IllegalArgumentException

open class ArmaDeFuego(
    nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String
) {
    val nombre = nombre
    private var municion = municion
        set(value) = if (value >= 0) field =
            value else throw IllegalArgumentException("El valor de la munición no puede ser menor a 0.")
    private var municionARestar = municionARestar
        set(value) = if (value >= 0) field =
            value else throw IllegalArgumentException("El valor de la munición a restar no puede ser menor a 0.")
    private val tipoDeMunicion = tipoDeMunicion
    private val danio = danio
    private val radio = radio

    companion object {
        val radioPermitido = listOf<String>("Pequeño", "Amplio")
    }

    init {
        require(tipoDeMunicion.isNotEmpty()) { "El tipo de munición no debe estar vacío." }
        require(nombre.isNotEmpty()) { "El nombre no debe estar vacío." }
        require(radio in radioPermitido) { "El radio debe estar en las siguientes opciones: $radioPermitido." }
        require(danio >= 0) { "El daño no puede ser menor a 0" }
    }

    fun dispara() {
        municion -= municionARestar
        if (municion < 0) municion = 0
    }

    fun recarga(municion: Int) {
        this.municion += municion
    }
}

class Pistola(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    private var municionARestar = municionARestar * 1
}

class Rifle(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    private var municionARestar = municionARestar * 2
}

class Bazooka(nombre: String, municion: Int, municionARestar: Int, tipoDeMunicion: String, danio: Int, radio: String) :
    ArmaDeFuego(nombre, municion, municionARestar, tipoDeMunicion, danio, radio) {
    private var municionARestar = municionARestar * 3
}

fun main() {
    var asdf = Pistola("Revolver", 16, 1, "Bala", 6, "Pequeño")

}