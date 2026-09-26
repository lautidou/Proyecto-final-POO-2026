package Modelo;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Dados;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Modelo.pjs.Objeto;
import Modelo.pjs.Raza;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de negocio de la pantalla de creación de personaje.
 * Valida los datos ingresados y arma un Heroe nuevo.
 */
public class ModeloCreacionPersonaje {

    private Heroe heroeCreado;

    public ModeloCreacionPersonaje() {
    }

    public Heroe crearHeroe(String nombre, Raza raza, Clases clase) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un nombre para el personaje.");
        }
        if (raza == null) {
            throw new IllegalArgumentException("Debe seleccionar una raza.");
        }
        if (clase == null) {
            throw new IllegalArgumentException("Debe seleccionar una clase.");
        }

        Caracteristicas caracteristicasBase = generarCaracteristicasBase(raza);
        List<Habilidad> habilidades = new ArrayList<>();
        List<Objeto> inventario = new ArrayList<>();

        this.heroeCreado = new Heroe(nombre.trim(), 1, raza, clase, habilidades,
                caracteristicasBase, 0, inventario);
        return this.heroeCreado;
    }

    /**
     * Genera características base tirando 3d6 por estadística (estilo clásico
     * de rol) y sumando los bonos raciales definidos en Raza.
     */
    private Caracteristicas generarCaracteristicasBase(Raza raza) {
        int fuerza = tirarCaracteristica() + raza.getFuerza();
        int sabiduria = tirarCaracteristica() + raza.getSabiduria();
        int destreza = tirarCaracteristica() + raza.getDestreza();
        int inteligencia = tirarCaracteristica() + raza.getInteligencia();
        int constitucion = tirarCaracteristica() + raza.getConstitucion();
        int carisma = tirarCaracteristica() + raza.getCarisma();

        return new Caracteristicas(
                limitar(fuerza), limitar(sabiduria), limitar(destreza),
                limitar(inteligencia), limitar(constitucion), limitar(carisma)
        );
    }

    private int tirarCaracteristica() {
        return Dados.d6.rolleo() + Dados.d6.rolleo() + Dados.d6.rolleo();
    }

    private int limitar(int valor) {
        return Math.min(20, Math.max(0, valor));
    }

    public Heroe getHeroeCreado() {
        return this.heroeCreado;
    }
}