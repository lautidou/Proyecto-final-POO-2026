package Modelo.pjs;

public class Arma extends Objeto implements Equipable {
    private Dados dado;

    public Arma(int idObjeto, String nombre, double peso, int precio, String descripcion, Dados dado) {
        super(idObjeto, nombre, peso, precio, descripcion);
        if (dado == null) {
            throw new IllegalArgumentException("El arma necesita un dado de daño.");
        }
        this.dado = dado;
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
        if (personaje.getArmaEquipada() == this) {
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

    public int tiradaDanio(int car_necesaria){
        return car_necesaria + dado.rolleo();
    }
    
}