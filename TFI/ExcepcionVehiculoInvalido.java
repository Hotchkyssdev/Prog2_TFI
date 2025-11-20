import java.io.Serializable;

public class ExcepcionVehiculoInvalido extends Exception implements Serializable {
    private static final long serialVersionUID = 1L; 

    public ExcepcionVehiculoInvalido(String mensaje) {
        super(mensaje);
    }

    public ExcepcionVehiculoInvalido(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}