package erki.api.storage;

public class IdentifierKey<T> extends Key<T, Identifier> {
    
    private static final long serialVersionUID = -4939483073525618440L;
    
    public IdentifierKey(Identifier id) {
        super(id);
    }
}
