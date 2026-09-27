package Modelo.pjs;

public enum Clases {

    Barbaro(Dados.d12),
    Bardo(Dados.d8),
    Brujo(Dados.d8),
    Clerigo(Dados.d8),
    Druida(Dados.d8),
    Explorador(Dados.d10),
    Guerrero(Dados.d10),
    Hechicero(Dados.d6),
    Mago(Dados.d6),
    Monje(Dados.d8),
    Paladin(Dados.d10),
    Picaro(Dados.d8);

    private final Dados puntosGolpe;

    Clases(Dados puntosGolpe){
        this.puntosGolpe = puntosGolpe;
    }

    public int getMaxPG(){
        return puntosGolpe.getMax();
    }
}
