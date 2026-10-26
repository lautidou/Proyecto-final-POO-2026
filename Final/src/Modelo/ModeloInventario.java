package Modelo;

import Modelo.objetos.Consumible;
import Modelo.objetos.Equipable;
import Modelo.objetos.Objeto;
import Modelo.pjs.Heroe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private Optional<Heroe> heroe() {
        return Optional.ofNullable(this.modeloMapa.getPersonaje());
    }

    public List<Objeto> getObjetos() {
        return heroe().map(h -> new ArrayList<>(h.getInventario())).orElseGet(ArrayList::new);
    }

    public boolean esEquipable(Objeto objeto) {
        return objeto instanceof Equipable;
    }

    public boolean estaEquipado(Objeto objeto) {
        return objeto instanceof Equipable
        && heroe().map(h -> h.estaEquipado((Equipable) objeto)).orElse(false);
    }

    public boolean usarObjeto(Objeto objeto) {
        Heroe heroe = heroe().orElse(null);
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