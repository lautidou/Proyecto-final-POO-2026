package Modelo.pjs;

public class Armadura extends ObjetoEquipable {
    private final int defensa;

    public Armadura(int idObjeto, String nombre, double peso, int precio, String descripcion, int defensa) {
        super(idObjeto, nombre, peso, precio, descripcion);
        if (defensa < 0) {
            throw new IllegalArgumentException("La defensa no puede ser negativa.");
        }
        this.defensa = defensa;
    }

    @Override
    public boolean estaEquipadoPor(Personaje personaje) {
        return personaje.getArmaduraEquipada() == this;
    }

    @Override
    public void equiparEn(Personaje personaje) {
        personaje.equipar(this);
    }

    @Override
    public void desequiparDe(Personaje personaje) {
        if (estaEquipadoPor(personaje)) {
            personaje.desequiparArmadura();
        }
    }

    @Override
    public String getResumen() {
        return "Defensa +" + defensa;
    }

    public int getDefensa() {
        return defensa;
    }
}