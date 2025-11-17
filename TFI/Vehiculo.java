public abstract class Vehiculo {
    protected String marca;
    protected String modelo;
    protected int anio;
    protected Color color;
    protected boolean esUsado;
    protected boolean mantenimientoPreventivoVentaRealizado;
    
    public Vehiculo(String marca, String modelo, int anio, Color color, boolean esUsado) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.esUsado = esUsado;
        this.mantenimientoPreventivoVentaRealizado = false;
    }
    
    public abstract String obtenerTipoEspecifico();
    public abstract double calcularPrecio();
    
    public boolean necesitaTaller() {
        return this.esUsado && !this.mantenimientoPreventivoVentaRealizado;
    }
    
    public String obtenerDatosBase() {
        String estado;
        String detalleMantenimiento = "";
        
        if (this.esUsado) {
            estado = "Usado";
            if (this.mantenimientoPreventivoVentaRealizado) {
                detalleMantenimiento = " [MANTENIMIENTO. OK]";
            } else {
                detalleMantenimiento = " [PENDIENTE TALLER]";
            }
        } else {
            estado = "0 Km";
        }
        return marca + " " + modelo + " (" + anio + ") - " + color.name() + 
            ". Estado: " + estado + detalleMantenimiento;
    }
    
    public String toString() {
        return obtenerDatosBase() + ", " + obtenerTipoEspecifico() +
            ", Precio Estimado: " + calcularPrecio();
    }
}