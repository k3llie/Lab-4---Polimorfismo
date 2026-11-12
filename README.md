# Lab-4 - Polimorfismo
**Nombre completo:** Kellie Sophia López Torres

**Carné:** 261551

## Descripción

ArenaGT Lite permite administrar equipos, encuentros deportivos y exhibiciones. El programa registra equipos, crea encuentros de fútbol sala y baloncesto, programa y reprograma eventos, registra resultados y muestra la tabla de posiciones y el calendario.

El programa está organizado con MVC: la Vista muestra el menú y pide los datos, el Controlador coordina las acciones y el modelo gestiona los equipos, eventos y puntos de tabla.

La clase abstracta Encuentro reúne los datos y comportamientos comunes. Sus clases hijas, FutbolSala y Baloncesto, aplican las reglas de cada deporte mediante polimorfismo.

Las interfaces Programable y Puntuable separan la programación de eventos del registro de resultados. Las exhibiciones implementan únicamente Programable porque no tienen equipos, marcador ni puntos.

Main inicia el programa y Copa carga cuatro equipos de dos empresas, cuatro encuentros de ambos deportes y una exhibición programada. Dos encuentros comienzan finalizados, incluyendo un empate de fútbol sala. Los puntos iniciales permiten observar el desempate por nombre en la tabla.

Los datos se conservan únicamente en memoria durante la ejecución.

## Cómo ejecutar

```bash
javac -d bin -sourcepath src src/Main.java
java -cp bin Main
```

## Propósito de las clases

**Main**

Punto de entrada del programa. Crea el Controlador e inicia la ejecución.

**Controlador**

Coordina el flujo del programa. Crea la Vista y la Copa, mantiene el menú disponible, solicita los datos mediante la Vista y delega las operaciones a Copa. Maneja los errores de cada opción para que el programa continúe funcionando.

**Vista**

Se encarga de la interacción por consola. Presenta el menú, lee textos, números y fechas, y muestra los equipos, eventos, resultados, posiciones, calendario y mensajes de error.

**Copa**

Es el administrador del sistema y la clase principal del modelo. Conserva los equipos y eventos, genera sus identificadores, valida los códigos de equipos y comprueba los conflictos de sede y horario. También coordina el registro de resultados y proporciona la tabla de posiciones y el calendario ordenados.

Carga los datos iniciales mediante el método privado cargarDatosIniciales(). Implementa Iterable<Programable> para recorrer el calendario con un for-each.

**Equipo**

Representa un equipo con código único, nombre, empresa y puntos acumulados. Implementa Comparable<Equipo> para ordenar la tabla por puntos de mayor a menor y, si hay empate, por nombre en orden alfabético.

**Encuentro**

Clase abstracta que representa un encuentro entre dos equipos. Guarda el identificador, los participantes, el marcador, el estado y la programación.

Implementa Programable y Puntuable. Controla los estados sin programar, programado y finalizado. Antes de registrar un resultado, valida el marcador según el deporte, calcula los puntos y los suma una sola vez. Un encuentro finalizado no puede reprogramarse ni recibir otro resultado.

**FutbolSala**

Hereda de Encuentro y aplica las reglas del fútbol sala. Permite empates y asigna 3 puntos al ganador y 0 al perdedor, o 1 punto a cada equipo si empatan.

**Baloncesto**

Hereda de Encuentro y aplica las reglas del baloncesto. Rechaza los empates y asigna 2 puntos al ganador y 1 al perdedor.

**Exhibicion**

Representa una clínica o demostración deportiva con título y programación. Implementa únicamente Programable. Puede programarse y reprogramarse, pero no tiene equipos, marcador ni puntos de tabla.

### Interfaces

**Programable**

Define las operaciones para programar eventos y consultar su información. La implementan Encuentro y Exhibicion.

Extiende Comparable<Programable> y comparte un método compareTo() default para ordenar los eventos por fecha, hora y sede.

**Puntuable**

Define las operaciones para registrar resultados y calcular los puntos de cada equipo. La implementa Encuentro, y cada deporte establece su propio reparto de puntos.

Comparable e Iterable ya vienen incluidas en Java, por lo que no se crean archivos adicionales para ellas.