import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class SortedDeque<T extends Comparable<T>>
{
    List<Component[]> comp = new ArrayList<>();

    public SortedDeque() { }

    int volume,maxsize,size;
    int size_of_comp = 1000;

    public void insert(T t)
    {
        if (size == volume && volume != 0)
            return;
        for (Component[] elements : comp)
            for (Component element : elements)
            {
                if (element != null && element.getValue() == t)
                {
                    element.increaseCounter();
                    maxsize++;
                    return;
                }
            }

        int index = size % size_of_comp;

        if (index != 0 && comp.size() > 0)
        {
            sortAndInsert(t);
        } else
        {
            comp.add(new Component[size_of_comp]);
            sortAndInsert(t);
        }
        size++;
        maxsize++;
    }

    private void sortAndInsert(T value)
    {
        T tmpValue = value;
        boolean change;
        change = false;
        int tmpCounter = 0;

        for (int i = 0; i < comp.size(); ++i)
        {
            Component[] elements = comp.get(i);
            for (int j = 0; j < elements.length; ++j)
            {
                Component e = elements[j];
                if (change)
                {
                    if (tmpValue != null && e != null)
                    {
                        T newValue = (T) e.getValue();
                        int newCounter = e.getCounter();
                        elements[j] = new Component<T>(tmpValue, tmpCounter);
                        tmpValue = newValue;
                        tmpCounter = newCounter;
                    } else if (tmpValue != null && e == null)
                    {
                        elements[j] = new Component<T>(tmpValue, tmpCounter);
                        tmpValue = null;
                    }
                } else
                {
                    if (e == null)
                    {
                        elements[j] = new Component<T>(value);
                        return;
                    }
                    if (e.getValue().compareTo(value) > 0)
                    {
                        tmpValue = (T) e.getValue();
                        tmpCounter = e.getCounter();
                        elements[j] = new Component<T>(value);
                        change = true;
                    }
                }
            }
        }
    }

    public int getTotalSize()
    {
        return maxsize;
    }

    public boolean isEmpty()
    {
        return comp.size() == 0;
    }

    public SortedDeque(int sizeOfBucket)
    {
        size_of_comp = sizeOfBucket;
    }

    public int getCapacity()
    {
        if (volume == 0)
            return size_of_comp * comp.size();
        return volume;
    }

    public int getUniqueSize()
    {
        return size;
    }

    public Object get(int i)
    {
        int numberOfList = (int) Math.ceil(i / size_of_comp);
        int index = i % size_of_comp;
        return comp.get(numberOfList)[index].getValue();
    }

    public int getNumberOfBuckets()
    {
        if (volume != 0)
            return (int) Math.ceil(volume / size_of_comp);
        return comp.size();
    }

    public void reserve(int capacity)
    {
        if (capacity < volume)
            return;
        int dif = capacity % size_of_comp;
        volume = capacity - dif;
    }

    public T back()
    {
        return (T) get(maxsize - 1);
    }

    public List<Component[]> getBuckets()
    {
        return comp;
    }

    public Iterator iterator()
    {
        return new IteratorDQ<T>(this);
    }
}
