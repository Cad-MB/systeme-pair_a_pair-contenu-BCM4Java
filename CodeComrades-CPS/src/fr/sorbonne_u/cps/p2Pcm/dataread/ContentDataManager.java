package fr.sorbonne_u.cps.p2Pcm.dataread;

// Copyright Jacques Malenfant, Sorbonne Universite.
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to provide a
// basic component programming model to program with components
// distributed applications in the Java programming language.
//
// This software is governed by the CeCILL-C license under French law and
// abiding by the rules of distribution of free software.  You can use,
// modify and/ or redistribute the software under the terms of the
// CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
// URL "http://www.cecill.info".
//
// As a counterpart to the access to the source code and  rights to copy,
// modify and redistribute granted by the license, users are provided only
// with a limited warranty  and the software's author,  the holder of the
// economic rights,  and the successive licensors  have only  limited
// liability. 
//
// In this respect, the user's attention is drawn to the risks associated
// with loading,  using,  modifying and/or developing or reproducing the
// software by the user in light of its specific status of free software,
// that may mean  that it is complicated to manipulate,  and  that  also
// therefore means  that it is reserved for developers  and  experienced
// professionals having in-depth computer knowledge. Users are therefore
// encouraged to load and test the software's suitability as regards their
// requirements in conditions enabling the security of their systems and/or 
// data to be ensured and,  more generally, to use and operate it in the 
// same conditions as regards security. 
//
// The fact that you are presently reading this means that you have had
// knowledge of the CeCILL-C license and that you accept its terms.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// -----------------------------------------------------------------------------

/**
 * The class <code>ContentDataManager</code> defines tools to manage data files
 * in the peer-to-peer content management project.
 *
 * <p><strong>Description</strong></p>
 *
 * <p>
 * Data in the project consist in descriptors stored in peer components and
 * templates used in facade components to make requests. Test data are provided
 * in files to be put in a directory which full name must be put in the variable
 * {@code DATA_DIR_NAME}.
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
public abstract class ContentDataManager {
    /**
     * full name of the directory containing the data files.
     */
    public static String DATA_DIR_NAME = "data";
    /**
     * descriptor files have a name beginning with this prefix followed
     * by a number.
     */
    public static final String DESCRIPTORS_FILE_NAME_PREFIX = "descriptors";
    /**
     * template files have a name beginning with this prefix followed
     * by a number.
     */
    public static final String TEMPLATES_FILE_NAME_PREFIX = "templates";
    /**
     * data files all have this extension.
     */
    public static final String FILE_NAME_EXTENSION = "dat";

    /**
     * when true, some actions are traced on the terminal.
     */
    protected static final boolean DEBUG = false;
    /**
     * sentinel data marking the end of data files.
     */
    protected static final HashMap<String, Object> EOF;
    /**
     * key used to mark the EOF hash map as the EOF sentinel.
     */
    protected static final String EOFKey = "eof";

    /**
     * key used to store the title of the song in the hash map.
     */
    public static final String TITLE_KEY = "title";
    /**
     * key used to store the album title of the song in the hash map.
     */
    public static final String ALBUM_TITLE_KEY = "album-title";
    /**
     * key used to store the interpreters of the song in the hash map.
     */
    public static final String INTERPRETERS_KEY = "interpreters";
    /**
     * key used to store the composers of the song in the hash map.
     */
    public static final String COMPOSERS_KEY = "composers";
    /**
     * key used to store the size of the song file  in the hash map.
     */
    public static final String SIZE_KEY = "size";

    // -------------------------------------------------------------------------
    // Static initialisations of the EOF sentinel for data files.
    // -------------------------------------------------------------------------

    static {
        EOF = new HashMap<String, Object>();
        EOF.put(EOFKey, 0);
    }

    // -------------------------------------------------------------------------
    // Static methods.
    // -------------------------------------------------------------------------

    /**
     * create a hash map containing all of the known the information about the
     * song file.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param title        title of the song.
     * @param albumTitle   title of the album.
     * @param interpreters set of interpreters (as an {@code ArrayList}).
     * @param composers    set of composers (as an {@code ArrayList}).
     * @param size         size of the file (in kilobytes).
     * @return a hash map containing all of the known the information about the song file.
     */
    public static HashMap<String, Object> create(
            String title,
            String albumTitle,
            ArrayList<String> interpreters,
            ArrayList<String> composers,
            long size
    ) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        if (title != null) {
            ret.put(TITLE_KEY, title);
        }
        if (albumTitle != null) {
            ret.put(ALBUM_TITLE_KEY, albumTitle);
        }
        if (interpreters != null) {
            ret.put(INTERPRETERS_KEY, interpreters);
        }
        if (composers != null) {
            ret.put(COMPOSERS_KEY, composers);
        }
        if (size > 0) {
            ret.put(SIZE_KEY, size);
        }
        return ret;
    }

    /**
     * create a string representation of {@code d} for debugging purposes
     * essentially.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code true}	// no precondition.
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param d a hash map containing all of the known the information about the song file.
     * @return a string representation of {@code d}.
     */
    public static String toString(HashMap<String, Object> d) {
        StringBuffer sb = new StringBuffer("[");
        boolean nonEmpty = false;
        boolean isDescriptor = true;
        if (d.containsKey(TITLE_KEY)) {
            sb.append("title = \"");
            sb.append(d.get(TITLE_KEY));
            sb.append('\"');
            nonEmpty = true;
        } else {
            isDescriptor = false;
        }
        if (d.containsKey(ALBUM_TITLE_KEY)) {
            if (nonEmpty) sb.append(", ");
            sb.append("album title = \"");
            sb.append(d.get(ALBUM_TITLE_KEY));
            sb.append('\"');
            nonEmpty = true;
        } else {
            isDescriptor = false;
        }
        if (d.containsKey(INTERPRETERS_KEY)) {
            if (nonEmpty) sb.append(", ");
            sb.append("interpreters = {");
            @SuppressWarnings("unchecked")
            Iterator<String> it =
                    ((ArrayList<String>) d.get(INTERPRETERS_KEY)).iterator();
            while (it.hasNext()) {
                sb.append('\"');
                sb.append(it.next());
                sb.append('\"');
                if (it.hasNext()) sb.append(", ");
            }
            sb.append("}");
            nonEmpty = true;
        } else {
            isDescriptor = false;
        }
        if (d.containsKey(COMPOSERS_KEY)) {
            if (nonEmpty) sb.append(", ");
            sb.append("composers = {");
            @SuppressWarnings("unchecked")
            Iterator<String> it =
                    ((ArrayList<String>) d.get(COMPOSERS_KEY)).iterator();
            while (it.hasNext()) {
                sb.append('\"');
                sb.append(it.next());
                sb.append('\"');
                if (it.hasNext()) sb.append(", ");
            }
            sb.append("}");
            nonEmpty = true;
        } else {
            isDescriptor = false;
        }
        if (d.containsKey(SIZE_KEY)) {
            if (nonEmpty) sb.append(", ");
            sb.append("size = ");
            sb.append(d.get(SIZE_KEY));
        }
        sb.append(']');

        return (isDescriptor ? "Descriptor" : "Request") + sb.toString();
    }

    /**
     * return {@code true} if {@code entry} is the sentinel marking the end of
     * the input (file).
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code entry != null && !entry.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param entry entry read from the input file.
     * @return        {@code true} if {@code entry} is the sentinel marking the end of the input.
     */
    protected static boolean isEOF(HashMap<String, Object> entry) {
        assert entry != null && !entry.isEmpty();
        return entry.containsKey(EOFKey);
    }

    /**
     * create a unique file name for descriptors corresponding to the given
     * number.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code number >= 0}
     * pre	{@code ContentDataManager.DATA_DIR_NAME != null && !ContentDataManager.DATA_DIR_NAME.isEmpty()}
     * post	{@code ret != null && !ret.isEmpty()}
     * </pre>
     *
     * @param number number associated with the file (and the user).
     * @return a unique file name corresponding to the given number.
     */
    public static String createDescriptorsFileName(int number) {
        assert number >= 0;
        assert ContentDataManager.DATA_DIR_NAME != null &&
                !ContentDataManager.DATA_DIR_NAME.isEmpty();

        return DATA_DIR_NAME + File.separator + DESCRIPTORS_FILE_NAME_PREFIX
                + number + "." + FILE_NAME_EXTENSION;
    }

    /**
     * create a unique file name for templates corresponding to the given
     * number.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code number >= 0}
     * pre	{@code ContentDataManager.DATA_DIR_NAME != null && !ContentDataManager.DATA_DIR_NAME.isEmpty()}
     * post	{@code ret != null && !ret.isEmpty()}
     * </pre>
     *
     * @param number number associated with the file (and the user).
     * @return a unique file name corresponding to the given number.
     */
    public static String createTemplatesFileName(int number) {
        assert number >= 0;
        assert ContentDataManager.DATA_DIR_NAME != null &&
                !ContentDataManager.DATA_DIR_NAME.isEmpty();

        return DATA_DIR_NAME + File.separator + TEMPLATES_FILE_NAME_PREFIX
                + number + "." + FILE_NAME_EXTENSION;
    }

    /**
     * read content (templates or descriptors) from the given file.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code fileName != null && !fileName.isEmpty()}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param fileName (full) name of the file from which to read content.
     * @throws IOException            <i>to do</i>.
     * @throws ClassNotFoundException <i>to do</i>.
     * @return an array list of hash maps representing the content of the file.
     */
    @SuppressWarnings("unchecked")
    protected static ArrayList<HashMap<String, Object>> readContent(
            String fileName
    ) throws IOException, ClassNotFoundException {
        assert fileName != null && !fileName.isEmpty();

        ArrayList<HashMap<String, Object>> ret =
                new ArrayList<HashMap<String, Object>>();

        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(file);
        HashMap<String, Object> entry =
                (HashMap<String, Object>) ois.readObject();
        while (!isEOF(entry)) {
            ret.add(entry);
            if (DEBUG) {
                System.out.println(entry);
            }
            entry = (HashMap<String, Object>) ois.readObject();
        }
        ois.close();

        return ret;
    }

    /**
     * writing the given content in a file with the given name.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code fileName != null && !fileName.isEmpty()}
     * pre	{@code descriptors != null}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param fileName    (full) name of the file into which the content must be written.
     * @param descriptors an array list of hash maps representing the content to write into the file.
     * @throws IOException <i>to do</i>.
     */
    public static void writeContent(
            String fileName,
            ArrayList<HashMap<String, Object>> descriptors
    ) throws IOException {
        assert fileName != null && !fileName.isEmpty();
        assert descriptors != null;

        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(file);
        for (HashMap<String, Object> entry : descriptors) {
            oos.writeObject(entry);
        }
        oos.writeObject(EOF);
        oos.close();
    }

    /**
     * reading descriptors from the data file with the given number.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code number >= 0}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param number number associated with the component calling for the reading.
     * @throws ClassNotFoundException <i>to do</i>.
     * @throws IOException            <i>to do</i>.
     * @return an array list of the descriptors read from the file.
     */
    public static ArrayList<HashMap<String, Object>> readDescriptors(
            int number
    ) throws ClassNotFoundException, IOException {
        assert number >= 0;
        ArrayList<HashMap<String, Object>> ret;
        String filename = ContentDataManager.createDescriptorsFileName(number);
        ret = ContentDataManager.readContent(filename);
        return ret;
    }

    /**
     * reading templates from the data file with the given number.
     *
     * <p><strong>Contract</strong></p>
     *
     * <pre>
     * pre	{@code number >= 0}
     * post	{@code true}	// no postcondition.
     * </pre>
     *
     * @param number number associated with the component calling for the reading.
     * @throws ClassNotFoundException <i>to do</i>.
     * @throws IOException            <i>to do</i>.
     * @return an array list of the descriptors read from the file.
     */
    public static ArrayList<HashMap<String, Object>> readTemplates(
            int number
    ) throws ClassNotFoundException, IOException {
        assert number >= 0;
        ArrayList<HashMap<String, Object>> ret;
        String filename = ContentDataManager.createTemplatesFileName(number);
        ret = ContentDataManager.readContent(filename);
        return ret;
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
            DATA_DIR_NAME = "testsDataCPS";
            int number = 1;
            String fileName =
                    ContentDataManager.createDescriptorsFileName(number);
            ArrayList<HashMap<String, Object>> descriptors =
                    new ArrayList<HashMap<String, Object>>();

            HashMap<String, Object> m;
            ArrayList<String> interpreters = new ArrayList<String>();
            interpreters.add("The Academy of Ancient Music");
            ArrayList<String> composers = new ArrayList<String>();
            composers.add("Antonio Vivaldi");
            m = ContentDataManager.create("Winter", "The Four Seasons",
                    interpreters, composers, 5000);
            descriptors.add(m);
            interpreters = new ArrayList<String>();
            interpreters.add("The Academy of Ancient Music");
            composers = new ArrayList<String>();
            composers.add("Antonio Vivaldi");
            m = ContentDataManager.create("Summer", "The Four Seasons",
                    interpreters, composers, 4500);
            descriptors.add(m);
            ContentDataManager.writeContent(fileName, descriptors);

            fileName = ContentDataManager.createTemplatesFileName(number);
            ArrayList<HashMap<String, Object>> templates =
                    new ArrayList<HashMap<String, Object>>();
            composers = new ArrayList<String>();
            composers.add("Antonio Vivaldi");
            m = ContentDataManager.create(null, null, null, composers, 0);
            templates.add(m);
            ContentDataManager.writeContent(fileName, templates);

            ArrayList<HashMap<String, Object>> readDescriptors =
                    ContentDataManager.readDescriptors(number);
            System.out.println(readDescriptors);
            ArrayList<HashMap<String, Object>> readTemplates =
                    ContentDataManager.readTemplates(number);
            System.out.println(readTemplates);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
// -----------------------------------------------------------------------------
