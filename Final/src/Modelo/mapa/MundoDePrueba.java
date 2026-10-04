package Modelo.mapa;

public final class MundoDePrueba {

    private MundoDePrueba() {
    }

    public static Mundo crear() {
        Mapa pueblo = new Mapa("Pueblo", 15, 25, Terreno.PASTO);
        for (int x = 5; x < 12; x++) {
            pueblo.setTerreno(new Posicion(x, 6), Terreno.PARED);
        }
        for (int y = 2; y < 6; y++) {
            pueblo.setTerreno(new Posicion(18, y), Terreno.AGUA);
        }

        Mapa bosque = new Mapa("Bosque", 12, 20, Terreno.PASTO);
        for (int y = 2; y < 9; y++) {
            bosque.setTerreno(new Posicion(9, y), Terreno.PARED);
        }
        for (int x = 4; x < 7; x++) {
            bosque.setTerreno(new Posicion(x, 9), Terreno.AGUA);
            bosque.setTerreno(new Posicion(x, 10), Terreno.AGUA);
        }

        Mapa cueva = new Mapa("Cueva", 8, 10, Terreno.PIEDRA);
        rodearConPared(cueva);
        for (int y = 2; y < 5; y++) {
            cueva.setTerreno(new Posicion(5, y), Terreno.PARED);
        }

        // Cada puerta lleva a la celda contigua a la puerta de regreso
        puerta(pueblo, new Posicion(24, 7), "Bosque", new Posicion(1, 6));
        puerta(bosque, new Posicion(0, 6), "Pueblo", new Posicion(23, 7));
        puerta(bosque, new Posicion(19, 6), "Cueva", new Posicion(1, 4));
        puerta(cueva, new Posicion(0, 4), "Bosque", new Posicion(18, 6));

        Mundo mundo = new Mundo();
        mundo.agregarMapa(pueblo);
        mundo.agregarMapa(bosque);
        mundo.agregarMapa(cueva);
        mundo.definirInicio("Pueblo", new Posicion(1, 1));
        return mundo;
    }

    private static void puerta(Mapa origen, Posicion donde, String idDestino, Posicion llegada) {
        origen.setTerreno(donde, Terreno.PUERTA);
        origen.agregarPortal(donde, new Portal(idDestino, llegada));
    }

    private static void rodearConPared(Mapa mapa) {
        for (int x = 0; x < mapa.getColumnas(); x++) {
            mapa.setTerreno(new Posicion(x, 0), Terreno.PARED);
            mapa.setTerreno(new Posicion(x, mapa.getFilas() - 1), Terreno.PARED);
        }
        for (int y = 0; y < mapa.getFilas(); y++) {
            mapa.setTerreno(new Posicion(0, y), Terreno.PARED);
            mapa.setTerreno(new Posicion(mapa.getColumnas() - 1, y), Terreno.PARED);
        }
    }
}