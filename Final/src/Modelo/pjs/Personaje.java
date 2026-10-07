package Modelo.pjs;
import java.util.List;

import Modelo.mapa.Posicion;

public abstract class Personaje {
//Atributos
    private String nombre;
    private int vida;
    private int vidaMaxima;
    private int nivel;
    private Clases clase;
    private List<Habilidad> habilidades;
    // Combate
    private Caracteristicas caracteristicas;
    //posicion de pj
    private Posicion posicion;

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

    public int getProficiencia(){
        return((this.nivel - 1) / 4) + 2;
    } 

    public Posicion getPosicion(){
        return this.posicion;
    }

    public void setPosicion(Posicion posicion){
        this.posicion = posicion;
    }
}