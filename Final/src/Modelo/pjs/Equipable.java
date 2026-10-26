package Modelo.pjs;

public interface Equipable {
    String getResumen();                         
    boolean estaEquipadoPor(Personaje personaje); 
    void equiparEn(Personaje personaje);         
    void desequiparDe(Personaje personaje);     
}