package Modelo.pjs;

public class Armadura extends Objeto implements Equipable {
    private int defensa;

    //constructor
     public Armadura(int idObjeto, String nombre, double peso, int precio, String descripcion, int defensa){
        super(idObjeto, nombre, peso, precio, descripcion);
        if (defensa < 0) {
            throw new IllegalArgumentException("La defensa no puede negativo o debajo de 0");
        }

        this.defensa = defensa;
    }

    @Override
    public void usar(Personaje objetivo) {
        equiparEn(objetivo);
    }

    @Override
    public void equiparEn(Personaje personaje) {
        personaje.equipar(this);
    }

    @Override
    public void desequiparDe(Personaje personaje) {
        if (personaje.getArmaduraEquipada() == this) {
            personaje.desequiparArmadura();
        }
    }

    @Override
    public String getResumen() {
        return "Defensa +" + defensa;
    }

    public int getDefensa(){
        return defensa;
    }

}