/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
public class NGram {

    public static class Map {
        public void map(String _, int n, String str,
                        OutputCollector<String, Integer> output) {
            for(int i = 0; i < str.length() - n + 1; i++){
                output.collect(str.substring(i, i+n), 1);
            }

        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, Integer> output) {
            int sum = 0;
            while(values.hasNext()){
                sum += values.next();
            }
            output.collect(key, sum);
        }
    }
}