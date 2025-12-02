import java.lang.Integer;
import java.time.Year;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        RepositorioVehiculos<Vehiculo> repo = new RepositorioVehiculos<>();
        TallerServicio taller = TallerServicio.getServicio();
        ColaTaller cola = new ColaTaller();
        
        repo.limpiarInventarioParaSimulacion();
        
        System.out.println("--- INICIO DE SIMULACIÓN DE INVENTARIO ---");
        Automovil autoUsado = new Automovil();
        autoUsado.setMarca("Ford");
        autoUsado.setModelo("Focus");
        
        try {
            autoUsado.setAnio(2018); 
        } catch (ExcepcionVehiculoInvalido e) {
             System.err.println("Error al crear auto usado: " + e.getMessage());
        }
        autoUsado.setColor(Color.AZUL);
        autoUsado.setEsUsado(true); 
        autoUsado.setCarroceria(TipoCarroceria.SEDAN);
        repo.agregarVehiculo(autoUsado);

        Motocicleta motoNueva = new Motocicleta();
        motoNueva.setMarca("Honda");
        motoNueva.setModelo("CB300");
        motoNueva.setColor(Color.ROJO);
        
        try {
            motoNueva.setAnio(Year.now().getValue());
        } catch (ExcepcionVehiculoInvalido e) {
             System.err.println("Error al crear moto nueva: " + e.getMessage());
        }
        motoNueva.setEsUsado(false); 
        repo.agregarVehiculo(motoNueva);
    
        System.out.println("\n--- PROCESO DE TALLER ---");
        List<Vehiculo> vehiculosParaTaller = repo.obtenerUsadosParaTaller();
        
        for (Vehiculo v : vehiculosParaTaller) {
            cola.agregarACola(v);
        }

        Vehiculo aMantener = cola.siguienteVehiculo();
        if (aMantener != null) {
             taller.realizarMantenimientoPreventivo(aMantener);
        }
        
        repo.guardarDatos();
        
        imprimirInventario(repo);
        
        Main controlador = new Main();
        /*System.out.println("\n--- PRUEBA DE CONTROL DE DATA-ENTRY ---");
        controlador.ingresarAnioDesdeUsuario("2028", autoUsado); 
        controlador.ingresarAnioDesdeUsuario("ABCD", autoUsado);*/
    }
     
    public void ingresarAnioDesdeUsuario(String entradaUsuario, Automovil auto) {
        try {
            int anioIngresado = Integer.parseInt(entradaUsuario); 
            auto.setAnio(anioIngresado); 
            System.out.println("Año establecido correctamente: " + anioIngresado);
        } catch (NumberFormatException e) {
            System.err.println("ERROR UX: Se esperaba un número entero válido.");
            
        } catch (ExcepcionVehiculoInvalido e) {
            System.err.println("ERROR NEGOCIO: " + e.getMessage());
        }
    }
    
    public static void imprimirInventario(RepositorioVehiculos<Vehiculo> repo) {
        System.out.println("\n--- INVENTARIO FINAL ---");
        for (Vehiculo v : repo.obtenerTodos()) { 
            System.out.println(v); 
        }
    }
}