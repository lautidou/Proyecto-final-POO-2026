package Modelo.objetos;

import Modelo.pjs.Personaje;

public abstract class ObjetoEquipable extends Objeto implements Equipable {

    protected ObjetoEquipable(int idObjeto, String nombre, double peso, int precio, String descripcion) {
        super(idObjeto, nombre, peso, precio, descripcion);
    }

    @Override
    public final void usar(Personaje objetivo) {
        if (objetivo.estaEquipado(this)) objetivo.desequipar(this);
        else objetivo.equipar(this);
    }

    @Override
    public String getDetalle() {
        return super.getDetalle() + "\n\n" + getResumen();
    }
}