/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
public class Anagram {

    public static class Map {
        public void map(String key, String value,
                        OutputCollector<String, String> output) {
            //Map<String, List<String>> map = new HashMap<>();
            StringTokenizer tokenizer = new StringTokenizer(value);
            while(tokenizer.hasMoreTokens()){
                String word = tokenizer.nextToken();
                char[] ch = word.toCharArray();
                Arrays.sort(ch);
                String k = String.valueOf(ch);
                output.collect(k, word);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<String> values,
                           OutputCollector<String, List<String>> output) {
            List<String> result = new ArrayList<>();
            while(values.hasNext()) result.add(values.next());

            output.collect(key, result);
        }
    }
}