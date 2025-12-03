import java.time.Year;

public class Automovil extends Vehiculo {
    private TipoCarroceria carroceria;
    private static final double PRECIO_BASE_FABRICA = 20000.00; 
    
        public TipoCarroceria getCarroceria() { 
        return carroceria; 
    }

    public void setCarroceria(TipoCarroceria carroceria) { 
        this.carroceria = carroceria; 
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
    
    public Automovil() {
        super("", "", 0, null, false); 
    }
    
    public Automovil(String marca, String modelo, int anio, Color color, boolean esUsado, 
                 TipoCarroceria carroceria) {
        super(marca, modelo, anio, color, esUsado);
        this.carroceria = carroceria;
    }

    public String obtenerTipoEspecifico() {
        String tipo = (this.carroceria != null) ? this.carroceria.name() : "Tipo NO ESPECIFICADO"; 
        return "Tipo de Carrocería: " + tipo;
    }   
    
    public double calcularPrecio() {
        double precioFinal = PRECIO_BASE_FABRICA;
        if (this.esUsado) { 
            precioFinal *= 0.80; 
            if (this.mantenimientoPreventivoVentaRealizado) {
                precioFinal += 500.00;
            }
        } else {
            precioFinal *= 1.10;
        }
        return precioFinal;
    }
    
    public String toString() {
        return super.toString() + ", " + obtenerTipoEspecifico();
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
   
        if (!super.equals(o)) {
            return false; 
        }
        
        Automovil automovil = (Automovil) o;

        if (carroceria == null) {
            if (automovil.carroceria != null) {
                return false;
            }
        } else {
            if (automovil.carroceria == null || carroceria != automovil.carroceria) {
                return false;
            }
        }
        
        return true; 
    }

    public int hashCode() {
        int result = super.hashCode(); 
        
        int carroceriaHash = 0;
        if (carroceria != null) {
            carroceriaHash = carroceria.hashCode();
        }
        result = 31 * result + carroceriaHash;
        
        return result;
    }

    public String getDetallesEspecificos() {
        return "Carrocería: " + this.carroceria; 
    }
}