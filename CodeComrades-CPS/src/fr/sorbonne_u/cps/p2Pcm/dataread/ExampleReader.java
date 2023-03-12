package fr.sorbonne_u.cps.p2Pcm.dataread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//------------------------------------------------------------------------------

/**
 * The class <code>ExampleReader</code> shows how to read data from files in
 * the peer-to-peer content management project.
 *
 * <p><strong>Description</strong></p>
 *
 * <p>
 * Data files must be stored in a directory which full name must be put in
 * the variable {@code ContentDataManager.DATA_DIR_NAME} prior to the readings.
 * The first constructor, without argument, shows how to read templates to be
 * used in facade components to make requests. The second argument, with an
 * integer argument, shows how to read descriptors in peer components. Note
 * that in this example, not processing of the data is done. In the project,
 * the read data must be converted into instances of descriptors and templates
 * before being used in the components.
 * </p>
 *
 * <p><strong>Invariant</strong></p>
 *
 * <pre>
 * invariant	{@code true}
 * </pre>
 *
 * <p>Created on : 2023-01-05</p>
 *
 * @author    <a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public class ExampleReader {
    /**
     * full name of the directory containing the data files.
     */
    public static final String MY_DATA_DIR_NAME = "/Users/jmalenfant/testsDataCPS";
    /**
     * when true, some actions are traced on the terminal.
     */
    protected static final boolean DEBUG = true;
    /**
     * templates read from a file.
     */
    protected ArrayList<HashMap<String, Object>> readTemplates;
    /**
     * descriptors read from a file.
     */
    protected ArrayList<HashMap<String, Object>> readDescriptors;

    /**
     * shows how to read templates from a template file for a facade with
     * number 0.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @throws ClassNotFoundException <i>to do</i>.
     * @throws IOException            <i>to do</i>.
     */
    public ExampleReader()
            throws ClassNotFoundException, IOException {
        this.readTemplates = ContentDataManager.readTemplates(0);
        // the next instruction must be replaced by the processing of templates
        // to create template instances and use them in requests.
        for (HashMap<String, Object> h : this.readTemplates) {
            System.out.println(ContentDataManager.toString(h));
        }
    }

    /**
     * shows how to read descriptors from a descriptor file for a peer with
     * the given number.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code number >= 0}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @throws IOException            <i>to do</i>.
     * @throws ClassNotFoundException <i>to do</i>.
     */
    public ExampleReader(int number) throws ClassNotFoundException, IOException {
        assert number >= 0;
        this.readDescriptors = ContentDataManager.readDescriptors(number);
        // the next instruction must be replaced by the processing of
        // descriptors to create descriptor instances and store them in peers.
        System.out.println("****************************");
        for (HashMap<String, Object> h : this.readDescriptors) {
            System.out.println(ContentDataManager.toString(h));
        }
        System.out.println("****************************");
    }

    /**
     * example showing how to access data in a hash map representing either a
     * template or a descriptor.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     */
    public void exampleProcessing() {
        for (HashMap<String, Object> h : this.readTemplates) {
            System.out.print("Next template: ");
            System.out.print(h.get(ContentDataManager.TITLE_KEY) + ", ");
            System.out.print(h.get(ContentDataManager.ALBUM_TITLE_KEY) + ", ");
            System.out.print(h.get(ContentDataManager.COMPOSERS_KEY) + ", ");
            System.out.print(h.get(ContentDataManager.INTERPRETERS_KEY) + ", ");
            System.out.println(h.get(ContentDataManager.SIZE_KEY) + ", ");
        }
    }

    /**
     * tests.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        try {
            ContentDataManager.DATA_DIR_NAME = MY_DATA_DIR_NAME;
            for (int number = 1; number < 10; number++) {
                new ExampleReader(number);
            }
            System.out.println("------------------------------");
            ExampleReader mockup = new ExampleReader();
            mockup.exampleProcessing();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
//------------------------------------------------------------------------------
