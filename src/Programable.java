package modelo;

import java.time.LocalDate;

public interface Programable extends Comparable<Programable> {

    int getId();

    LocalDate getFecha();

    int getHora();

    String getSede();

    boolean estaProgramado();

    void programar(LocalDate fecha, int hora, String sede);

    boolean participaEquipo(Equipo equipo);

    String getDescripcion();

    @Override
    default int compareTo(Programable otro) {
        int comparacion = getFecha().compareTo(otro.getFecha());

        if (comparacion == 0) {
            comparacion = Integer.compare(getHora(), otro.getHora());
        }

        if (comparacion == 0) {
            comparacion = getSede().compareToIgnoreCase(otro.getSede());
        }

        return comparacion;
    }
}