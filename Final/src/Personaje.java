import java.util.List;

public class Personaje {
//Atributos
    private String nombre;
    private int vida;
    private int nivel;
    private int experiencia;
    private String raza;
    private String clase;
    private List<Objeto> inventario;
    // Combate
    private int fuerza;
    private int sabiduria;
    private int destreza;
    private int inteligencia;
    private int constitucion;
    private int carisma;
//Metodos
    public String getNombre() {
        return nombre;
    }
}