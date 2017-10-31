/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 * Definition of Document:
 * class Document {
 *     public int id;
 *     public String content;
 * }
 */

class Tuple{
    String key;
    int val;
    public Tuple(String key, int value){
        this.key = key;
        this.val = value;
    }
}

public class TopKFrequentWords {

    public static class Map {
        public void map(String _, Document value,
                        OutputCollector<String, Integer> output) {
            int id = value.id;
            String content = value.content.trim();
            String[] tokens = content.split("\\s+");
            for(int i = 0; i < tokens.length; i++){
                output.collect(tokens[i], 1);
            }
        }
    }

    public static class Reduce {
        private int k;
        private PriorityQueue<Tuple> qu;
        private Comparator<Tuple> com = new Comparator<Tuple>(){
                @Override
                public int compare(Tuple a, Tuple b){
                    if(a.val != b.val) return a.val - b.val;
                    else return b.key.compareTo(a.key);
                }
            };

        public void setup(int k) {
            this.k = k;
            this.qu = new PriorityQueue<Tuple>(k, com);
        }

        public void reduce(String key, Iterator<Integer> values) {
            int sum = 0;
            while(values.hasNext()){
                sum += values.next();
            }

            Tuple temp = new Tuple(key, sum);
            if(qu.size() < k) qu.add(temp);
            else{
                if(com.compare(temp, qu.peek()) > 0){
                    qu.poll();
                    qu.add(temp);
                }
            }
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            List<Tuple> list = new ArrayList<>();
            while(!qu.isEmpty()){
                list.add(qu.poll());
            }

            for(int i = list.size()-1; i >= 0; i--) output.collect(list.get(i).key, list.get(i).val);
        }
    }
}