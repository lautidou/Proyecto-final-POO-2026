package Modelo.pjs;

public abstract class ObjetoEquipable extends Objeto implements Equipable {

    protected ObjetoEquipable(int idObjeto, String nombre, double peso, int precio, String descripcion) {
        super(idObjeto, nombre, peso, precio, descripcion);
    }

    @Override
    public final void usar(Personaje objetivo) {
        if (estaEquipadoPor(objetivo)) {
            desequiparDe(objetivo);
        } else {
            equiparEn(objetivo);
        }
    }

    @Override
    public String getDetalle() {
        return super.getDetalle() + "\n\n" + getResumen();
    }
}