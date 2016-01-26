import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;


public class MainTest {

    int ceilOfDivision(int divident, int divisor)
    {
        return (int) Math.ceil(divident / divisor);
    }

    @Test
    public void testBasic()
    {
        SortedDeque<Integer> sortedDeque = new SortedDeque<>();
        sortedDeque.insert(3);
        sortedDeque.insert(4);
        sortedDeque.insert(5);
        assertEquals(4, sortedDeque.get(1));
    }

    @Test
    public void testConstructionOfEmptyContainer()
    {
        SortedDeque<Integer> sortedDeque = new SortedDeque<>();
        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.getTotalSize());
        assertEquals(0, sortedDeque.getUniqueSize());
        assertEquals(0, sortedDeque.getCapacity());
    }

    @Test
    public void testReserve()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        final int CAPACITY_WHICH_I_WANT = SIZE_OF_BUCKET * 10 + 1;
        sortedDeque.reserve(CAPACITY_WHICH_I_WANT);

        final int EXPECTED_NUMBER_OF_BUCKETS = ceilOfDivision(CAPACITY_WHICH_I_WANT, SIZE_OF_BUCKET);
        assertEquals(EXPECTED_NUMBER_OF_BUCKETS, sortedDeque.getNumberOfBuckets());

        final int EXPECTED_CAPACITY = EXPECTED_NUMBER_OF_BUCKETS * SIZE_OF_BUCKET;
        assertEquals(EXPECTED_CAPACITY, sortedDeque.getCapacity());

        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.getTotalSize());
        assertEquals(0, sortedDeque.getUniqueSize());
    }


    @Test
    public void testReserveWithSmallerNewCapacity()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        final int CAPACITY_WHICH_I_WANT = SIZE_OF_BUCKET * 10;
        sortedDeque.reserve(CAPACITY_WHICH_I_WANT);

        final int CAPACITY_WHICH_I_WANT_NEW = SIZE_OF_BUCKET * 5;
        sortedDeque.reserve(CAPACITY_WHICH_I_WANT_NEW);

        assertEquals(CAPACITY_WHICH_I_WANT / SIZE_OF_BUCKET, sortedDeque.getNumberOfBuckets());
        assertEquals(CAPACITY_WHICH_I_WANT, sortedDeque.getCapacity());

        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.getTotalSize());
        assertEquals(0, sortedDeque.getUniqueSize());
    }

    @Test
    public void testSimpleAddingIntoSingleBucket()
    {
        List<Integer> valuesToAdd = new ArrayList<>();
        valuesToAdd.add(2);
        valuesToAdd.add(0);
        valuesToAdd.add(8);
        valuesToAdd.add(1);

        final int SIZE_OF_BUCKET = 10;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        for (Integer aValuesToAdd : valuesToAdd)
            sortedDeque.insert(aValuesToAdd);

        Collections.sort(valuesToAdd);

        for (int i = 0; i < valuesToAdd.size(); ++i)
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));


        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.getTotalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.getUniqueSize());

        assertEquals(SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(1, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testAddingSmallestValueWhenFirstBucketIsFull()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        List<Integer> valuesToAdd = new ArrayList<>();
        valuesToAdd.add(2);
        valuesToAdd.add(1);
        valuesToAdd.add(9);

        for (Integer value : valuesToAdd)
            sortedDeque.insert(value);

        Collections.sort(valuesToAdd);

        for (int i = 0; i < valuesToAdd.size(); ++i)
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.getTotalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.getUniqueSize());

        assertEquals(SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(1, sortedDeque.getNumberOfBuckets());

        /////////////////////////////////////////////////////////
        final Integer VALUE_SMALLER_THAN_PREVIOUS_VALUES_TO_ADD = 0;
        sortedDeque.insert(VALUE_SMALLER_THAN_PREVIOUS_VALUES_TO_ADD);
        assertEquals(VALUE_SMALLER_THAN_PREVIOUS_VALUES_TO_ADD, sortedDeque.get(0));

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.getTotalSize());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.getUniqueSize());

        assertEquals(2*SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(2, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testAddingGreaterValueWhenFirstBucketIsFull()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        List<Integer> valuesToAdd = new ArrayList<>();
        valuesToAdd.add(2);
        valuesToAdd.add(1);
        valuesToAdd.add(9);

        for (Integer value : valuesToAdd)
            sortedDeque.insert(value);

        Collections.sort(valuesToAdd);

        for (int i = 0; i < valuesToAdd.size(); ++i)
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.getTotalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.getUniqueSize());

        assertEquals(SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(1, sortedDeque.getNumberOfBuckets());

        /////////////////////////////////////////////////////////
        final Integer VALUE_GREATER_THAN_PREVIOUS_VALUES_TO_ADD = 100;
        sortedDeque.insert(VALUE_GREATER_THAN_PREVIOUS_VALUES_TO_ADD);
        assertTrue(sortedDeque.back() == VALUE_GREATER_THAN_PREVIOUS_VALUES_TO_ADD);

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.getTotalSize());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.getUniqueSize());

        assertEquals(2 * SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(2, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testAddingValueWhichShouldBeAddedBetweenTwoFullBuckets()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        List<Integer> valuesToAddToFirstBucket = new ArrayList<>();
        valuesToAddToFirstBucket.add(1);
        valuesToAddToFirstBucket.add(2);
        valuesToAddToFirstBucket.add(3);

        for (Integer value : valuesToAddToFirstBucket)
            sortedDeque.insert(value);

        List<Integer> valuesToAddToSecondBucket = new ArrayList<>();
        valuesToAddToSecondBucket.add(5);
        valuesToAddToSecondBucket.add(6);
        valuesToAddToSecondBucket.add(7);

        for (Integer value : valuesToAddToSecondBucket)
            sortedDeque.insert(value);

        int valueWhichShouldBeAddedBetweenTwoFullBuckets = 4;
        sortedDeque.insert(valueWhichShouldBeAddedBetweenTwoFullBuckets);

        /////////////////////////////////////////////////////////

        final int EXPECTED_TOTAL_NUMBER_OF_ELEMENTS = valuesToAddToFirstBucket.size() + 1 + valuesToAddToSecondBucket.size();
        assertEquals(EXPECTED_TOTAL_NUMBER_OF_ELEMENTS, sortedDeque.getTotalSize());
        assertEquals(EXPECTED_TOTAL_NUMBER_OF_ELEMENTS, sortedDeque.getUniqueSize());

        assertEquals(3 * SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(3, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testAddingValueWhichShouldBeAddedBetweenTwoValuesInFullBucketAfterWhichIsAnotherFullBucket()
    {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Double> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        List<Double> valuesToAddToFirstBucket = new ArrayList<>();
        valuesToAddToFirstBucket.add(1.);
        valuesToAddToFirstBucket.add(2.);
        valuesToAddToFirstBucket.add(3.);

        for (Double value : valuesToAddToFirstBucket)
            sortedDeque.insert(value);

        List<Double> valuesToAddToSecondBucket = new ArrayList<>();
        valuesToAddToSecondBucket.add(5.);
        valuesToAddToSecondBucket.add(6.);
        valuesToAddToSecondBucket.add(7.);

        for (Double value : valuesToAddToSecondBucket)
            sortedDeque.insert(value);

        double valueWhichShouldBeAddedBetweenTwoFullBuckets = 2.78;
        sortedDeque.insert(valueWhichShouldBeAddedBetweenTwoFullBuckets);

        ////////////////////////////////////////////////////////

        List<Double> allValues = new ArrayList<>();

        for (Double value : valuesToAddToFirstBucket)
            allValues.add(value);
        for (Double value : valuesToAddToSecondBucket)
            allValues.add(value);
        allValues.add(valueWhichShouldBeAddedBetweenTwoFullBuckets);
        Collections.sort(allValues);

        for (int i = 0; i < allValues.size(); ++i) {
            assertEquals(allValues.get(i), sortedDeque.get(i));
        }

        /////////////////////////////////////////////////////////

        final int EXPECTED_TOTAL_NUMBER_OF_ELEMENTS = valuesToAddToFirstBucket.size() + 1 + valuesToAddToSecondBucket.size();
        assertEquals(EXPECTED_TOTAL_NUMBER_OF_ELEMENTS, sortedDeque.getTotalSize());
        assertEquals(EXPECTED_TOTAL_NUMBER_OF_ELEMENTS, sortedDeque.getUniqueSize());

        assertEquals(3 * SIZE_OF_BUCKET, sortedDeque.getCapacity());
        assertEquals(3, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testAddingDuplicatedElements()
    {
        SortedDeque<Integer> sortedDeque = new SortedDeque<>();

        List<Integer> valuesToAdd = new ArrayList<>();
        valuesToAdd.add(2);
        valuesToAdd.add(1);
        valuesToAdd.add(9);
        valuesToAdd.add(1);
        valuesToAdd.add(5);
        valuesToAdd.add(2);
        valuesToAdd.add(9);

        for (Integer value : valuesToAdd)
            sortedDeque.insert(value);

        Collections.sort(valuesToAdd);

        List<Integer> uniqueValues = new ArrayList<>();
        uniqueValues.add(2);
        uniqueValues.add(1);
        uniqueValues.add(9);
        uniqueValues.add(5);

        Collections.sort(uniqueValues);

        for (int i = 0; i < uniqueValues.size(); ++i)
            assertEquals(uniqueValues.get(i), sortedDeque.get(i));

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.getTotalSize());
        assertEquals(uniqueValues.size(), sortedDeque.getUniqueSize());

        assertEquals(1, sortedDeque.getNumberOfBuckets());
    }

    @Test
    public void testIterator()
    {
        SortedDeque<Float> sortedDeque = new SortedDeque<>();

        List<Float> valuesToAdd = new ArrayList<>();
        valuesToAdd.add(1.11f);
        valuesToAdd.add(2.22f);
        valuesToAdd.add(3.33f);
        valuesToAdd.add(4.44f);
        valuesToAdd.add(5.55f);

        for (Float value : valuesToAdd)
            sortedDeque.insert(value);

        Collections.sort(valuesToAdd);

        Iterator listIterartor = valuesToAdd.iterator();
        Iterator sortedDequeIterator = sortedDeque.iterator();
        while (listIterartor.hasNext())
            assertEquals(listIterartor.next(), sortedDequeIterator.next());
    }
}
