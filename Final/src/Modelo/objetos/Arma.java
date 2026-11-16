package Modelo.objetos;

import Modelo.partida.ObjetoGuardado;
import Modelo.pjs.Dados;

public class Arma extends ObjetoEquipable {
    private final Dados dado;

    public Arma(int idObjeto, String nombre, double peso, int precio, String descripcion, Dados dado) {
        super(idObjeto, nombre, peso, precio, descripcion);
        if (dado == null) {
            throw new IllegalArgumentException("El arma necesita un dado de daño.");
        }
        this.dado = dado;
    }

    @Override
    public String getResumen() {
        return "Daño " + dado.name();
    }

    public Dados getDado() {
        return dado;
    }

    public int tiradaDanio(int car_necesaria) {
        return car_necesaria + dado.rolleo();
    }

    public RanuraEquipo getRanura() {
        return RanuraEquipo.ARMA;
    }

    @Override
    public ObjetoGuardado aGuardado(boolean equipado) {
        return new ObjetoGuardado("ARMA", getIdObjeto(), getNombre(), getPeso(), getPrecio(),
            getDescripcion(), 1, equipado, this.dado.name(), 0, 0);
    }
}