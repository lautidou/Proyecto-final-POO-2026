package pjs;

public class Caracteristicas {
    private int fuerza;
    private int sabiduria;
    private int destreza;
    private int inteligencia;
    private int constitucion;
    private int carisma;

    public Caracteristicas(int fuerza, int sabiduria, int destreza, int inteligencia, int constitucion, int carisma) {
        if (fuerza < 0 || fuerza > 20) {
            throw new IllegalArgumentException("La fuerza debe estar entre 0 y 20.");
        }
        if (sabiduria < 0 || sabiduria > 20) {
            throw new IllegalArgumentException("La sabiduría debe estar entre 0 y 20.");
        }
        if (destreza < 0 || destreza > 20) {
            throw new IllegalArgumentException("La destreza debe estar entre 0 y 20.");
        }
        if (inteligencia < 0 || inteligencia > 20) {
            throw new IllegalArgumentException("La inteligencia debe estar entre 0 y 20.");
        }
        if (constitucion < 0 || constitucion > 20) {
            throw new IllegalArgumentException("La constitución debe estar entre 0 y 20.");
        }
        if (carisma < 0 || carisma > 20) {
            throw new IllegalArgumentException("El carisma debe estar entre 0 y 20.");
        }

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
