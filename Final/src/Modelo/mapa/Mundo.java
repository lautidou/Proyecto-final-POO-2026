package Modelo.mapa;

import java.util.LinkedHashMap;
import java.util.Map;

public class Mundo {
    private final Map<String, Mapa> mapas = new LinkedHashMap<>();
    private String idMapaInicial;
    private Posicion posicionInicial;
    private Mapa mapaActual;

    public void agregarMapa(Mapa mapa) {
        if (mapa == null) {
            throw new IllegalArgumentException("El mapa no puede ser nulo.");
        }
        if (mapas.containsKey(mapa.getId())) {
            throw new IllegalArgumentException("Ya existe un mapa con id '" + mapa.getId() + "'.");
        }
        mapas.put(mapa.getId(), mapa);
    }

    public void definirInicio(String idMapa, Posicion posicion) {
        Mapa mapa = mapas.get(idMapa);
        if (mapa == null) {
            throw new IllegalArgumentException("El mapa inicial '" + idMapa + "' no existe.");
        }
        if (!mapa.esTransitable(posicion)) {
            throw new IllegalArgumentException("La posición inicial " + posicion + " no es transitable.");
        }
        this.idMapaInicial = idMapa;
        this.posicionInicial = posicion;
    }

    public void validar() {
        if (idMapaInicial == null) {
            throw new IllegalStateException("Falta definir el inicio del mundo.");
        }
        for (Mapa mapa : mapas.values()) {
            for (Map.Entry<Posicion, Portal> entrada : mapa.getPortales().entrySet()) {
                Portal portal = entrada.getValue();
                Mapa destino = mapas.get(portal.getIdMapaDestino());
                if (destino == null) {
                    throw new IllegalStateException("El portal de '" + mapa.getId() + "' en "
                            + entrada.getKey() + " apunta a un mapa inexistente: '"
                            + portal.getIdMapaDestino() + "'.");
                }
                if (!destino.esTransitable(portal.getPosicionDestino())) {
                    throw new IllegalStateException("El portal de '" + mapa.getId() + "' en "
                            + entrada.getKey() + " llega a una posición no transitable: "
                            + portal.getPosicionDestino() + " en '" + destino.getId() + "'.");
                }
            }
        }
    }

    public void reiniciar() {
        this.mapaActual = mapas.get(idMapaInicial);
    }

    public void irA(String idMapa) {
        Mapa mapa = mapas.get(idMapa);
        if (mapa == null) {
            throw new IllegalArgumentException("El mapa '" + idMapa + "' no existe.");
        }
        this.mapaActual = mapa;
    }

    public Mapa getMapaActual() { return mapaActual; }
    public Posicion getPosicionInicial() { return posicionInicial; }
}