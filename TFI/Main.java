import java.lang.Integer;
import java.time.Year;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static RepositorioVehiculos<Vehiculo> repo;
    private static TallerServicio taller; 
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, IOException, ExcepcionVehiculoInvalido { 
        
        repo = new RepositorioVehiculos<>(); 
        taller = TallerServicio.getServicio(); 
        
        try {
            repo.cargarDatos(); 
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Advertencia: No se pudo cargar el inventario existente. Se iniciará un nuevo inventario vacío.");
        }
        
        ejecutarMenuPrincipal();
        
        try {
            repo.guardarDatos();
            System.out.println("\nGuardado de datos exitoso en inventario.dat.");
        } catch (IOException e) {
            System.err.println("Error fatal al guardar el inventario: " + e.getMessage());
        } catch (ClassNotFoundException e) {
             System.err.println("Error fatal: Clase no encontrada al guardar el inventario.");
        } finally {
            scanner.close();
        }
    }

    private static void ejecutarMenuPrincipal() throws ExcepcionVehiculoInvalido {
        int opcion;
        do {
            System.out.println("\n=============================================");
            System.out.println("|| GESTIÓN DE INVENTARIO (CONCESIONARIA) ||");
            System.out.println("=============================================");
            System.out.println("1. Agregar Vehículo");
            System.out.println("2. Buscar Vehículo (por Marca/Modelo)");
            System.out.println("3. Eliminar Vehículo (por Marca/Modelo)");
            System.out.println("4. Mostrar Inventario Actual");
            System.out.println("5. Gestión de Taller y Servicios");
            System.out.println("0. Salir y Guardar");
            System.out.println("---------------------------------------------");
            System.out.print("Seleccione una opción: ");

            try {
                String input = scanner.nextLine();
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Opción no válida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarVehiculo(); 
                    break;
                case 2:
                    buscarVehiculoSimple(); 
                    break;
                case 3:
                    eliminarVehiculo(); 
                    break;
                case 4:
                    mostrarInventario(); 
                    break;
                case 5:
                    gestionarTaller();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación. Guardando datos...");
                    break;
                default:
                    System.out.println("Opción no reconocida.");
            }
        } while (opcion != 0);
    }
    
    private static void agregarVehiculo() throws ExcepcionVehiculoInvalido {
        System.out.println("\n--- AGREGAR VEHÍCULO ---");
        System.out.println("Seleccione el TIPO de vehículo a agregar:");
        System.out.println("1. Automóvil");
        System.out.println("2. Camioneta");
        System.out.println("3. Motocicleta");
        System.out.print("Opción: ");
        String tipoStr = scanner.nextLine().trim();
        
        if (!tipoStr.matches("[1-3]")) {
            System.err.println("Opción no válida. Cancelando alta.");
            return;
        }

        System.out.print("Ingrese Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Ingrese Modelo: ");
        String modelo = scanner.nextLine();
        int anio = solicitarAnio();
        Color color = solicitarColor();
        boolean esUsado = solicitarEsUsado();

        Vehiculo nuevoVehiculo = null;

        try {
            switch (tipoStr) {
                case "1": 
                    System.out.print("Tipo de Carrocería (SEDAN/HATCHBACK/COUPE/RURAL): ");
                    TipoCarroceria carroceria = TipoCarroceria.valueOf(scanner.nextLine().trim().toUpperCase());
                    nuevoVehiculo = new Automovil(marca, modelo, anio, color, esUsado, carroceria);
                    break;
                case "2": 
                    TipoCamioneta tipoCamioneta = solicitarTipoCamioneta(); 
                    int capacidad = solicitarEntero("Capacidad de carga (kg): ");
                    boolean dobleTraccion = solicitarBooleanoConSN("¿Tiene Doble Tracción (4x4)? (s/n): ");
                    
                    nuevoVehiculo = new Camioneta(marca, modelo, anio, color, esUsado, 
                                                  tipoCamioneta, capacidad, dobleTraccion);
                    break;
                case "3": 
                    System.out.print("Tipo de Motocicleta (SPORT/CUSTOM/SCOOTER/NAKED/TOURING): ");
                    TipoMotocicleta tipoMoto = TipoMotocicleta.valueOf(scanner.nextLine().trim().toUpperCase());
                    int cilindrada = solicitarEntero("Cilindrada (cc): ");
                    boolean tieneCajaCambios = solicitarBooleanoConSN("¿Tiene Caja de Cambios? (s/n): ");
                    
                    nuevoVehiculo = new Motocicleta(marca, modelo, anio, color, esUsado, tipoMoto, cilindrada, tieneCajaCambios);
                    break;
            }

            repo.agregarVehiculo(nuevoVehiculo);
            System.out.println("Vehículo " + nuevoVehiculo.getClass().getSimpleName() + " " + marca + " " + modelo + " agregado al repositorio.");
            
            if (esUsado) {
                System.out.println("¡ATENCIÓN!: Vehículo usado detectado. Se recomienda pasar por el Taller/Lavadero antes de la venta.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error de entrada de datos: El valor ingresado para un atributo ENUM (Color/Tipo) no es válido.");
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar el vehículo: " + e.getMessage());
        }
    }
    
    private static void gestionarTaller() {
        System.out.println("\n--- GESTIÓN DE TALLER Y SERVICIOS ---");
        
        List<Vehiculo> usados = repo.obtenerUsadosParaTaller();
        
        if (usados.isEmpty()) {
            System.out.println("No hay vehículos usados que requieran mantenimiento en el inventario.");
            return;
        }

        System.out.println("Vehículos Usados pendientes (" + usados.size() + " en cola de trabajo):");
        
        for (int i = 0; i < usados.size(); i++) {
            Vehiculo v = usados.get(i);
            System.out.println("\nProcesando (" + (i + 1) + "/" + usados.size() + ") [FIFO]: " + v.getMarca() + " " + v.getModelo());
            
            taller.realizarMantenimientoPreventivo(v);
            System.out.println("-> Mantenimiento Preventivo completado.");
            
            taller.realizarLavadoYDetallado(v);
            System.out.println("-> Lavado y Detallado completado.");
            
            System.out.println("-> Vehículo listo para la venta."); 
        }
        System.out.println("\n--- Proceso de Taller completado para todos los vehículos pendientes. ---");
    }

    public static void imprimirListaVehiculosConIndice(List<Vehiculo> lista) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("IDX | [Tipo] | Marca | Modelo | Año | Color | Usado | Detalles Específicos");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < lista.size(); i++) {
            Vehiculo v = lista.get(i);
            String tipoVehiculo = v.getClass().getSimpleName();
            String detallesEspecificos = v.getDetallesEspecificos(); 

            System.out.println(String.format("%-3d | [%s] | %s | %s | %d | %s | %s | %s",
                                            i + 1,
                                            tipoVehiculo,
                                            v.getMarca(),
                                            v.getModelo(),
                                            v.getAnio(),
                                            v.getColor(),
                                            v.isEsUsado() ? "Usado" : "Nuevo",
                                            detallesEspecificos));
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void imprimirInventario(RepositorioVehiculos<Vehiculo> repo) {
        System.out.println("\n--- INVENTARIO ACTUAL COMPLETO ---");
        if (repo.obtenerTodos().isEmpty()) {
             System.out.println("[Vacío]");
             return;
        }
        imprimirListaVehiculosConIndice(repo.obtenerTodos());
    }
    
    private static void mostrarInventario() {
        imprimirInventario(repo);
    }
    
    private static void buscarVehiculoSimple() {
        System.out.println("\n--- BÚSQUEDA DE VEHÍCULO ---");
        System.out.print("Ingrese Marca a buscar: ");
        String marca = scanner.nextLine();
        System.out.print("Ingrese Modelo a buscar: ");
        String modelo = scanner.nextLine();
        
        List<Vehiculo> encontrados = repo.buscarTodosPorMarcaYModelo(marca, modelo); 
        
        if (encontrados.isEmpty()) {
            System.out.println("Vehículo no encontrado: " + marca + " " + modelo);
        } else {
            System.out.println("Éxito: Se encontraron " + encontrados.size() + " coincidencias:");
            imprimirListaVehiculosConIndice(encontrados);
        }
    }
    
    private static void eliminarVehiculo() throws ExcepcionVehiculoInvalido {
        System.out.println("\n--- ELIMINAR VEHÍCULO ESPECÍFICO ---");
        System.out.print("Ingrese Marca del vehículo a eliminar: ");
        String marca = scanner.nextLine();
        System.out.print("Ingrese Modelo del vehículo a eliminar: ");
        String modelo = scanner.nextLine();
        
        List<Vehiculo> coincidencias = repo.buscarTodosPorMarcaYModelo(marca, modelo);
        
        if (coincidencias.isEmpty()) {
            System.out.println("Error: No se encontró ningún vehículo " + marca + " " + modelo + " para eliminar.");
            return;
        }

        System.out.println("Se encontraron " + coincidencias.size() + " vehículos. Seleccione el número a eliminar:");
        imprimirListaVehiculosConIndice(coincidencias);

        int indiceSeleccionado = -1;
        boolean indiceValido = false;
        while (!indiceValido) {
            System.out.print("Ingrese el número del vehículo a ELIMINAR (1 a " + coincidencias.size() + "): ");
            try {
                String input = scanner.nextLine();
                indiceSeleccionado = Integer.parseInt(input);
                if (indiceSeleccionado >= 1 && indiceSeleccionado <= coincidencias.size()) {
                    indiceValido = true;
                } else {
                    System.err.println("Número fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada no válida. Ingrese un número.");
            }
        }
        
        Vehiculo vehiculoAEliminar = coincidencias.get(indiceSeleccionado - 1);
        
        System.out.println("\nEjecutando: Borrando todas las coincidencias para reagregar las restantes...");

        try {
            repo.eliminarVehiculo(marca, modelo); 
            
            for (int i = 0; i < coincidencias.size(); i++) {
                Vehiculo v = coincidencias.get(i);
                if (v != vehiculoAEliminar) {
                    repo.agregarVehiculo(v);
                }
            }
            
            System.out.println("Éxito: Unidad " + vehiculoAEliminar.getMarca() + " " + vehiculoAEliminar.getModelo() + " (Índice " + indiceSeleccionado + ") eliminada del inventario.");

        } catch (Exception e) {
            System.err.println("Error inesperado durante el proceso de eliminación específica: " + e.getMessage());
        }
    }

    private static int solicitarEntero(String prompt) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(prompt);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= 0) {
                    valido = true;
                } else {
                    System.err.println("El valor debe ser positivo o cero.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada no válida. Ingrese un número entero.");
            }
        }
        return valor;
    }

    private static int solicitarAnio() {
        int anio = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print("Ingrese Año (ej: 2024): ");
            try {
                String input = scanner.nextLine();
                anio = Integer.parseInt(input);
                if (anio > 1900 && anio <= Year.now().getValue() + 1) {
                    valido = true;
                } else {
                    System.err.println("Año fuera de rango (1900 - " + (Year.now().getValue() + 1) + ").");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada no válida. Ingrese un número entero.");
            }
        }
        return anio;
    }

    private static Color solicitarColor() {
        Color color = null;
        boolean valido = false;
        while (!valido) {
            System.out.print("Ingrese Color (ROJO/AZUL/BLANCO/NEGRO/GRIS/AMARILLO): ");
            try {
                color = Color.valueOf(scanner.nextLine().trim().toUpperCase());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Color no válido. Verifique la ortografía y use MAYÚSCULAS.");
            }
        }
        return color;
    }

    private static boolean solicitarBooleanoConSN(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt); 
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("s")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.err.println("Entrada no válida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        }
    }

    private static boolean solicitarEsUsado() {
        return solicitarBooleanoConSN("¿Es usado? (s/n): ");
    }

    private static TipoCamioneta solicitarTipoCamioneta() {
        TipoCamioneta tipo = null;
        boolean valido = false;
        while (!valido) {
            System.out.print("Ingrese Tipo de Camioneta (UTILITARIA/PICKUPMEDIANA/PICKUPGRANDE/SUVGRANDE): ");
            try {
                tipo = TipoCamioneta.valueOf(scanner.nextLine().trim().toUpperCase());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Tipo de Camioneta no válido. Verifique la ortografía y use MAYÚSCULAS (UTILITARIA/PICKUPMEDIANA/PICKUPGRANDE/SUVGRANDE).");
            }
        }
        return tipo;
    }
}