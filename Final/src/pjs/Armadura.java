package pjs;

public class Armadura extends Objeto{
    private int defensa;
    private boolean equipada;

    //constructor
     public Armadura(int idObjeto, String nombre, double peso, int precio, String descripcion, int defensa){
        super(idObjeto, nombre, peso, precio, descripcion);
        if (defensa < 0) {
            throw new IllegalArgumentException("La defensa no puede negativo o debajo de 0");
        }

        this.defensa = defensa;
        this.equipada = false;
    }

    @Override
    //metodo abstracto
    public void usar(Personaje objetivo) {
        if (!equipada) {
            equipada = true;
            System.out.println("Has equipado el escudo: " + super.getNombre());
        } else {
            equipada = false;
            System.out.println("Has desequipado el escudo: " + super.getNombre());
        }
    }

    public int getDefensa(){
        return defensa;
    }

    public boolean isEquipada(){
        return equipada;
    }
}
