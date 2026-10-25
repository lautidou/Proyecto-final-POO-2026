package Modelo.pjs;

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
    public boolean estaEquipadoPor(Personaje personaje) {
        return personaje.getArmaEquipada() == this;
    }

    @Override
    public void equiparEn(Personaje personaje) {
        personaje.equipar(this);
    }

    @Override
    public void desequiparDe(Personaje personaje) {
        if (estaEquipadoPor(personaje)) {
            personaje.desequiparArma();
        }
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
}