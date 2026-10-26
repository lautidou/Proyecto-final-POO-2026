package Modelo.pjs;

import Modelo.combate.CatalogoHabilidades;
import Modelo.combate.CatalogoObjetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public enum Clases {

    Barbaro(Dados.d12,
            CatalogoObjetos::hachaDeGuerra, CatalogoObjetos::armaduraDePieles,
            new Desbloqueo(1, CatalogoHabilidades.golpePoderoso()),
            new Desbloqueo(3, CatalogoHabilidades.tajoBrutal()),
            new Desbloqueo(5, CatalogoHabilidades.furiaDesatada())),
    //Bardo(Dados.d8),
    //Brujo(Dados.d8),
    //Clerigo(Dados.d8),
    //Druida(Dados.d8),
    //Explorador(Dados.d10),
    //Guerrero(Dados.d10),
    //Hechicero(Dados.d6),
    Mago(Dados.d6,
            CatalogoObjetos::bastonArcano, CatalogoObjetos::tunicaDeMago,
            new Desbloqueo(1, CatalogoHabilidades.proyectilMagico()),
            new Desbloqueo(3, CatalogoHabilidades.bolaDeFuego()),
            new Desbloqueo(5, CatalogoHabilidades.rayoArcano())),
    Monje(Dados.d8,
            CatalogoObjetos::vendajesDeCombate, CatalogoObjetos::ropasDeMonje,
            new Desbloqueo(1, CatalogoHabilidades.punoDePalma()),
            new Desbloqueo(3, CatalogoHabilidades.curacionMenor()),
            new Desbloqueo(5, CatalogoHabilidades.rafagaDeGolpes()));
    //Paladin(Dados.d10),
    //Picaro(Dados.d8);

    private final Dados puntosGolpe;
    private final Supplier<Arma> armaInicial;
    private final Supplier<Armadura> armaduraInicial;
    private final List<Desbloqueo> desbloqueos;

    Clases(Dados puntosGolpe, Supplier<Arma> armaInicial, Supplier<Armadura> armaduraInicial,
           Desbloqueo... desbloqueos){
        this.puntosGolpe = puntosGolpe;
        this.armaInicial = armaInicial;
        this.armaduraInicial = armaduraInicial;
        this.desbloqueos = Collections.unmodifiableList(Arrays.asList(desbloqueos));
    }

    public int getMaxPG(){
        return puntosGolpe.getMax();
    }

    public List<Objeto> crearEquipoInicial(Personaje personaje){
        Arma arma = armaInicial.get();
        Armadura armadura = armaduraInicial.get();
        arma.usar(personaje);
        armadura.usar(personaje);

        List<Objeto> equipo = new ArrayList<>();
        equipo.add(arma);
        equipo.add(armadura);
        equipo.add(CatalogoObjetos.pocionCurativa(5));
        return equipo;
    }

    public List<Habilidad> getHabilidadesPosibles(){
        return getHabilidadesHastaNivel(Integer.MAX_VALUE);
    }

    public List<Habilidad> getHabilidadesHastaNivel(int nivel){
        List<Habilidad> desbloqueadas = new ArrayList<>();
        for (Desbloqueo desbloqueo : desbloqueos) {
            if (desbloqueo.getNivelRequerido() <= nivel) {
                desbloqueadas.add(desbloqueo.getHabilidad());
            }
        }
        return desbloqueadas;
    }

    public boolean permiteAprender(Habilidad habilidad, int nivel){
        return getHabilidadesHastaNivel(nivel).contains(habilidad);
    }
}