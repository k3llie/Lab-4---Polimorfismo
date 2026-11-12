package modelo;

public class Baloncesto extends Encuentro {

    public Baloncesto(int id, Equipo equipo1, Equipo equipo2) {
        super(id, equipo1, equipo2);
    }

    @Override
    protected void validarMarcador(int marcador1, int marcador2) {
        if (marcador1 < 0 || marcador2 < 0) {
            throw new IllegalArgumentException("Los marcadores no pueden ser negativos.");
        }

        if (marcador1 == marcador2) {
            throw new IllegalArgumentException("En baloncesto no se permiten empates.");
        }
    }

    @Override
    public int[] calcularPuntos(int marcador1, int marcador2) {
        validarMarcador(marcador1, marcador2);

        if (marcador1 > marcador2) {
            return new int[] {2, 1};
        }

        return new int[] {1, 2};
    }

    @Override
    public String getDeporte() {
        return "Baloncesto";
    }
}