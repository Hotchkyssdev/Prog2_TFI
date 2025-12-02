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

        Motocicleta motoNueva = new Motocicleta("Honda", "CB300", Year.now().getValue(), Color.ROJO, false, 
            TipoMotocicleta.TOURING, 300, false); 
        repo.agregarVehiculo(motoNueva);
 
        Camioneta camiUsada = new Camioneta();
        camiUsada.setMarca("Toyota");
        camiUsada.setModelo("Hilux");
        try { 
            camiUsada.setAnio(2015); 
        } catch (ExcepcionVehiculoInvalido e) { 
            System.err.println("Error al crear camioneta usada: " + e.getMessage()); 
        }
        
        camiUsada.setColor(Color.BLANCO);
        camiUsada.setEsUsado(true);
        camiUsada.setTieneDobleTraccion(true);
        camiUsada.setCapacidadCargaKg(1000);   
        repo.agregarVehiculo(camiUsada);
        
        Automovil autoNuevo = new Automovil();
        autoNuevo.setMarca("VW");
        autoNuevo.setModelo("Polo");
        try { 
            autoNuevo.setAnio(Year.now().getValue()); 
        } catch (ExcepcionVehiculoInvalido e) { 
            System.err.println("Error al crear auto nuevo: " + e.getMessage()); 
        }
        
        autoNuevo.setColor(Color.GRIS);
        autoNuevo.setEsUsado(false);
        autoNuevo.setCarroceria(TipoCarroceria.HATCHBACK);
        repo.agregarVehiculo(autoNuevo);
        
        Motocicleta motoUsada = new Motocicleta();
        motoUsada.setMarca("Kawasaki");
        motoUsada.setModelo("Ninja 400");
        try { 
            motoUsada.setAnio(2021); 
        } catch (ExcepcionVehiculoInvalido e) { 
            System.err.println("Error al crear moto usada: " + e.getMessage()); 
        }
        
        motoUsada.setColor(Color.GRIS);
        motoUsada.setEsUsado(true);
        repo.agregarVehiculo(motoUsada);
        
        Automovil focusAdicional = new Automovil();
        focusAdicional.setMarca("Ford");
        focusAdicional.setModelo("Focus");
        try { 
            focusAdicional.setAnio(2022); 
        } catch (ExcepcionVehiculoInvalido e) { 
            System.err.println("Error al crear Focus adicional: " + e.getMessage()); 
        }
        
        focusAdicional.setColor(Color.NEGRO);
        focusAdicional.setEsUsado(false); 
        focusAdicional.setCarroceria(TipoCarroceria.HATCHBACK);
        repo.agregarVehiculo(focusAdicional);
        
        System.out.println("\n--- PRUEBA DE FUNCIONALIDAD ABMc ---");
        
        Vehiculo encontrado = repo.buscarVehiculo("VW", "Polo");
        if (encontrado != null) {
            System.out.println("-> [Búsqueda Simple] Éxito: VW Polo encontrado. Precio: " + encontrado.calcularPrecio());
        } else {
            System.out.println("-> [Búsqueda Simple] Error: VW Polo no encontrado.");
        }
        
        boolean eliminado = repo.eliminarVehiculo("Kawasaki", "Ninja 400");
        if (eliminado) {
            System.out.println("-> [Baja] Éxito: Kawasaki Ninja 400 eliminada del inventario.");
        } else {
            System.out.println("-> [Baja] Error: Kawasaki Ninja 400 no se pudo eliminar (no existe).");
        }
        
        List<Vehiculo> focusList = repo.buscarTodosPorMarcaYModelo("Ford", "Focus");
        System.out.println("-> [Búsqueda Múltiple] Encontrados " + focusList.size() + " Ford Focus.");
        
        System.out.println("\n--- PRUEBA DE SERVICIO ADICIONAL ---");
        if (autoNuevo != null) {
            boolean resultadoLavado = taller.realizarLavadoYDetallado(autoNuevo); 
            if (resultadoLavado) {
                 System.out.println("-> [Servicio] El VW Polo fue lavado y detallado con éxito.");
            } else {
                 System.out.println("-> [Servicio] Falló la limpieza del VW Polo (Error interno).");
            }
        }
        
        System.out.println("El VW Polo ha pasado por el lavadero centralizado.");
        
        System.out.println("\n--- PROCESO DE TALLER ---");
        List<Vehiculo> vehiculosParaTaller = repo.obtenerUsadosParaTaller();
        
        for (Vehiculo v : vehiculosParaTaller) {
            cola.agregarACola(v);
            System.out.println("-> " + v.getMarca() + " " + v.getModelo() + " ingresado a la Cola.");
        }

        Vehiculo aMantener1 = cola.siguienteVehiculo();
        if (aMantener1 != null) {
            System.out.println("Taller: Iniciando Mantenimiento Preventivo 1 a: " + aMantener1.getMarca() + " " + aMantener1.getModelo());
            taller.realizarMantenimientoPreventivo(aMantener1);
        }
        
        Vehiculo aMantener2 = cola.siguienteVehiculo();
        if (aMantener2 != null) {
            System.out.println("Taller: Iniciando Mantenimiento Preventivo 2 a: " + aMantener2.getMarca() + " " + aMantener2.getModelo());
            taller.realizarMantenimientoPreventivo(aMantener2);
        }
        
        repo.guardarDatos();
        imprimirInventario(repo);
        
        Main controlador = new Main();
        /*System.out.println("\n--- PRUEBA DE CONTROL DE DATA-ENTRY ---");
        System.out.println("\n[PRUEBA 1] Intentando ingresar año futuro (2028)...");
        controlador.ingresarAnioDesdeUsuario("2028", autoUsado); 

        System.out.println("\n[PRUEBA 2] Intentando ingresar texto (ABCD)...");
        controlador.ingresarAnioDesdeUsuario("ABCD", autoUsado);
        
        System.out.println("\n[VERIFICACIÓN] El año de " + autoUsado.getMarca() + " sigue siendo: " + autoUsado.getAnio());*/
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