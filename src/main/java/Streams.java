import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> implements CustomStream<T>{
    private List<T> data;

    private Streams(List<T> data){
        this.data = new ArrayList<>(data);
    }

    public static <T> Streams<T> of(List<T> list){
        return new Streams<>(list);
    }

    @Override
    public Streams<T> filter(Predicate<? super T> predicate){
        data.removeIf(predicate.negate());
        return this;
    }

    @Override
    public <R, E extends Exception> CustomStream<R> transform(FunctionWithException<? super T, ? extends R, E> trans) throws E {
        List<R> transformed = new ArrayList<>();
        for (T elem : data)
            transformed.add(trans.apply(elem));

        return new Streams<>(transformed);
    }

    @Override
    public Streams<T> map(Function<? super T, ? extends T> mapper){
        List<T> mappedData = new ArrayList<>(data.size());
        for (T element : data) {
            mappedData.add(mapper.apply(element));
        }
        data = mappedData;
        return this;
    }

    @Override
    public <R> Streams<R> flatMap(Function<? super T, ? extends CustomStream<? extends R>> mapper) {
        List<R> flatMappedData = new ArrayList<>();
        for (T element : data) {
            mapper.apply(element).forEach(flatMappedData::add);
        }
        return new Streams<>(flatMappedData);
    }

    @Override
    public Streams<T> sorted(Comparator<? super T> comparator){
        data.sort(comparator);
        return this;
    }

    @Override
    public Streams<T> limit(long maxSize) {
        data.subList((int) maxSize, data.size()).clear();
        return this;
    }

    @Override
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        Map<K, V> result = new HashMap<>();
        for (T element : data) {
            result.put(keyMapper.apply(element), valueMapper.apply(element));
        }
        return result;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        data.forEach(action);
    }

    @Override
    public List<T> toList() {
        return new ArrayList<>(data);
    }
}
