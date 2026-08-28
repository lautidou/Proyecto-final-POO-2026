package pjs;

public enum Raza {
    Enano(0, 0, 2, 0, 2, 0),
    Elfo(0, 0, 2, 0, 0, 0),
    Semiorco(2, 0, 0, 0, 1, 0);

    private final int fuerza;
    private final int sabiduria;
    private final int destreza;
    private final int inteligencia;
    private final int constitucion;
    private final int carisma;

    Raza(int fuerza, int sabiduria, int destreza, int inteligencia, int constitucion, int carisma) {
        this.fuerza = fuerza;
        this.sabiduria = sabiduria;
        this.destreza = destreza;
        this.inteligencia = inteligencia;
        this.constitucion = constitucion;
        this.carisma = carisma;
    }
    public int getFuerza() {
        return fuerza;
    }
    public int getSabiduria() {
        return sabiduria;
    }
    public int getDestreza() {
        return destreza;
    }
    public int getInteligencia() {
        return inteligencia;
    }
    public int getConstitucion() {
        return constitucion;
    }
    public int getCarisma() {
        return carisma;
    }
    
}
