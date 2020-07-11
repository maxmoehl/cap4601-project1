
import java.util.ArrayList;



/**
 *
 * @author treichherzer
 */
public class DocumentVectorModel {
    
    private ArrayList<Float> _weights;
            
    public DocumentVectorModel (DocumentModel documentModel, 
                                Dictionary dictionary,
                                InvertedIndex invertedIndex) {
        // build document vector using appropriate size
        
        // compute word frequency for all words in document model
        
        // compute weights for words
        
    }
    
    public static double cosineSimilarity (DocumentVectorModel d1, DocumentVectorModel d2) 
    {
        // implement this
        return 0.0;
    }
   
}
