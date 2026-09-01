package pjs;

public enum Clases {

    /*barbaro(),
    bardo(),
    brujo(),
    clerigo(),
    druida(),
    explorador(),
    guerrero(),
    hechicero(),
    mago(),
    monje(),
    paladin(),*/
    picaro(Dados.d8, 1);

    private final Dados puntosGolpe;
    private final int proficiencia;

    Clases(Dados puntosGolpe, int proficiencia){
        this.puntosGolpe = puntosGolpe;
        this.proficiencia = proficiencia;
    }

    public int getMaxPG(){
        return puntosGolpe.getMax();
    }
}
