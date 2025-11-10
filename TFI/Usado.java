public class Usado extends Automovil implements Mantenible {
    private int kilometraje;
    private boolean mantenimientoRealizado;

    public Usado(String marca, String modelo, int año, String color, TipoCarroceria carroceria, int km) throws ExcepcionKilometraje {
        super(marca, modelo, año, color, carroceria);
        if (km < 0) {
            throw new ExcepcionKilometraje("El kilometraje no puede ser negativo.");
        }
        this.kilometraje = km;
        this.mantenimientoRealizado = false;
    }

    public void realizarMantenimiento() throws ExcepcionMantenimiento {
        if (mantenimientoRealizado) {
            throw new ExcepcionMantenimiento("El mantenimiento ya se realizó.");
        }
        System.out.println("Realizando mantenimiento preventivo...");
        mantenimientoRealizado = true;
    }
}