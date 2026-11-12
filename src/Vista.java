package vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import modelo.Equipo;
import modelo.Programable;
import modelo.Puntuable;

public class Vista {

    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("\n===== ARENAGT LITE =====");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Crear encuentro");
        System.out.println("3. Crear exhibicion");
        System.out.println("4. Programar o reprogramar evento");
        System.out.println("5. Registrar resultado");
        System.out.println("6. Mostrar tabla de posiciones");
        System.out.println("7. Mostrar calendario");
        System.out.println("0. Salir\n");

        return leerEntero("Seleccione una opcion: ");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);

        return scanner.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } 
            catch (NumberFormatException e) {
                mostrarMensaje("Ingrese un numero entero valido.");
            }
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(mensaje));
            } 
            catch (DateTimeParseException e) {
                mostrarMensaje("Ingrese una fecha valida con formato AAAA-MM-DD.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarEquipos(List<Equipo> equipos) {
        mostrarMensaje("\n--- EQUIPOS ---");

        if (equipos.isEmpty()) {
            mostrarMensaje("No hay equipos registrados.");
        }

        for (Equipo equipo : equipos) {
            mostrarMensaje(equipo.getCodigo() + " | " + equipo.getNombre() + " | " + equipo.getEmpresa());
        }
    }

    public void mostrarEventos(List<Programable> eventos) {
        mostrarMensaje("\n--- EVENTOS ---");

        if (eventos.isEmpty()) {
            mostrarMensaje("No hay eventos registrados.");
        }

        for (Programable evento : eventos) {
            mostrarMensaje(evento.getDescripcion());

            if (evento.estaProgramado()) {
                mostrarMensaje("Fecha: " + evento.getFecha() + " | Hora: " + evento.getHora() + ":00" + " | Sede: " + evento.getSede());
            }
        }
    }

    public void mostrarEncuentros(List<Puntuable> encuentros) {
        mostrarMensaje("\n--- ENCUENTROS ---");

        if (encuentros.isEmpty()) {
            mostrarMensaje("No hay encuentros registrados.");
        }

        for (Puntuable encuentro : encuentros) {
            mostrarMensaje(encuentro.getDescripcion());
        }
    }

    public void mostrarTabla(List<Equipo> equipos) {
        mostrarMensaje("\n--- TABLA DE POSICIONES ---");

        if (equipos.isEmpty()) {
            mostrarMensaje("No hay equipos registrados.");
        }

        int posicion = 1;

        for (Equipo equipo : equipos) {
            mostrarMensaje(posicion + ". " + equipo.getNombre() + " | " + equipo.getEmpresa() + " | Puntos: " + equipo.getPuntosTabla());

            posicion++;
        }
    }

    public void mostrarCalendario(Iterable<Programable> calendario) {
        mostrarMensaje("\n--- CALENDARIO ---");

        boolean hayEventos = false;

        for (Programable evento : calendario) {
            mostrarMensaje(evento.getFecha() + " | " + evento.getHora() + ":00" + " | " + evento.getSede() + " | " + evento.getDescripcion());

            hayEventos = true;
        }

        if (!hayEventos) {
            mostrarMensaje("No hay eventos programados.");
        }
    }
}