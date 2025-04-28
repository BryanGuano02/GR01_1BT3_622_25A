package excepciones;

public class RestauranteNoEncontradoException extends RuntimeException {
    public RestauranteNoEncontradoException(Long id) {
        super("Restaurante no encontrado con ID: " + id);
    }
}
