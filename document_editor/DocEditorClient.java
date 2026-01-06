package document_editor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point and classes for a simple document editor supporting text and images.
 * Demonstrates use of interfaces, composition, and persistence strategies.
 */

/**
 * Represents an element in a document, such as text or image.
 */
interface DocumentElement {
    /**
     * Renders the content of the element as String.
     */
    String render();
}

/**
 * A document element representing plain text.
 */
class TextDocumentElement implements DocumentElement {

    private String text;

    /**
     * Constructs a text element.
     * @param text The text to store.
     */
    public TextDocumentElement(String text) {
        this.text = text;
    }

    /**
     * Renders the text as String.
     */
    @Override
    public String render() {
        return text;
    }

}

/**
 * A document element representing an image.
 */
class ImageDocumentElement implements DocumentElement {

    private String imagePath;

    /**
     * Constructs an image element referencing the given path.
     * @param imagePath The path to the image file.
     */
    public ImageDocumentElement(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Renders the image as a placeholder string.
     */
    @Override
    public String render() {
        return "[Image: " + imagePath + "]";
    }

}

/**
 * A document element representing a new line.
 */
class SpaceDocumentElement implements DocumentElement {

    /**
     * Renders the element as a new line character.
     */
    @Override
    public String render() {
        return "\n";
    }

}

/**
 * A document element representing a tab character.
 */
class TabDocumentElement implements DocumentElement {

    /**
     * Renders the element as a tab character.
     */
    @Override
    public String render() {
        return "\t";
    }

}

/**
 * Defines a strategy for persisting rendered document content.
 */
interface Persistance {
    /**
     * Saves the given document content to some backing store.
     * @param content The document's content.
     */
    void save(String content);
}

/**
 * Persists document content by saving to a file named document.txt.
 */
class FilePersistance implements Persistance {

    /**
     * Saves content to document.txt file.
     */
    @Override
    public void save(String content) {
        try {
            FileWriter outFile = new FileWriter("document.txt");
            outFile.write(content);
            outFile.close();
            System.out.println("Document saved to document.txt");
        } catch (IOException e) {
            System.out.println("Error: Unable to open file for writing.");
        }
    }

}

/**
 * Mock persistence strategy for storing content in a database (logic not implemented in this demo).
 */
class DBPersistance implements Persistance {

    /**
     * Prints a stub DB save message.
     */
    @Override
    public void save(String content) {
        System.out.println("Saving the dat to DB logic");
    }

}

/**
 * Represents a document composed of various document elements.
 */
class Document {
    private ArrayList<DocumentElement> elements;

    /**
     * Constructs an empty document.
     */
    public Document() {
        elements = new ArrayList<>();
    }

    /**
     * Adds an element to the document.
     * @param element The element to add.
     */
    public void addElement(DocumentElement element) {
        elements.add(element);
    }

    /**
     * Returns all the elements of the document.
     */
    public ArrayList<DocumentElement> getElements() {
        return elements;
    }

    /**
     * Renders the entire document as a single string.
     */
    public String render() {
        StringBuilder result = new StringBuilder();
        for (DocumentElement element : elements) {
            result.append(element.render());
        }
        return result.toString();
    }

}

/**
 * Main API for editing a document (adding elements, rendering, and saving).
 */
class DocumentEditor {
    private Document document;
    private Persistance storage;

    /**
     * Constructs a document editor using the given Document and Persistance strategy.
     * @param document Document to be edited.
     * @param persistance Persistence strategy (file/db).
     */
    public DocumentEditor(Document document, Persistance persistance) {
        this.document = document;
        this.storage = persistance;
    }

    /**
     * Adds a text element to the document.
     * @param text The text to add.
     */
    public void addText(String text) {
        document.addElement(new TextDocumentElement(text));
    }

    /**
     * Adds an image element to the document.
     * @param imagePath The path of the image to add.
     */
    public void addImage(String imagePath) {
        document.addElement(new ImageDocumentElement(imagePath));
    }

    /**
     * Adds a new line to the document.
     */
    public void addNewLine() {
        document.addElement(new SpaceDocumentElement());
    }

    /**
     * Adds a tab character to the document.
     */
    public void addNewTab() {
        document.addElement(new TabDocumentElement());
    }

    /**
     * Renders the entire document as a String.
     */
    public String render() {
        return document.render();
    }

    /**
     * Persists/saves the rendered document using the chosen persistence strategy.
     */
    public void saveDocument() {
        storage.save(render());
    }

}

/**
 * Demonstrates usage of the document editor with simple text and image entries.
 */
public class DocEditorClient {

    public static void main(String[] args) {

        // Create a DocumentEditor using file-based persistence
        DocumentEditor docEditor = new DocumentEditor(new Document(), new FilePersistance());

        // Add elements
        docEditor.addText("Hello World");
        docEditor.addImage("abc.jpeg");
        docEditor.addNewLine();
        docEditor.addNewTab();

        docEditor.addText("Hello World");
        docEditor.addImage("abc.jpeg");

        // Save and display the final content
        docEditor.saveDocument();
        System.out.println(docEditor.render());

    }

}
