public class Automovil extends Vehiculo {
    private TipoCarroceria carroceria;
    
    public Automovil(String marca, String modelo, int año, String color, TipoCarroceria carroceria) {
        super(marca, modelo, año, color);
        this.carroceria = carroceria;
    }

    public void mostrarDatos() {
        System.out.println("Automóvil " + marca + " " + modelo + " (" + año + "), Carrocería: " + carroceria);
    }
}