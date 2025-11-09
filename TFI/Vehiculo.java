public abstract class Vehiculo {
    protected String marca;
    protected String modelo;
    protected int año;
    protected String color;
    
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getAño() {
        return año;
    }
    public void setAño(int marca) {
        this.año = año;
    }
    
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    
    public Vehiculo() {
        
    }
    
    public Vehiculo(String marca, String modelo, int año, String color) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.color = color;
    }
    
    public abstract void mostrarDatos();
}