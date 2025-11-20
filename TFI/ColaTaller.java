import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;

public class ColaTaller implements Serializable {
    private static final long serialVersionUID = 1L;
    private Queue<Vehiculo> colaEspera;

    public ColaTaller() {
        this.colaEspera = new LinkedList<>();
    }

    public void agregarACola(Vehiculo vehiculo) {
        if (vehiculo.necesitaTaller()) {
            colaEspera.offer(vehiculo); // offer es la operación de inserción para Queue
            System.out.println("-> " + vehiculo.obtenerDatosBase() + " ingresado a la Cola del Taller.");
        } else {
            System.out.println("-> " + vehiculo.obtenerDatosBase() + " no necesita Taller o ya fue mantenido.");
        }
    }

    public Vehiculo siguienteVehiculo() {
        return colaEspera.poll(); // poll es la operación de extracción para Queue
    }
    
    public boolean estaVacia() {
        return colaEspera.isEmpty();
    }
}