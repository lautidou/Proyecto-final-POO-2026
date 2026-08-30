package pjs;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

public abstract class Personaje {
//Atributos
    private String nombre;
    private int vida;
    private int nivel;
    private Raza raza;
    private String clase;
    private List<Habilidad> habilidades;
    // Combate
    private Caracteristicas caracteristicas;
    // Pj en Escenario
    protected int x;
    protected int y;
    protected int velocidad = 8;

    public Personaje(String nombre, int vida, int nivel, Raza raza, String clase, List<Habilidad> habilidades,
                      Caracteristicas caracteristicas) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (vida <= 0) {
            throw new IllegalArgumentException("La vida debe ser mayor que cero.");
        }
        if (nivel <= 0 || nivel > 20) {
            throw new IllegalArgumentException("El nivel debe ser mayor que cero y menor o igual que 20.");
        }
        if (raza == null) {
            throw new IllegalArgumentException("La raza no puede ser nula.");
        }
        if (clase == null || clase.isEmpty()) {
            throw new IllegalArgumentException("La clase no puede ser nula o vacía.");
        }
        if (caracteristicas == null) {
            throw new IllegalArgumentException("Las características no pueden ser nulas.");
        }
        this.nombre = nombre;
        this.vida = vida;
        this.nivel = nivel;
        this.raza = raza;
        this.clase = clase;
        this.habilidades = habilidades;
        this.caracteristicas = caracteristicas;
    }
    //Metodos
    public void curar(int puntosCurados) {
        this.vida += puntosCurados;
    }

    public void recibirDanio(int puntosDanio) {
        this.vida -= puntosDanio;
        if (this.vida < 0) {
            this.vida = 0;
            //logica de muerte
        }
    }
    
    public void aprenderHabilidad(Habilidad habilidad) {
        if (!habilidades.contains(habilidad)) {
            habilidades.add(habilidad);
        } else {
            System.out.println("El personaje ya conoce esta habilidad.");
        }
    }

    public void subirNivel() {
        this.nivel++;
    }

    public String getNombre() {
        return nombre;
    }
    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // El personaje es responsable de dibujarse a sí mismo
    public void dibujar(Graphics g) {
        g.setColor(Color.RED); // Representaremos al personaje como un cuadrado rojo
        g.fillRect(x, y, 50, 50); 
        
        // Cuando uses imágenes reales, usarías:
        // g.drawImage(tuImagen, x, y, null);
    }

    // método para modificar las coordenadas
    public void mover(int dx, int dy) {
        this.x += dx * velocidad;
        this.y += dy * velocidad;
    }
}