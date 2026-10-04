package Modelo.mapa;

public final class Portal {
    private final String idMapaDestino;
    private final Posicion posicionDestino;

    public Portal(String idMapaDestino, Posicion posicionDestino) {
        if (idMapaDestino == null || idMapaDestino.isEmpty()) {
            throw new IllegalArgumentException("El portal necesita un mapa destino.");
        }
        if (posicionDestino == null) {
            throw new IllegalArgumentException("El portal necesita una posición de destino.");
        }
        this.idMapaDestino = idMapaDestino;
        this.posicionDestino = posicionDestino;
    }

    public String getIdMapaDestino() { return idMapaDestino; }
    public Posicion getPosicionDestino() { return posicionDestino; }
}