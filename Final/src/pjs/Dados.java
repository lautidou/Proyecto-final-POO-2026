package pjs;

public enum Dados {
    d4(4),
    d6(6),
    d8(8),
    d10(10),
    d12(12),
    d20(20);

    private final int max;

    Dados(int max) {
        this.max = max;
    }

    public int rolleo(Dados dado)
    {
        return (int) (Math.random() * max) + 1;
    }
}