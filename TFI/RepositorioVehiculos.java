import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioVehiculos<T extends Vehiculo> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String NOMBRE_ARCHIVO = "inventario.dat";
    private List<T> inventario;

    public RepositorioVehiculos() {
        this.inventario = new ArrayList<>();
    }

    public void cargarDatos() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO))) {
            this.inventario = (List<T>) ois.readObject(); 
            System.out.println("Carga de datos exitosa desde " + NOMBRE_ARCHIVO);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Archivo de inventario no encontrado o primera ejecución. Se inicia vacío.");
            if (this.inventario == null) {
                this.inventario = new ArrayList<>();
            }
        }
    }

    public void guardarDatos() throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(this.inventario);
            System.out.println("Guardado de datos exitoso en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("ERROR Persistencia: No se pudo guardar el inventario. " + e.getMessage());
        }
    }

    public boolean agregarVehiculo(T vehiculo) {
        if (vehiculo != null) {
            inventario.add(vehiculo);
            System.out.println("Vehículo agregado: " + vehiculo.obtenerDatosBase());
            return true;
        }
        return false;
    }

    public boolean eliminarVehiculo(String marca, String modelo) {
        return inventario.removeIf(v -> v.getMarca().equalsIgnoreCase(marca) && 
                                    v.getModelo().equalsIgnoreCase(modelo));
    }

    public T buscarVehiculo(String marca, String modelo) {
        for (T vehiculo : inventario) {
            if (vehiculo.getMarca().equalsIgnoreCase(marca) && 
                vehiculo.getModelo().equalsIgnoreCase(modelo)) {
                return vehiculo;
            }
        }
        return null; 
    }
    
    public List<T> buscarTodosPorMarcaYModelo(String marca, String modelo) {
        return inventario.stream()
                .filter(v -> v.getMarca().equalsIgnoreCase(marca) && 
                              v.getModelo().equalsIgnoreCase(modelo))
                .collect(Collectors.toList());
    }
    
    public List<T> obtenerTodos() {
        return new ArrayList<>(inventario); 
    }
    
    public List<T> obtenerUsadosParaTaller() {
        return inventario.stream()
                .filter(Vehiculo::necesitaTaller)
                .collect(Collectors.toList());
    }
    
    public void limpiarInventarioParaSimulacion() {
        this.inventario.clear(); 
    }
}