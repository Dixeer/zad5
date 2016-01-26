
public class Component<T extends Comparable<T>>
        implements Comparable<Component<T>>
{
    private int counter = 1;
    private T val;

    public T getValue()
    {
        return val;
    }

    public void increaseCounter()
    {
        counter++;
    }

    public Component(T value)
    {
        this.val = value;
    }

    public Component(T value, int counter)
    {
        this.val = value;
        this.counter = counter;
    }

    public int getCounter()
    {
        return counter;
    }

    @Override
    public int compareTo(Component<T> e)
    {
        return val.compareTo(e.getValue());
    }
}