package modelo;

public interface Puntuable {

    int getId();

    String getDescripcion();

    void registrarResultado(int marcador1, int marcador2);

    int[] calcularPuntos(int marcador1, int marcador2);
}