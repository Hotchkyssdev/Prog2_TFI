public class Motocicleta extends Vehiculo {
    private TipoMoto tipo;
    
    public Motocicleta(String marca, String modelo, int año, String color, TipoMoto tipo) {
        super(marca, modelo, año, color);
        this.tipo = tipo;
    }
    
    public void mostrarDatos() {
        System.out.println("Motocicleta " + marca + " " + modelo + " (" + año + "), Tipo: " + tipo);
    }
}