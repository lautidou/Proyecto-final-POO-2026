package pjs;

public enum TipoEnemigo {
    Goblin(15, 7, new Caracteristicas(8, 14, 10, 10, 8, 8)),
    RamaMala(),
    Esqueleto(),
    Ogro(),
    MagoOscuro();

    private final int armadura;
    private final int danio;
    private final Caracteristicas caracteristicas;
    
    TipoEnemigo(int armadura, int danio , Caracteristicas caracteristicas){
        this.armadura = armadura;
        this.danio = danio;
        this.caracteristicas = caracteristicas;
    }

    public int getArmadura(){
        return this.armadura;
    }

    public int getDanio(){
        return this.danio;
    }

}
