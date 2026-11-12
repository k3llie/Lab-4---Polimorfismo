package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Copa implements Iterable<Programable> {

    private ArrayList<Equipo> equipos;
    private ArrayList<Programable> eventos;
    private ArrayList<Puntuable> encuentros;
    private int siguienteId;

    public Copa() {
        equipos = new ArrayList<>();
        eventos = new ArrayList<>();
        encuentros = new ArrayList<>();
        siguienteId = 1;

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        registrarEquipo("E01", "Aguilas", "Empresa Aurora");
        registrarEquipo("E02", "Buhos", "Empresa Aurora");
        registrarEquipo("E03", "Condores", "Empresa Nova");
        registrarEquipo("E04", "Dragones", "Empresa Nova");

        FutbolSala futbol1 = crearFutbolSala("E01", "E02");
        programarEvento(futbol1.getId(), LocalDate.of(2026, 11, 14), 10, "Cancha A");
        registrarResultado(futbol1.getId(), 2, 2);

        Baloncesto baloncesto1 = crearBaloncesto("E03", "E04");
        programarEvento(baloncesto1.getId(), LocalDate.of(2026, 11, 14), 12, "Cancha B");
        registrarResultado(baloncesto1.getId(), 60, 50);

        FutbolSala futbol2 = crearFutbolSala("E01", "E03");
        programarEvento(futbol2.getId(), LocalDate.of(2026, 11, 15), 10, "Cancha A");

        Baloncesto baloncesto2 = crearBaloncesto("E02", "E04");
        programarEvento(baloncesto2.getId(), LocalDate.of(2026, 11, 15), 12, "Cancha B");

        Exhibicion exhibicion = crearExhibicion("Clinica deportiva");
        programarEvento(exhibicion.getId(), LocalDate.of(2026, 11, 14), 16, "Cancha A");
    }

    public Equipo registrarEquipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }

        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new IllegalArgumentException("Ya existe un equipo con ese codigo.");
            }
        }

        Equipo equipo = new Equipo(codigo, nombre, empresa);
        equipos.add(equipo);

        return equipo;
    }

    public Equipo buscarEquipo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }

        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return equipo;
            }
        }

        throw new IllegalArgumentException("No existe un equipo con ese codigo.");
    }

    public FutbolSala crearFutbolSala(String codigo1, String codigo2) {
        FutbolSala encuentro = new FutbolSala(siguienteId, buscarEquipo(codigo1), buscarEquipo(codigo2));

        eventos.add(encuentro);
        encuentros.add(encuentro);
        siguienteId++;

        return encuentro;
    }

    public Baloncesto crearBaloncesto(String codigo1, String codigo2) {
        Baloncesto encuentro = new Baloncesto(siguienteId, buscarEquipo(codigo1), buscarEquipo(codigo2));

        eventos.add(encuentro);
        encuentros.add(encuentro);
        siguienteId++;

        return encuentro;
    }

    public Exhibicion crearExhibicion(String titulo) {
        Exhibicion exhibicion = new Exhibicion(siguienteId, titulo);

        eventos.add(exhibicion);
        siguienteId++;

        return exhibicion;
    }

    public Programable buscarEvento(int id) {
        for (Programable evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }

        throw new IllegalArgumentException("No existe un evento con ese identificador.");
    }

    public Puntuable buscarEncuentro(int id) {
        for (Puntuable encuentro : encuentros) {
            if (encuentro.getId() == id) {
                return encuentro;
            }
        }

        throw new IllegalArgumentException("No existe un encuentro con ese identificador.");
    }

    public void programarEvento(int id, LocalDate fecha, int hora, String sede) {
        Programable evento = buscarEvento(id);

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }

        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("La sede no puede estar vacia.");
        }

        validarConflictos(evento, fecha, hora, sede.trim());
        evento.programar(fecha, hora, sede);
    }

    private void validarConflictos(Programable evento, LocalDate fecha, int hora, String sede) {
        for (Programable otro : eventos) {
            if (otro.getId() == evento.getId() || !otro.estaProgramado()) {
                continue;
            }

            if (otro.getFecha().equals(fecha) && otro.getHora() == hora) {
                if (otro.getSede().equalsIgnoreCase(sede)) {
                    throw new IllegalStateException("La sede ya esta ocupada en esa fecha y hora.");
                }

                for (Equipo equipo : equipos) {
                    if (evento.participaEquipo(equipo) && otro.participaEquipo(equipo)) {
                        throw new IllegalStateException("El equipo " + equipo.getNombre() + " ya tiene un encuentro en esa fecha y hora.");
                    }
                }
            }
        }
    }

    public void registrarResultado(int id, int marcador1, int marcador2) {
        buscarEncuentro(id).registrarResultado(marcador1, marcador2);
    }

    public List<Equipo> getEquipos() {
        return new ArrayList<>(equipos);
    }

    public List<Programable> getEventos() {
        return new ArrayList<>(eventos);
    }

    public List<Puntuable> getEncuentros() {
        return new ArrayList<>(encuentros);
    }

    public List<Equipo> obtenerTablaPosiciones() {
        List<Equipo> tabla = new ArrayList<>(equipos);

        Collections.sort(tabla);

        return tabla;
    }

    @Override
    public Iterator<Programable> iterator() {
        List<Programable> calendario = new ArrayList<>();

        for (Programable evento : eventos) {
            if (evento.estaProgramado()) {
                calendario.add(evento);
            }
        }

        Collections.sort(calendario);

        return calendario.iterator();
    }
}