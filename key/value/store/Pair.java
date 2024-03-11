package key.value.store;

public class Pair<T, T1> {
    private T first;
    private T1 second;

    public Pair(T token, T1 token1) {
        first = token;
        second = token1;
    }

    public T getFirst() {
        return first;
    }

    public T1 getSecond() {
        return second;
    }

    public void setFirst(T v1) {
        this.first = v1;
    }

    public void setSecond(T1 v2) {
        this.second = v2;
    }
}
