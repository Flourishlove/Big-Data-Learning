/**
 * Definition of OutputCollector:
 * class OutputCollector<K, V> {
 *     public void collect(K key, V value);
 *         // Adds a key/value pair to the output buffer
 * }
 */
class Tuple{
    int row;
    int col;
    int val;
    public Tuple(int row, int col, int val){
        this.row = row;
        this.col = col;
        this.val = val;
    }
}

public class SortIntegers {

    public static class Map {
        public void map(int _, List<Integer> value,
                        OutputCollector<String, List<Integer>> output) {
            Collections.sort(value);
            output.collect("ignore_key", value);
        }
    }

    public static class Reduce {
        public void reduce(String key, List<List<Integer>> values,
                           OutputCollector<String, List<Integer>> output) {
            List<Integer> result = new ArrayList<>();
            if(values.size() == 0){
                output.collect(key, result);
                return;
            }

            int k = values.size();
            PriorityQueue<Tuple> Q = new PriorityQueue<Tuple>(k, new Comparator<Tuple>(){
                public int compare(Tuple a, Tuple b){
                    return a.val - b.val;
                }
            });

            for(int i = 0; i < k; i++){
                if(values.get(i).size() > 0){
                    Tuple temp = new Tuple(i, 0, values.get(i).get(0));
                    Q.add(temp);
                }
            }

            while(!Q.isEmpty()){
                Tuple temp = Q.poll();
                result.add(temp.val);
                if(values.get(temp.row).size() > temp.col+1){
                    Tuple next = new Tuple(temp.row, temp.col+1, values.get(temp.row).get(temp.col+1));
                    Q.add(next);
                }
            }
            output.collect(key, result);
        }
    }
}