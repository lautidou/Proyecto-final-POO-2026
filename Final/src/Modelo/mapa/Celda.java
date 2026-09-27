package Modelo.mapa;

public class Celda {
    private boolean pasable;
    private int tipoterreno;

    public Celda(boolean pasable, int tipoterreno){
        this.tipoterreno = tipoterreno;
        this.pasable = pasable;
    }

    public boolean esPasable(){
        return pasable;
    }

    public int getTipoterreno(){
        return tipoterreno;
    }
}
