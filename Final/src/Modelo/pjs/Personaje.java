package Modelo.pjs;

import java.util.ArrayList;
import java.util.List;
import Modelo.mapa.Posicion;

public abstract class Personaje {
    // Atributos
    private String nombre;
    private int vida;
    private int vidaMaxima;
    private int nivel;
    private Clases clase;
    private List<Habilidad> habilidades;
    private Caracteristicas caracteristicas;
    private Posicion posicion;
    private Arma armaEquipada;
    private Armadura armaduraEquipada;

    public Personaje(String nombre, int nivel, Clases clase, List<Habilidad> habilidades,
                      Caracteristicas caracteristicas) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (nivel <= 0 || nivel > 20) {
            throw new IllegalArgumentException("El nivel debe ser mayor que cero y menor o igual que 20.");
        }
        if (clase == null) {
            throw new IllegalArgumentException("La clase no puede ser nula.");
        }
        if (caracteristicas == null) {
            throw new IllegalArgumentException("Las características no pueden ser nulas.");
        }
        this.nombre = nombre;
        this.vidaMaxima = clase.getMaxPG() + caracteristicas.getConstitucion();
        setVida(this.vidaMaxima);
        this.nivel = nivel;
        this.clase = clase;
        this.caracteristicas = caracteristicas;
        this.habilidades = (habilidades != null) ? habilidades : new ArrayList<>();
        actualizarHabilidadesDesbloqueadas();
    }

    public void curar(int puntosCurados) {
        this.vida += puntosCurados;
    }

    public void recibirDanio(int puntosDanio) {
        this.vida -= puntosDanio;
        if (this.vida < 0) {
            this.vida = 0;
            // Lógica de muerte
        }
    }
    
    public void aprenderHabilidad(Habilidad habilidad) {
        if (habilidad == null) return;
        boolean yaExiste = habilidades.stream()
                .anyMatch(h -> h.getIdHabilidad() == habilidad.getIdHabilidad());
        if (!yaExiste) {
            habilidades.add(habilidad);
            System.out.println(nombre + " ha aprendido la habilidad: " + habilidad.getNombre());
        }
    }

    public void subirNivel() {
        if (this.nivel >= 20) {
            System.out.println(this.nombre + " ya ha alcanzado el nivel máximo.");
            return;
        }
        this.nivel++;
        System.out.println(this.nombre + " ha subido al Nivel " + this.nivel + "!");
        actualizarHabilidadesDesbloqueadas();
    }

    public void actualizarHabilidadesDesbloqueadas() {
        List<Habilidad> disponibles = clase.getHabilidadesHastaNivel(this.nivel);
        for (Habilidad habilidad : disponibles) {
            aprenderHabilidad(habilidad);
        }
    }

    // Equipo
    public Arma equipar(Arma arma) {
        if (arma == null) {
            throw new IllegalArgumentException("El arma no puede ser nula.");
        }
        if (arma == this.armaEquipada) {
            return null;
        }
        Arma anterior = this.armaEquipada;
        this.armaEquipada = arma;
        return anterior;
    }

    public Armadura equipar(Armadura armadura) {
        if (armadura == null) {
            throw new IllegalArgumentException("La armadura no puede ser nula.");
        }
        if (armadura == this.armaduraEquipada) {
            return null;
        }
        Armadura anterior = this.armaduraEquipada;
        this.armaduraEquipada = armadura;
        return anterior;
    }

    public Arma desequiparArma() {
        Arma arma = this.armaEquipada;
        this.armaEquipada = null;
        return arma;
    }

    public Armadura desequiparArmadura() {
        Armadura armadura = this.armaduraEquipada;
        this.armaduraEquipada = null;
        return armadura;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Armadura getArmaduraEquipada() {
        return armaduraEquipada;
    }

    public int getDefensa() {
        return (armaduraEquipada == null) ? 0 : armaduraEquipada.getDefensa();
    }

    // Getters y Setters
    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setVida(int Valor){
        this.vida = Valor;
    }

    public int getNivel() {
        return nivel;
    }

    public Clases getClase() {
        return clase;
    }

    public int getProficiencia(){
        return((this.nivel - 1) / 4) + 2;
    } 

    public Posicion getPosicion(){
        return this.posicion;
    }

    public void setPosicion(Posicion posicion){
        this.posicion = posicion;
    }

    public Caracteristicas getCaracteristicas() {
        return caracteristicas;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
}