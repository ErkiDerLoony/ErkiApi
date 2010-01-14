package erki.api.storage;

import java.io.IOException;

public class SimpleTest {
    
    public static void main(String[] args) throws IOException {
        JavaObjectStorage<Identifier> storage = new JavaObjectStorage<Identifier>("test.tmp");
        System.out.println(storage.get(new IdentifierKey<Double>(Identifier.EINS)));
    }
}
