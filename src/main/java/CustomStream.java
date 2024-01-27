import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface CustomStream<T> {

    Streams<T> filter(Predicate<? super T> predicate);

    <R, E extends Exception> CustomStream<R> transform(FunctionWithException<? super T, ? extends R, E> trans) throws E;

    Streams<T> map(Function<? super T, ? extends T> mapper);

    <R> Streams<R> flatMap(Function<? super T, ? extends CustomStream<? extends R>> mapper);

    Streams<T> sorted(Comparator<? super T> comparator);

    Streams<T> limit(long maxSize);

    <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper);

    void forEach(Consumer<? super T> action);

    List<T> toList();
}
