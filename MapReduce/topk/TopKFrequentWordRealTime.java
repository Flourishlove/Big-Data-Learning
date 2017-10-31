public class TopK {
    /*
    * @param k: An integer
    */
    private int k;
    private Map<String, Integer> map;
    private NavigableSet<String> topK;
    private Comparator<String> com;

    public TopK(int k) {
        this.k = k;
        this.com = new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                int first = map.get(a);
                int second = map.get(b);
                if(first != second) return second - first;
                else return a.compareTo(b);
            }
        };
        this.map = new HashMap<>();
        this.topK = new TreeSet<>(com);
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        if (map.containsKey(word)) {
            if (topK.contains(word))
                topK.remove(word);
            map.put(word, map.get(word) + 1);
        } else {
            map.put(word, 1);
        }
        // map.put(word, map.getOrDefault(word, 0)+1);
        // if(topK.contains(word)) topK.remove(word);

        topK.add(word);
        if(topK.size() > k) topK.pollLast();
    }

    /*
     * @return: the current top k frequent words.
     */
    public List<String> topk() {
        List<String> result = new ArrayList<>();
        Iterator<String> iter = topK.iterator();
        while(iter.hasNext()) result.add(iter.next());
        return result;
    }
}