
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * This prog
 * @author treichherzer
 */
public class IRTester {
    
    /**
     * The path to a directory containing several documents to be processed.
     */
    private final String _sourceDir;
    
    private ArrayList<DocumentModel>       _documentModels;
    private ArrayList<DocumentVectorModel> _documentVectorModels;
    private Dictionary                     _dictionary;
    private InvertedIndex                  _invertedIndex;
    
    /**
     * Constructs an IRTester.
     * 
     * @param sourceDir - the directory that contains text files to be processed
     */
    public IRTester(String sourceDir) {
        _sourceDir = sourceDir;
        
        _documentModels = new ArrayList<>();
        _documentVectorModels = new ArrayList<>();
        _dictionary = new Dictionary();
        _invertedIndex = new InvertedIndex();
    }
    
    /**
     * Runs the IR tester program.
     */
    public void run()
    {
        // get list of file names
        ArrayList<String> filenames = getTextFileList ();
        
        // read documents and create a list of documents
        readDocuments (filenames);
        
        // process documents word by word to build dictionary
        buildDocumentVectorModels();
        
        // build document-document matrix
        
        // print document-document matrix
    }
    
    
    
    public static void main(String args[])
    {
        //
        if (args.length != 1) {
            System.out.println ("usage IRTester <pathDir>");
            return;
        }
        
        // run tester
        new IRTester(args[0]).run();
        
        
    }
    
    /**
     * Returns a list of file names with extension .txt.
     * 
     * @return a list of file names
     */
    private ArrayList<String> getTextFileList()
    {
        File folder = new File (_sourceDir);
        
        ArrayList filenames = new ArrayList();
        
        File[] fileList = folder.listFiles (new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".txt");
            }
            });
        
        // check if any files were found
        if (fileList == null) {
            return filenames; 
        }
        
        for (File file : fileList)
        {
            filenames.add(_sourceDir + File.separator + file.getName());
        }
        
        return filenames;
    }

    /**
     * Reads documents given a list of files. An error message is printed to 
     * standard error for documents not successfully read.
     * 
     * @param filenames - the list of filenames of text files
     * 
     */
    private void readDocuments(ArrayList<String> filenames) {
        for (int i=0; i < filenames.size(); i++) {
            try {
                DocumentModel documentModel = new DocumentModel(filenames.get(i));
                _documentModels.add(documentModel);
            }
            catch (Exception xcp) {
                System.err.println ("unable to read file " + filenames.get(i));
            }
        }
    }

    private void buildDocumentVectorModels() {
        // implement this

    }
    
    
}
