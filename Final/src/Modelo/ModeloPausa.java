package Modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModeloPausa {

    public static final String PROP_ESTADO_PAUSA = "estado_pausa";
    private boolean juegoPausado;
    private final PropertyChangeSupport soporte;

    public ModeloPausa(){
        this.juegoPausado = false;
        this.soporte = new PropertyChangeSupport(this);
    }

    public boolean isJuegoPausado(){
        return juegoPausado;
    }

    public void alternarPausa(){
        setJuegoPausado(!this.juegoPausado);
    }

    public void setJuegoPausado(boolean pausa){
        if (!this.juegoPausado != pausa) {
        this.juegoPausado =  pausa;
        //notificamos a los contoladores que el estado de pausa cambio
        notificarCambio(); 
        }
    }
    
     private void notificarCambio() {
        this.soporte.firePropertyChange(PROP_ESTADO_PAUSA, null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.addPropertyChangeListener(oyente);
    }

    public void removePropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.removePropertyChangeListener(oyente);
    } 
    

    
}
