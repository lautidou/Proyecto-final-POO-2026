package Modelo.combate;

import Modelo.pjs.Dados;
import Modelo.pjs.Habilidad;

public final class CatalogoHabilidades {

    private CatalogoHabilidades() {
    }
    // ---- Bárbaro ----
    public static Habilidad golpePoderoso() {
        return new Habilidad(1, "Golpe poderoso", "Un golpe devastador: d10 + Fuerza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d10, fuerza(contexto))), 3);
    }

    public static Habilidad tajoBrutal() {
        return new Habilidad(4, "Tajo brutal", "Un tajo con todo el peso del arma: 2d6 + Fuerza.",
                contexto -> contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, fuerza(contexto))), 2);
    }

    public static Habilidad furiaDesatada() {
        return new Habilidad(5, "Furia desatada", "Una furia incontrolable: 2d10 + Fuerza.",
                contexto -> contexto.infligirDanio(Dados.d10.rolleo() + Reglas.tirada(Dados.d10, fuerza(contexto))), 1);
    }
    // ---- Mago ----
    public static Habilidad proyectilMagico() {
        return new Habilidad(6, "Proyectil mágico", "Un dardo de energía: d6 + Inteligencia.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d6, inteligencia(contexto))), 5);
    }

    public static Habilidad bolaDeFuego() {
        return new Habilidad(2, "Bola de fuego", "Una explosión mágica: 2d8 + Inteligencia.",
                contexto -> contexto.infligirDanio(Dados.d8.rolleo() + Reglas.tirada(Dados.d8, inteligencia(contexto))), 2);
    }

    public static Habilidad rayoArcano() {
        return new Habilidad(7, "Rayo arcano", "Un rayo devastador: 2d10 + Inteligencia.",
                contexto -> contexto.infligirDanio(Dados.d10.rolleo() + Reglas.tirada(Dados.d10, inteligencia(contexto))), 1);
    }
    // ---- Monje ----
    public static Habilidad punoDePalma() {
        return new Habilidad(8, "Puño de palma", "Un golpe preciso: d8 + Destreza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d8, destreza(contexto))), 5);
    }

    public static Habilidad curacionMenor() {
        return new Habilidad(3, "Curación menor", "Recupera vida: d8 + Sabiduría.",
                contexto -> contexto.curarse(Reglas.tirada(Dados.d8, sabiduria(contexto))), 2);
    }

    public static Habilidad rafagaDeGolpes() {
        return new Habilidad(9, "Ráfaga de golpes", "Una lluvia de golpes: 2d6 + Destreza.",
                contexto -> contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, destreza(contexto))), 1);
    }

    // ---- Modificadores de quien actúa ----
    private static int fuerza(ContextoCombate contexto) {
        return Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
    }

    private static int destreza(ContextoCombate contexto) {
        return Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza());
    }

    private static int inteligencia(ContextoCombate contexto) {
        return Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
    }

    private static int sabiduria(ContextoCombate contexto) {
        return Reglas.modificador(contexto.getActor().getCaracteristicas().getSabiduria());
    }
}