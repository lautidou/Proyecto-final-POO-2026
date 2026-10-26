package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloMapa;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;
import Modelo.combate.SalidaCombate;
import Vista.Ventana;
import Vista.VistaMapa;
import Vista.VistaMuerte;

public class ControladorFinCombate implements SalidaCombate {

    private final ModeloMapa modeloMapa;

    public ControladorFinCombate(ModeloMapa modeloMapa) {
        if (modeloMapa == null) {
            throw new IllegalArgumentException("El modelo del mapa es obligatorio");
        }
        this.modeloMapa = modeloMapa;
    }

    @Override
    public void alGanar(Heroe heroe, Enemigo enemigo) {
        this.modeloMapa.eliminarEnemigo(enemigo);
        // VistaMapa retoma la música del mapa al mostrarse (ControladorMapa ya lo hace)
        Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);
    }

    @Override
    public void alPerder(Heroe heroe, Enemigo enemigo) {
        ReproductorAudio.detenerMusica();
        Ventana.getInstancia().mostrarPanel(VistaMuerte.NOMBRE);
    }
}