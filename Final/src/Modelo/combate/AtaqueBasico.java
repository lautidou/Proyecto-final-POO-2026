package Modelo.combate;

public class AtaqueBasico implements EfectoCombate {

    @Override
    public void aplicar(ContextoCombate contexto) {
        contexto.infligirDanio(Reglas.danioBasico(contexto.getActor()));
    }
}