import java.time.Year;

public class Camioneta extends Vehiculo {
    private TipoCamioneta tipoCamioneta;
    private int capacidadCargaKg; 
    private boolean tieneDobleTraccion; 
    private static final double PRECIO_BASE_FABRICA_CAMIONETA = 35000.00;
    
    public TipoCamioneta getTipoCamioneta() { 
        return tipoCamioneta; 
    }
    
    public void setTipoCamioneta(TipoCamioneta tipoCamioneta) { 
        this.tipoCamioneta = tipoCamioneta; 
    }

    public int getCapacidadCargaKg() { 
        return capacidadCargaKg; 
    }
    
    public void setCapacidadCargaKg(int capacidadCargaKg) { 
        this.capacidadCargaKg = capacidadCargaKg; 
    }

    public boolean isTieneDobleTraccion() { 
        return tieneDobleTraccion; 
    }
    
    public void setTieneDobleTraccion(boolean tieneDobleTraccion) { 
        this.tieneDobleTraccion = tieneDobleTraccion; 
    }
    
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

    public int getAnio() { 
        return anio; 
    }
    
    public void setAnio(int anio) throws ExcepcionVehiculoInvalido {
        int anioActual = Year.now().getValue();
        if (anio < 1900 || anio > anioActual) {
            throw new ExcepcionVehiculoInvalido(
                "El año del vehículo (" + anio + ") es inválido. Debe estar entre 1900 y " + anioActual + ".");
        }
        this.anio = anio;
    }

    public Color getColor() { 
        return color; 
    }
    
    public void setColor(Color color) { 
        this.color = color; 
    }

    public boolean isEsUsado() { 
        return esUsado; 
    }
    
    public void setEsUsado(boolean esUsado) { 
        this.esUsado = esUsado; 
    }

    public boolean isMantenimientoPreventivoVentaRealizado() { 
        return mantenimientoPreventivoVentaRealizado; 
    }
    
    public void setMantenimientoPreventivoVentaRealizado(boolean mantenimiento) {
        this.mantenimientoPreventivoVentaRealizado = mantenimiento;
    }
    
    public Camioneta(String marca, String modelo, int anio, Color color, boolean esUsado, boolean dobleTraccion, int capacidad) {
        super("", "", 0, null, false);
    }
    
    public Camioneta(String marca, String modelo, int anio, Color color, boolean esUsado, 
                     TipoCamioneta tipoCamioneta, int capacidadCargaKg, boolean tieneDobleTraccion) {
        super(marca, modelo, anio, color, esUsado);
        this.tipoCamioneta = tipoCamioneta;
        this.capacidadCargaKg = capacidadCargaKg;
        this.tieneDobleTraccion = tieneDobleTraccion;
    }

    public String obtenerTipoEspecifico() {
        String tipo = (this.tipoCamioneta != null) ? this.tipoCamioneta.name() : "Tipo NO ESPECIFICADO"; 
        String traccion = this.tieneDobleTraccion ? "4x4" : "4x2";
        return "Tipo: " + tipo + ", Carga: " + this.capacidadCargaKg + " Kg, Tracción: " + traccion;
    }
    
    public double calcularPrecio() {
        double precioFinal = PRECIO_BASE_FABRICA_CAMIONETA;
        
        if (this.tieneDobleTraccion) {
             precioFinal += 4000.00;
        }
        
        if (this.esUsado) {
            precioFinal *= 0.75; 
            if (this.mantenimientoPreventivoVentaRealizado) {
                 precioFinal += 650.00; 
            }
        } else {
            precioFinal *= 1.15;
        }
        
        return precioFinal;
    }
    
    public String toString() {
        String traccion;
        if (tieneDobleTraccion) {
            traccion = "4x4";
        } else {
            traccion = "Simple";
        }
        
        return super.toString() + ", Carga: " + this.capacidadCargaKg + " Kg, Tracción: " + traccion;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!super.equals(o)) {
            return false;
        }
        
        Camioneta camioneta = (Camioneta) o;
        
        if (capacidadCargaKg != camioneta.capacidadCargaKg) {
            return false;
        }
        
        if (tieneDobleTraccion != camioneta.tieneDobleTraccion) {
            return false;
        }
        
        return true; 
    }
    
    public int hashCode() {
        int result = super.hashCode(); 

        result = 31 * result + capacidadCargaKg;
        
        //La convención de Java para booleans es 1231 para true y 1237 para false.
        int traccionHash;
        if (tieneDobleTraccion) {
            traccionHash = 1231;
        } else {
            traccionHash = 1237;
        }
        result = 31 * result + traccionHash;
        
        return result;
    }

    public String getDetallesEspecificos() {
        return String.format("Tipo: %s, Carga: %d kg, 4x4: %s", 
                             this.tipoCamioneta, 
                             this.capacidadCargaKg, 
                             this.tieneDobleTraccion ? "Sí" : "No");
    }
}