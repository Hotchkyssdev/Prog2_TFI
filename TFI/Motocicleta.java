import java.time.Year;

public class Motocicleta extends Vehiculo {
    
    private TipoMotocicleta tipoMotocicleta;
    private int cilindradaCm3; 
    private boolean tieneCajaCambios; 
    private static final double PRECIO_BASE_FABRICA_MOTO = 8000.00;
    
    public TipoMotocicleta getTipoMotocicleta() { 
        return tipoMotocicleta; 
    }
    
    public void setTipoMotocicleta(TipoMotocicleta tipoMotocicleta) { 
        this.tipoMotocicleta = tipoMotocicleta;
    }

    public int getCilindradaCm3() { 
        return cilindradaCm3; 
    }
    
    public void setCilindradaCm3(int cilindradaCm3) { 
        this.cilindradaCm3 = cilindradaCm3; 
    }

    public boolean isTieneCajaCambios() { 
        return tieneCajaCambios; 
    }
    
    public void setTieneCajaCambios(boolean tieneCajaCambios) { 
        this.tieneCajaCambios = tieneCajaCambios; 
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
    
    public Motocicleta() {
        super("", "", 0, null, false);
    }
    
    public Motocicleta(String marca, String modelo, int anio, Color color, boolean esUsado, 
                        TipoMotocicleta tipoMotocicleta, int cilindradaCm3, boolean tieneCajaCambios) {
        super(marca, modelo, anio, color, esUsado);
        this.tipoMotocicleta = tipoMotocicleta;
        this.cilindradaCm3 = cilindradaCm3;
        this.tieneCajaCambios = tieneCajaCambios;
    }
    
    public String obtenerTipoEspecifico() {
        //Si el tipoMotocicleta es null, se usa el String "Tipo NO ESPECIFICADO" en su lugar.
        String tipo = (this.tipoMotocicleta != null) ? this.tipoMotocicleta.name() : "Tipo NO ESPECIFICADO";
        String caja = this.tieneCajaCambios ? " con caja" : " automática";
        
        return "Tipo: " + tipo + ", Cilindrada: " + this.cilindradaCm3 + " cm³" + caja;
    }

    public double calcularPrecio() {
        double precioFinal = PRECIO_BASE_FABRICA_MOTO;
         
        if (this.cilindradaCm3 > 500) {
             precioFinal *= 1.20;
        }
        
        if (this.esUsado) {
            precioFinal *= 0.65; 
            if (this.mantenimientoPreventivoVentaRealizado) {
                 precioFinal += 300.00; 
            }
        } else {
            precioFinal *= 1.10;
        }
        return precioFinal;
    }
}