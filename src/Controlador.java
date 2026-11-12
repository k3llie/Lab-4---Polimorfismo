package controlador;

import java.time.LocalDate;
import modelo.Copa;
import vista.Vista;

public class Controlador {

    private Vista vista;
    private Copa copa;

    public Controlador() {
        vista = new Vista();
        copa = new Copa();
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = vista.mostrarMenu();

                switch (opcion) {
                    case 1:
                        gestionarRegistroEquipo();
                        break;

                    case 2:
                        gestionarCreacionEncuentro();
                        break;

                    case 3:
                        gestionarCreacionExhibicion();
                        break;

                    case 4:
                        gestionarProgramacion();
                        break;

                    case 5:
                        gestionarResultado();
                        break;

                    case 6:
                        gestionarTabla();
                        break;

                    case 7:
                        gestionarCalendario();
                        break;

                    case 0:
                        continuar = false;
                        vista.mostrarMensaje("Programa finalizado.");
                        break;

                    default:
                        vista.mostrarMensaje("Opcion invalida.");
                        break;
                }
            } 
            catch (IllegalArgumentException | IllegalStateException e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            }
        }
    }

    private void gestionarRegistroEquipo() {
        String codigo = vista.leerTexto("Codigo del equipo: ");
        String nombre = vista.leerTexto("Nombre del equipo: ");
        String empresa = vista.leerTexto("Empresa: ");

        copa.registrarEquipo(codigo, nombre, empresa);

        vista.mostrarMensaje("Equipo registrado correctamente.");
    }

    private void gestionarCreacionEncuentro() {
        vista.mostrarMensaje("1. Futbol sala");
        vista.mostrarMensaje("2. Baloncesto");

        int deporte = vista.leerEntero("Seleccione el deporte: ");

        if (deporte != 1 && deporte != 2) {
            throw new IllegalArgumentException("Deporte invalido.");
        }

        vista.mostrarEquipos(copa.getEquipos());

        String codigo1 = vista.leerTexto("Codigo del primer equipo: ");
        String codigo2 = vista.leerTexto("Codigo del segundo equipo: ");

        int id;

        if (deporte == 1) {
            id = copa.crearFutbolSala(codigo1, codigo2).getId();
        } 
        else {
            id = copa.crearBaloncesto(codigo1, codigo2).getId();
        }

        vista.mostrarMensaje("Encuentro creado con ID " + id + ".");
    }

    private void gestionarCreacionExhibicion() {
        String titulo = vista.leerTexto("Titulo de la exhibicion: ");

        int id = copa.crearExhibicion(titulo).getId();

        vista.mostrarMensaje("Exhibicion creada con ID " + id + ".");
    }

    private void gestionarProgramacion() {
        vista.mostrarEventos(copa.getEventos());

        int id = vista.leerEntero("ID del evento: ");
        LocalDate fecha = vista.leerFecha("Fecha (AAAA-MM-DD): ");
        int hora = vista.leerEntero("Hora (0 a 23): ");
        String sede = vista.leerTexto("Sede: ");

        copa.programarEvento(id, fecha, hora, sede);

        vista.mostrarMensaje("Programacion guardada correctamente.");
    }

    private void gestionarResultado() {
        vista.mostrarEncuentros(copa.getEncuentros());

        int id = vista.leerEntero("ID del encuentro: ");
        int marcador1 = vista.leerEntero("Marcador del primer equipo: ");
        int marcador2 = vista.leerEntero("Marcador del segundo equipo: ");

        copa.registrarResultado(id, marcador1, marcador2);

        vista.mostrarMensaje("Resultado registrado y puntos actualizados.");
    }

    private void gestionarTabla() {
        vista.mostrarTabla(copa.obtenerTablaPosiciones());
    }

    private void gestionarCalendario() {
        vista.mostrarCalendario(copa);
    }
}