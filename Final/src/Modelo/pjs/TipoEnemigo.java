package Modelo.pjs;

public enum TipoEnemigo {
    Goblin(15, new Caracteristicas(8, 14, 10, 10, 8, 8)),
    RamaMala(13, new Caracteristicas(6, 8, 13, 4, 12, 3)),
    Esqueleto(13, new Caracteristicas(10, 8, 14, 6, 15, 5)),
    Ogro(13, new Caracteristicas(16, 11, 12, 7, 16, 10));

    private final int armadura;
    private final Caracteristicas caracteristicas;
    
    TipoEnemigo(int armadura, Caracteristicas caracteristicas){
        this.armadura = armadura;
        this.caracteristicas = caracteristicas;
    }

    public int getArmadura(){
        return this.armadura;
    }
}
