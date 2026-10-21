package Modelo.pjs;

// Un objeto que se puede llevar puesto. Quién lo lleva lo guarda el Personaje (en su ranura), no el objeto.
public interface Equipable {
    String getNombre();
    String getDescripcion();
    String getResumen();                      // lo que aporta, en una línea: "Daño d8", "Defensa +2"
    void equiparEn(Personaje personaje);      // el personaje se lo pone
    void desequiparDe(Personaje personaje);   // el personaje se lo saca (si es el que lleva)
}