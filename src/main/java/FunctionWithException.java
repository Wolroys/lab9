/**
 * Functional interface for a function that may throw an exception.
 *
 * @param <T> The type of the input argument.
 * @param <R> The type of the result of the function.
 * @param <E> The type of the exception.
 */
public interface FunctionWithException<T, R, E extends Exception> {

    /**
     * Applies the function to the given input.
     *
     * @param t The input argument.
     * @return The result of applying the function.
     * @throws E Possible exception thrown during the operation.
     */
    R apply(T t) throws E;
}
