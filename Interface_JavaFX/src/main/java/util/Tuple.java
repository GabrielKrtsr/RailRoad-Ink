package util;

/**
 * Represents a generic tuple that stores two related objects of potentially
 * different types.
 *
 * @param <C> the type of the first object in the tuple.
 * @param <H> the type of the second object in the tuple.
 */
public class Tuple<C,H> {

    /**
     * The first object stored in the tuple.
     * Represents a value of a generic type C.
     */
    private  C type1;

    /**
     * The second object stored in the tuple.
     * Represents a value of a generic type H.
     */
    private  H type2;

    /**
     * Constructs a Tuple object with two elements of potentially different types.
     *
     * @param type1 the first element of the tuple
     * @param type2 the second element of the tuple
     */
    public Tuple(C type1,H type2){
        this.type2 = type2;
        this.type1 = type1;
    }

    /**
     * Returns the first object of the tuple.
     *
     * @return the first object of type C stored in the tuple.
     */
    public C getType1() {
        return type1;
    }

    /**
     * Retrieves the value of the second object in the tuple.
     *
     * @return the second object of type H stored in the tuple.
     */
    public H getType2() {
        return type2;
    }

    /**
     * Sets the value of the first object in the tuple.
     *
     * @param type1 the new value to set for the first object in the tuple.
     */
    public void setType1(C type1) {
        this.type1 = type1;
    }

    /**
     * Sets the value of the second object in the tuple.
     *
     * @param type2 the new value to set for the second object.
     */
    public void setType2(H type2){
        this.type2 = type2;
    }

    @Override
    public String toString() {
        return "{ "
                 + type1
                 + ", "
                 + type2 +
                " }";
    }
}
