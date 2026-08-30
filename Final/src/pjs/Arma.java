package pjs;

public class Arma extends Objeto {
    private Dados dado ;
    private boolean equipada;

    public Arma(int idObjeto, String nombre, double peso, int precio, String descripcion, Dados dado) {
        super(idObjeto, nombre, peso, precio, descripcion);
        this.dado = dado;
        this.equipada = false;
    }

    @Override
    public void usar(Personaje objetivo) {
        if (!equipada) {
            equipada = true;
            System.out.println("Has equipado el arma: " + super.getNombre());
            // Lógica para actualizar las estadísticas del jugador
        } else {
            equipada = false;
            System.out.println("Has desequipado: " + super.getNombre());
        }
    }

    
}
