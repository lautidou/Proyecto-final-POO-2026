package Modelo;

import Modelo.pjs.Consumible;
import Modelo.pjs.Equipable;
import Modelo.pjs.Heroe;
import Modelo.pjs.Objeto;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModeloInventario {

    public static final String PROP_INVENTARIO = "inventario";

    private final ModeloMapa modeloMapa;
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

    public ModeloInventario(ModeloMapa modeloMapa) {
        if (modeloMapa == null) {
            throw new IllegalArgumentException("El modelo del mapa es obligatorio");
        }
        this.modeloMapa = modeloMapa;
    }

    public List<Objeto> getObjetos() {
        Heroe heroe = this.modeloMapa.getPersonaje();
        if (heroe == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(heroe.getInventario());
    }

    public boolean esEquipable(Objeto objeto) {
        return objeto instanceof Equipable;
    }

    public boolean estaEquipado(Objeto objeto) {
        Heroe heroe = this.modeloMapa.getPersonaje();
        return heroe != null && objeto instanceof Equipable
                && ((Equipable) objeto).estaEquipadoPor(heroe);
    }

    public boolean usarObjeto(Objeto objeto) {
        Heroe heroe = this.modeloMapa.getPersonaje();
        if (heroe == null || objeto == null || !heroe.getInventario().contains(objeto)) {
            return false;
        }
        objeto.usar(heroe);
        if (objeto instanceof Consumible && ((Consumible) objeto).getCantidad() == 0) {
            heroe.getInventario().remove(objeto);
        }
        this.soporte.firePropertyChange(PROP_INVENTARIO, null, this);
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.addPropertyChangeListener(oyente);
    }

    public void removePropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.removePropertyChangeListener(oyente);
    }
}