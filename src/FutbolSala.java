package modelo;

public class FutbolSala extends Encuentro {

    public FutbolSala(int id, Equipo equipo1, Equipo equipo2) {
        super(id, equipo1, equipo2);
    }

    @Override
    protected void validarMarcador(int marcador1, int marcador2) {
        if (marcador1 < 0 || marcador2 < 0) {
            throw new IllegalArgumentException("Los marcadores no pueden ser negativos.");
        }
    }

    @Override
    public int[] calcularPuntos(int marcador1, int marcador2) {
        validarMarcador(marcador1, marcador2);

        if (marcador1 > marcador2) {
            return new int[] {3, 0};
        }

        if (marcador1 < marcador2) {
            return new int[] {0, 3};
        }

        return new int[] {1, 1};
    }

    @Override
    public String getDeporte() {
        return "Futbol sala";
    }
}