package Modelo.mapa;

import Modelo.pjs.Personaje;

public class Movimiento {
   private Mapa mapita;
   
    public Movimiento(Mapa mapita){
    this.mapita = mapita;
    }

    public void moverConTeclado(Personaje pj, char tecla){
        if (pj.getPosicion() == null) {
            System.out.println("El personaje no tiene una posición asignada en el mapa.");
        }

        int x2 = pj.getPosicion().getX();
        int y2 = pj.getPosicion().getY();

        switch (Character.toLowerCase(tecla)) {
            case 'w': y2 -= 1; break;
            case 's': y2 += 1; break;
            case 'a': x2 -= 1; break;
            case 'd': x2 += 1; break;
            default:
                return;
        }
        moverPj(pj, x2, y2);
    }

    private void moverPj(Personaje pj, int x2, int y2){
        if (mapita.CoordenadasValida(x2,y2)) {
            pj.getPosicion().setCoordenadas(x2,y2);
          System.out.println(pj.getNombre() + " se movió a " + x2 + "," + y2 + "]");
        } else {
            System.out.println("Choque: El paso hacia " + x2 + "," + y2 + " está bloqueado.");
        }  
    }

}
