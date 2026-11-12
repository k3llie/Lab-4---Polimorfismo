package modelo;

import java.time.LocalDate;

public class Exhibicion implements Programable {

    private int id;
    private String titulo;
    private LocalDate fecha;
    private int hora;
    private String sede;

    public Exhibicion(int id, String titulo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio.");
        }

        this.id = id;
        this.titulo = titulo.trim();
        this.fecha = null;
        this.hora = -1;
        this.sede = null;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
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

    public String getEstado() {
        return estaProgramado() ? "PROGRAMADA" : "SIN_PROGRAMAR";
    }

    @Override
    public boolean estaProgramado() {
        return fecha != null;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
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
    }

    @Override
    public boolean participaEquipo(Equipo equipo) {
        return false;
    }

    @Override
    public String getDescripcion() {
        return "ID " + id + " | Exhibicion: " + titulo + " | " + getEstado();
    }
}