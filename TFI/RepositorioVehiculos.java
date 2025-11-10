import java.util.List;
import java.util.ArrayList;

public class RepositorioVehiculos {
    private List<Vehiculo> lista = new ArrayList<>();

    public void agregar(Vehiculo v) {
        lista.add(v);
    }

    public List<Vehiculo> obtenerTodos() {
        return lista;
    }
}