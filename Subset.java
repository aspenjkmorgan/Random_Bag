public class Subset {


    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        // fill the bag with words from StdIn;  example of doing this in e.g. LinkedQueue
        RandomizedBag<String> bag = new RandomizedBag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.put(item);
        }
        StdOut.println("(" + bag.size() + " left on queue)");


        // pull k things from the bag, if possible.
        if (k > bag.size()) throw new IllegalArgumentException(
                "There are not enough items in the bag for that operation.");
        for (int j = 0; j < k; j++) {
            StdOut.println(bag.get());
        }

    }


}
