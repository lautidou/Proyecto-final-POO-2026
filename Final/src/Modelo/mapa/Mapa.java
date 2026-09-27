package Modelo.mapa;

public class Mapa {
    private Celda[][]cuadricula;
    private int fila;
    private int columna;

    public Mapa(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
        this.cuadricula = new Celda[fila][columna];
        inicializarMapa();
    }

    private void inicializarMapa(){
        for(int y=0; y < fila; y++){
            for(int x=0; y < columna; x++){
                this.cuadricula[y][x] = new Celda(true, 0);
            }
        }
    }
    //logica de movimiento
    public boolean CoordenadasValida(int x, int y){
        if (x < 0 || x >= columna || y < 0 || y >= fila) {
            return false;
        }
        //! es para negar
        if (!this.cuadricula[y][x].esPasable()) {
            return false;
        }

        return true;
    }
}
