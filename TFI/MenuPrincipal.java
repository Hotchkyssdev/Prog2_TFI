import java.util.Scanner;

public class MenuPrincipal {
    private Scanner sc = new Scanner(System.in);
    private RepositorioVehiculos repo = new RepositorioVehiculos();
    private ColaTaller<Usado> colaTaller = new ColaTaller<>();

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Registrar vehículo usado");
            System.out.println("2. Enviar vehículo al taller");
            System.out.println("3. Procesar taller");
            System.out.println("4. Listar vehículos");
            System.out.println("0. Salir");
            System.out.print("Elija opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());  
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ingresar un número.");
                continue;
            }

            switch (opcion) {
                case 1: 
                    registrarUsado(); 
                    break;
                case 2: 
                    enviarAlTaller(); 
                    break;
                case 3: 
                    procesarTaller(); 
                    break;
                case 4: 
                    listar(); 
                    break;
                case 0: 
                    System.out.println("Saliendo..."); 
                    break;
                default: 
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void registrarUsado() {
        try {
            System.out.print("Marca: ");
            String marca = sc.nextLine();
            
            System.out.print("Modelo: ");
            String modelo = sc.nextLine();
            
            System.out.print("Año: ");
            int año = Integer.parseInt(sc.nextLine());
            
            System.out.print("Color: ");
            String color = sc.nextLine();
            
            System.out.print("Kilometraje: ");
            int km = Integer.parseInt(sc.nextLine());
            
            Usado u = new Usado(marca, modelo, año, color, TipoCarroceria.SEDAN, km);
            repo.agregar(u);
            System.out.println("Vehículo registrado.");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Entrada numérica inválida.");
        } catch (ExcepcionKilometraje e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void enviarAlTaller() {
        for (Vehiculo v : repo.obtenerTodos()) {
            if (v instanceof Usado) {
                colaTaller.encolar((Usado) v);
                System.out.println("Encolado en taller: " + v.getMarca() + " " + v.getModelo());
            }
        }
    }

    private void procesarTaller() {
        while (!colaTaller.estaVacia()) {
            Usado u = colaTaller.desencolar();
            try {
                u.realizarMantenimiento();
                System.out.println("Mantenimiento completado.");
            } catch (ExcepcionMantenimiento e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void listar() {
        for (Vehiculo v : repo.obtenerTodos()) {
            v.mostrarDatos();
        }
    }
}