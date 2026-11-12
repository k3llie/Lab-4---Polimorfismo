package modelo;

import java.time.LocalDate;

public abstract class Encuentro implements Programable, Puntuable {

    private int id;
    private Equipo equipo1;
    private Equipo equipo2;
    private Integer marcador1;
    private Integer marcador2;
    private String estado;
    private LocalDate fecha;
    private int hora;
    private String sede;

    protected Encuentro(int id, Equipo equipo1, Equipo equipo2) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }

        if (equipo1 == null || equipo2 == null) {
            throw new IllegalArgumentException("Los dos equipos son obligatorios.");
        }

        if (equipo1.getCodigo().equalsIgnoreCase(equipo2.getCodigo())) {
            throw new IllegalArgumentException("Los equipos deben ser diferentes.");
        }

        this.id = id;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.marcador1 = null;
        this.marcador2 = null;
        this.estado = "SIN_PROGRAMAR";
        this.fecha = null;
        this.hora = -1;
        this.sede = null;
    }

    @Override
    public int getId() {
        return id;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public Integer getMarcador1() {
        return marcador1;
    }

    public Integer getMarcador2() {
        return marcador2;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public int getHora() {
        return hora;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public boolean estaProgramado() {
        return fecha != null;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (estado.equals("FINALIZADO")) {
            throw new IllegalStateException("No se puede reprogramar un encuentro finalizado.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }

        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("La sede no puede estar vacia.");
        }

        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        this.estado = "PROGRAMADO";
    }

    @Override
    public boolean participaEquipo(Equipo equipo) {
        if (equipo == null) {
            return false;
        }

        return equipo1.getCodigo().equalsIgnoreCase(equipo.getCodigo()) || equipo2.getCodigo().equalsIgnoreCase(equipo.getCodigo());
    }

    @Override
    public void registrarResultado(int marcador1, int marcador2) {
        if (!estado.equals("PROGRAMADO")) {
            throw new IllegalStateException("Solo se registran resultados en encuentros programados y sin resultado previo.");
        }

        validarMarcador(marcador1, marcador2);

        int[] puntos = calcularPuntos(marcador1, marcador2);

        this.marcador1 = marcador1;
        this.marcador2 = marcador2;

        equipo1.sumarPuntos(puntos[0]);
        equipo2.sumarPuntos(puntos[1]);

        estado = "FINALIZADO";
    }

    protected abstract void validarMarcador(int marcador1, int marcador2);

    @Override
    public abstract int[] calcularPuntos(int marcador1, int marcador2);

    public abstract String getDeporte();

    @Override
    public String getDescripcion() {
        String descripcion = "ID " + id + " | " + getDeporte() + " | " + equipo1.getNombre() + " vs " + equipo2.getNombre() + " | " + estado;

        if (marcador1 != null) {
            descripcion += " | Marcador: " + marcador1 + " - " + marcador2;
        }

        return descripcion;
    }
}