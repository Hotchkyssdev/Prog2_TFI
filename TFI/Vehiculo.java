import java.io.Serializable;

public abstract class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected Color color;
    protected boolean esUsado;
    protected boolean mantenimientoPreventivoVentaRealizado;
    
    public String getMarca() { 
        return marca; 
    }
    
    public String getModelo() { 
        return modelo; 
    }
    
    public int getAnio() { 
        return anio; 
    }
    
    public Color getColor() { 
        return color; 
    }
    
    public boolean isEsUsado() { 
        return esUsado; 
    }
    
    public boolean isMantenimientoPreventivoVentaRealizado() { 
        return mantenimientoPreventivoVentaRealizado; 
    }
    
    public void setMantenimientoPreventivoVentaRealizado(boolean mantenimiento) {
        this.mantenimientoPreventivoVentaRealizado = mantenimiento;
    }
    
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
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Vehiculo vehiculo = (Vehiculo) o;
                
        if (anio != vehiculo.anio) {
            return false;
        }
        
        if (marca == null) {
            if (vehiculo.marca != null) {
                return false; 
            }
        } else {
            if (vehiculo.marca == null || !marca.equalsIgnoreCase(vehiculo.marca)) {
                return false; 
            }
        }
        
        if (modelo == null) {
            if (vehiculo.modelo != null) {
                return false;
            }
        } else {
            if (vehiculo.modelo == null || !modelo.equalsIgnoreCase(vehiculo.modelo)) {
                return false;
            }
        }
        
        return true;
    }

    public int hashCode() {
        int result = 1; 
        result = 31 * result + anio;
        
        int marcaHash = 0;
        if (marca != null) {
            marcaHash = marca.toLowerCase().hashCode();
        }
        result = 31 * result + marcaHash;
        
        int modeloHash = 0;
        if (modelo != null) {
            modeloHash = modelo.toLowerCase().hashCode();
        }
        result = 31 * result + modeloHash;
        
        return result;
    }
    
    public String toString() {
        return obtenerDatosBase() + ", Precio Estimado: " + calcularPrecio();
    }
}