import java.util.Iterator;

public class IteratorDQ<T extends Comparable<T>>
        implements Iterator<T>
{
    int cur;
    SortedDeque<T> sorteddeque;

    public IteratorDQ(SortedDeque<T> sortedDeque)
    {
        sorteddeque = sortedDeque;
    }

    @Override
    public T next()
    {
        int tmp = 0;
        while (true)
        {
            for (int i = 0; i < sorteddeque.getBuckets().size(); ++i)
                for (int j = 0; j < sorteddeque.getBuckets().get(i).length; ++j)
                    for (int k = 0; k < sorteddeque.getBuckets().get(i)[j].getCounter(); ++k)
                        if (tmp++ == cur) {
                            ++cur;
                            return (T) sorteddeque.getBuckets().get(i)[j].getValue();
                        }
        }
    }

    @Override
    public boolean hasNext()
    {
        return sorteddeque.getTotalSize() > cur;
    }
}
