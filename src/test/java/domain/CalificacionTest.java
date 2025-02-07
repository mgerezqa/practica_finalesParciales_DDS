package domain;

import domain.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CalificacionTest {
    private Consumidor consumidor;
    private Prestador prestador;
    private Trabajo trabajo;
    private Calificacion calificacion;






    @Test
    public void testCalificacionCreadaCorrectamente() {
        // Preparación
        consumidor = new Consumidor("Juan");
        prestador = new Prestador("Pedro");
        trabajo = new Trabajo();
        // Ejecución
        calificacion = new Calificacion(
                5,
                "Excelente servicio",
                consumidor,
                trabajo);
        trabajo.setTrabajoEstado(EstadoDeTrabajo.FINALIZADO);
        // Verificación
        assertEquals(5, calificacion.getEstrellas());
        assertEquals("Excelente servicio", calificacion.getComentario());
        assertEquals(consumidor, calificacion.getConsumidor());
        assertEquals(1, prestador.getCalificacionesRecibidas().size());


    }

    @Test
    public void testNoSePuedeCalificarUnTrabajoNoFinalizado() {
        // Preparación
        Consumidor consumidor = new Consumidor("Juan");
        Prestador prestador = new Prestador("Pedro");
        Trabajo trabajo = new Trabajo();
        trabajo.setTrabajoEstado(EstadoDeTrabajo.EN_CURSO);

        // Ejecución y Verificación
        assertThrows(IllegalStateException.class, () -> {
            calificacion.calificar(consumidor, prestador, trabajo, 4, "Buen trabajo");
        });
    }

    @Test
    public void testCalificacionConEstrellasInvalidas() {
        // Preparación
        Consumidor consumidor = new Consumidor("Juan");
        Prestador prestador = new Prestador("Pedro");
        Trabajo trabajo = new Trabajo();
        trabajo.setTrabajoEstado(EstadoDeTrabajo.FINALIZADO);

        // Ejecución y Verificación
        assertThrows(IllegalArgumentException.class, () -> {
            calificacion.calificar(consumidor, prestador, trabajo, 6, "Demasiado bueno"); // Más de 5 estrellas
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calificacion.calificar(consumidor, prestador, trabajo, 0, "Malo"); // Menos de 1 estrella
        });
    }

    @Test
    public void testPromedioDeCalificacionesEsCorrecto() {
        // Preparación
        Prestador prestador = new Prestador("Pedro");
        Consumidor consumidor1 = new Consumidor("Juan");
        Consumidor consumidor2 = new Consumidor("María");

        Trabajo trabajo1 = new Trabajo();
        trabajo1.setTrabajoEstado(EstadoDeTrabajo.FINALIZADO);

        Trabajo trabajo2 = new Trabajo();
        trabajo2.setTrabajoEstado(EstadoDeTrabajo.FINALIZADO);

        // Ejecución
        calificacion.calificar(consumidor1, prestador, trabajo1, 5, "Excelente");
        calificacion.calificar(consumidor2, prestador, trabajo2, 3, "Bueno");

        // Verificación
        assertEquals(4.0, prestador.obtenerPromedioCalificaciones());
    }
}