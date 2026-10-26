package Modelo.objetos;

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
    public String getResumen() {
        return "Defensa +" + defensa;
    }

    public int getDefensa() {
        return defensa;
    }

    public RanuraEquipo getRanura() {
        return RanuraEquipo.ARMADURA;
    }
}