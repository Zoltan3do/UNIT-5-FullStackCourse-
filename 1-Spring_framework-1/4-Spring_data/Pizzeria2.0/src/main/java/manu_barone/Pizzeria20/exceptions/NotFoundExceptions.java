package manu_barone.Pizzeria20.exceptions;

public class NotFoundExceptions extends RuntimeException{
    public NotFoundExceptions(long id) {
        super("La risorsa con id " + id + " non è stata trovata!");
    }
}
