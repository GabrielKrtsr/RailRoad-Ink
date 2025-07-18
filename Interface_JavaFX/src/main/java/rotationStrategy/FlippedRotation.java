package rotationStrategy;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


/**
 * Implements a rotation strategy for flipping the EAST and WEST paths of an AbstractFace instance.
 */
public class FlippedRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "F" stands for "Flipped".
     */
    private final String id = "F";

    @Override
    public String getId() {
        return id;
    }


    @Override
    public void rotate(AbstractFace face) {
        face.setRotate(id);
        Node newTop = face.getNode(Side.TOP.getFlippedSide());
        Node newBottom = face.getNode(Side.BOTTOM.getFlippedSide());
        Node newLeft = face.getNode(Side.LEFT.getFlippedSide());
        Node newRight = face.getNode(Side.RIGHT.getFlippedSide());

        face.setNode(Side.TOP, newTop);
        face.setNode(Side.BOTTOM, newBottom);
        face.setNode(Side.LEFT, newLeft);
        face.setNode(Side.RIGHT, newRight);

        face.changeImageSvg(rotateSvg(face.getImageSvg()));
    }


//    private String rotateSvg(String svgContent) {
//
//        if (svgContent == null || svgContent.isEmpty()) {
//            return svgContent;
//        }
//
//        int svgIndex = svgContent.lastIndexOf("<g");
//        if (svgIndex != -1) {
//            StringBuilder rotatedSvgContent = new StringBuilder(svgContent);
//            rotatedSvgContent.insert(svgIndex + 2, " transform=\" translate(250, 0) scale(-1, 1) \"");
//            return rotatedSvgContent.toString();
//        }
//
//        return svgContent;
//    }

    private String rotateSvg(String svgContent) {
        if (svgContent == null || svgContent.isEmpty()) {
            return svgContent;
        }

        String wrappedSvgContent = "<svg viewBox=\"0 0 256 256\" width=\"100%\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\">" + svgContent + "</svg>";

        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            Document document = factory.createDocument(null, new java.io.StringReader(wrappedSvgContent));

            NodeList gElements = document.getElementsByTagName("g");
            for (int i = 0; i < gElements.getLength(); i++) {
                Element gElement = (Element) gElements.item(i);
                String classAttribute = gElement.getAttribute("class");
                if ("rail".equals(classAttribute) || "road".equals(classAttribute)) {
                    String existingTransform = gElement.getAttribute("transform");
                    String newTransform = " translate(256, 0) scale(-1, 1)";
                    if (existingTransform == null || existingTransform.isEmpty()) {
                        gElement.setAttribute("transform", newTransform);
                    }
                }
            }

            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(writer));

            String transformedSvg = writer.toString();

            int startIndex = transformedSvg.indexOf("<svg");
            int endIndex = transformedSvg.lastIndexOf("</svg>");

            if (startIndex != -1 && endIndex != -1) {
                int contentStart = transformedSvg.indexOf('>', startIndex) + 1;
                if (contentStart > 0 && contentStart < endIndex) {
                    return transformedSvg.substring(contentStart, endIndex);
                }
            }

            return transformedSvg;
        } catch (Exception e) {
            e.printStackTrace();
            return svgContent;
        }
    }

    @Override
    public RotationStrategy getOppositeRotation() {
        return new FlippedRotation();
    }


}
