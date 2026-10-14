package Modelo.combate;

import Modelo.pjs.Dados;
import Modelo.pjs.Habilidad;

public final class CatalogoHabilidades {

    private CatalogoHabilidades() {
  }
    //BARBARO
    public static Habilidad golpePoderoso() {
        return new Habilidad(1, "Golpe poderoso", "Un golpe devastador: d10 + Fuerza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d10,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza()))), 15);
    }
    public static Habilidad tajoBrutal() {
        return new Habilidad(3, "Tajo brutal", "Un corte profundo: 2d6 + Fuerza.",
                contexto -> contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza()))), 10);
    }
    public static Habilidad furiaDesatada() {
        return new Habilidad(5, "Furia desatada", "Un ataque frenético: 3d4 + Fuerza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza())) +
                        Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza())) +
                        Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza()))), 5);
    }
    //MAGO
    public static Habilidad proyectilMagico() {
        return new Habilidad(1, "Proyectil mágico", "Un ataque a distancia: d4 + Inteligencia.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d4,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia()))), 10);
    }
    public static Habilidad bolaDeFuego() {
        return new Habilidad(2, "Bola de fuego", "Una explosión mágica: 2d8 + Inteligencia.",
                contexto -> contexto.infligirDanio(Dados.d8.rolleo() + Reglas.tirada(Dados.d8,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia()))), 5);
    }
    public static Habilidad rayoArcano() {
        return new Habilidad(3, "Rayo arcano", "Un rayo de energía: d12 + Inteligencia.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d12,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia()))), 2);
    }
    //MONJE
    public static Habilidad punoDePalma() {
        return new Habilidad(1, "Puño de palma", "Un golpe rápido: d6 + Destreza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d6,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza()))), 10);
    }
    public static Habilidad curacionMenor() {
        return new Habilidad(3, "Curación menor", "Recupera vida: d8 + Sabiduría.",
                contexto -> contexto.curarse(Reglas.tirada(Dados.d8,
                        Reglas.modificador(contexto.getActor().getCaracteristicas().getSabiduria()))), 5);
    }
    public static Habilidad rafagaDeGolpes() {
        return new Habilidad(5, "Ráfaga de golpes", "Una serie de ataques rápidos: 3d4 + Destreza.",
                contexto -> contexto.infligirDanio(Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza())) +
                        Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza())) +
                        Reglas.tirada(Dados.d4, Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza()))), 5);
    }
}