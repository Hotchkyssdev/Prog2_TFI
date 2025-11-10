import java.util.Queue;
import java.util.LinkedList;

public class ColaTaller<T> {
    private Queue<T> cola = new LinkedList<>();

    public void encolar(T elemento) {
        cola.add(elemento);
    }

    public T desencolar() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
}