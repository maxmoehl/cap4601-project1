
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author treichherzer
 */
public class InvertedIndex {
    private HashMap<String,ArrayList<DocumentModel>> _indexTable;
    
    public InvertedIndex()
    {
        _indexTable = new HashMap<>();
    }
    
    public void addRelation(String word, DocumentModel documentModel) {
        // implement this
    }
    
    public int getDocumentFrequency (String word) {
        // implement this
        return 0;
    }
}
