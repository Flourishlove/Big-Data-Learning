public class Solution {
    /*
     * @param words: an array of string
     * @param k: An integer
     * @return: an array of string
     */
    public String[] topKFrequentWords(String[] words, int k) {
        if(k <= 0) return new String[0];

        Map<String, Integer> map = new HashMap<>();
        Comparator<Tuple> com = new Comparator<Tuple>(){
            @Override
            public int compare(Tuple a, Tuple b){
                if(a.val != b.val) return a.val - b.val;
                else{
                    return b.key.compareTo(a.key);
                }
            }
        };

        PriorityQueue<Tuple> Q = new PriorityQueue<Tuple>(k, com);

        for(String s : words) map.put(s, map.getOrDefault(s, 0)+1);

        for(String s : map.keySet()){
            Tuple temp = new Tuple(s, map.get(s));
            if(Q.size() < k) Q.add(temp);
            else{
                if(com.compare(temp, Q.peek()) > 0){
                    Q.poll();
                    Q.add(temp);
                }
            }
        }

        List<String> list = new ArrayList<>();
        while(!Q.isEmpty()) list.add(Q.poll().key);

        String[] result = new String[list.size()];
        for(int i = list.size()-1; i >= 0; i--) result[list.size()-i-1] = list.get(i);
        return result;
    }
}

class Tuple{
    String key;
    int val;
    public Tuple(String key, int val){
        this.key = key;
        this.val = val;
    }
}