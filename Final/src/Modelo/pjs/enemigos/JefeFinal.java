package Modelo.pjs.enemigos;

import java.util.ArrayList;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;

public class JefeFinal extends Enemigo {

    public JefeFinal() {
        super("Sombra del Vacío", 10, Clases.Mago, new ArrayList<>(),
        new Caracteristicas(20, 20, 15, 20, 20, 15), 1000, new ArrayList<>());
        
        setRutaSprite(Recursos.Rutas.BOSS);
    }
}
