import entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StreamsTest {

    private List<Person> people;

    @BeforeEach
    void init(){
        people = new ArrayList<>();
        people.add(new Person("Aleksey", 20));
        people.add(new Person("Valeriya", 20));
        people.add(new Person("Ivan", 30));
        people.add(new Person("Petr", 10));
        people.add(new Person("Garfield", 25));
    }

    @Test
    void testFilter(){
        List<Person> filteredList = Streams.of(people)
                .filter(person -> person.getAge() > 20).toList();

        assertEquals(2, filteredList.size());
        assertTrue(filteredList.stream().allMatch(person -> person.getAge() > 20));
    }

    @Test
    void testTransform(){
        List<Person> transformed = Streams.of(people)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toList();

        assertTrue(transformed.stream().allMatch(person -> person.getAge() > 30));
    }

    @Test
    void testFlatMap(){
        List<Person> result = Streams.of(people)
                .flatMap(person -> Streams.of(Arrays.asList(person, new Person(person.getName(),
                        person.getAge() + 30))))
                .toList();

        assertEquals(10, result.size());
    }
}