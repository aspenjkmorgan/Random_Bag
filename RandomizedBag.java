/******************************************************************************
 *  Compilation:  javac RandomizedBag.java
 *  Execution:    java RandomizedBag < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Stack implementation with a resizing array.
 *
 *  % java ResizingArrayStack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


public class RandomizedBag<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on stack
    Random rng;

    // Initializes an empty stack
    public RandomizedBag() {
        a = (Item[]) new Object[2];
        n = 0;
        rng = new Random();
    }

    // Is this stack empty?
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in the stack.
     */
    public int size() {
        return n;
    }


    /*
     * Resize the underlying array holding the elements
     */
    private void resize(int capacity) {
        assert capacity >= n;

        // copy stack over to bigger size stack
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }


    /**
     * Adds the item to this bag (which is an array).
     */
    public void put(Item item) {
        // double size of array if necessary
        if (n == a.length) resize(2 * a.length);

        // add to the end, removing randomly will make this a randomized bag
        a[n++] = item;
    }

    /**
     * Removes and returns a random item from the bag
     */
    public Item get() {
        int randomIndex = rng.nextInt(n);
        Item item = a[randomIndex];

        // move null item to end of array
        Item buf = a[n - 1];
        a[randomIndex] = buf;
        a[n - 1] = null;
        n--;

        // shrink if needed
        if (n > 0 && n == a.length / 4) resize(a.length / 2);

        return item;
    }


    /**
     * Returns an iterator to this bag that iterates through the items in random order.
     */
    public Iterator<Item> iterator() {
        return new RandomizedBagIterator();
    }

    // an iterator; ours doesn't implement remove() since it's optional
    private class RandomizedBagIterator implements Iterator<Item> {
        private int i;
        private Item itArr[];

        public RandomizedBagIterator() {
            /* do the work here to support
             *    (i) multiple independent iterators (i.e. each one initializes its own itArr);
             *        - in this constructor, this can take time linear in the size of the bag
             *    (ii) constant time next() and hasnext() calls.
             */

            // Copies current state of array to the itArr array
            itArr = (Item[]) new Object[n];

            // Copy array
            for (int k = 0; k < n; k++) {
                itArr[k] = a[k];
            }

            // Put itArr in random order, doesn't allow repeats
            for (int j = itArr.length - 1; j > 0; j--) {
                int index = rng.nextInt(j + 1);
                Item buf = itArr[index];
                itArr[index] = itArr[j];
                itArr[j] = buf;
            }

            // Start at i = n - 1
            i = n - 1;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return itArr[i--];
        }
    }


    /**
     * Unit tests the RandomizeBag data type.
     */
    public static void main(String[] args) {
        RandomizedBag<String> bag = new RandomizedBag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) bag.put(item);
            else if (!bag.isEmpty()) StdOut.print(bag.get() + " ");
        }
        StdOut.println("(" + bag.size() + " left on bag)");


        Iterator<String> itr1 = bag.iterator();
        if (!bag.isEmpty())
            bag.get(); // test removal of one
        Iterator<String> itr2 = bag.iterator();

        StdOut.println("Here's what was left before removing one (in random order):");
        while (itr1.hasNext()) {
            String s = itr1.next();
            StdOut.println(s + " ");
        }
        StdOut.println("");

        StdOut.println("Here's what was left after removing one (in random order):");
        while (itr2.hasNext()) {
            String s = itr2.next();
            StdOut.println(s + " ");
        }
        StdOut.println("");

        StdOut.println(
                "I sure hope the second one is missing one entry, and in a different order.");


    }


}
