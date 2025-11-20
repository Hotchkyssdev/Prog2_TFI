public class TallerServicio implements ITallerMecanico, ILavadero {
    
    private static TallerServicio servicio = new TallerServicio();

    private TallerServicio() {
    }

    public static TallerServicio getServicio() {
        return servicio;
    }
    
    public boolean verificarEstado(Vehiculo vehiculo) {
        return vehiculo.necesitaTaller();
    }
    
    public boolean realizarMantenimientoPreventivo(Vehiculo vehiculo) {
        if (vehiculo.necesitaTaller()) {
            System.out.println("Taller: Iniciando mantenimiento para " + vehiculo.obtenerDatosBase());
            try {
                vehiculo.setMantenimientoPreventivoVentaRealizado(true);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean realizarLavadoYDetallado(Vehiculo vehiculo) {
        System.out.println("Lavadero: Realizando lavado y detallado para " + vehiculo.obtenerDatosBase());
        return true;
    }
}