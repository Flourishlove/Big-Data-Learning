/* Definition of BaseGFSClient
 * class BaseGFSClient {
 *     private Map<String, String> chunk_list;
 *     public BaseGFSClient() {}
 *     public String readChunk(String filename, int chunkIndex) {
 *         // Read a chunk from GFS
 *     }
 *     public void writeChunk(String filename, int chunkIndex,
 *                            String content) {
 *         // Write a chunk to GFS
 *     }
 * }
 */


public class GFSClient extends BaseGFSClient {
    /*
    * @param chunkSize: An integer
    */
    private int chunkSize;
    private Map<String, Integer> chunkNum;

    public GFSClient(int chunkSize) {
        this.chunkSize = chunkSize;
        this.chunkNum = new HashMap<>();
    }

    /*
     * @param filename: a file name
     * @return: conetent of the file given from GFS
     */
    public String read(String filename) {
        if(!chunkNum.containsKey(filename)) return null;

        StringBuffer content = new StringBuffer();
        for(int i = 0; i < chunkNum.get(filename); i++){
            content.append(readChunk(filename, i));
        }
        return content.toString();
    }

    /*
     * @param filename: a file name
     * @param content: a string
     * @return: nothing
     */
    public void write(String filename, String content) {
        int len = content.length();

        int num = (len-1) / chunkSize + 1;
        chunkNum.put(filename, num);
        for(int i = 0; i < num; i++){
            int start = i * chunkSize;
            int end = i == num - 1 ? len : (i+1) * chunkSize;
            writeChunk(filename, i, content.substring(start, end));
        }
    }
}