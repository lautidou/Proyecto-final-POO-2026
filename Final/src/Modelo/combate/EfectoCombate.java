package Modelo.combate;

@FunctionalInterface
public interface EfectoCombate {

    void aplicar(ContextoCombate contexto);
}