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
    
    public Camioneta() {
        super("", "", 0, null, false);
    }

    public String obtenerTipoEspecifico() {
        String traccion = this.tieneDobleTraccion ? "4x4" : "4x2";
        return "Tipo: " + this.tipoCamioneta.name() + ", Carga: " + this.capacidadCargaKg + " Kg, Tracción: " + traccion;
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
}