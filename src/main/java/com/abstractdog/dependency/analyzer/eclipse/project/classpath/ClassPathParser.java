package com.abstractdog.dependency.analyzer.eclipse.project.classpath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ClassPathParser {
	private static XPathExpression entryXPath;
	private static DocumentBuilder documentBuilder;

	private List<ClassPathEntry> classPathEntries;

	static {
		try {
			entryXPath = XPathFactory.newInstance().newXPath()
					.compile("/classpath/classpathentry");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}

		try {
			documentBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private File classPathFile;

	public ClassPathParser(File classPathFile) {
		this.classPathFile = classPathFile;
	}

	public List<ClassPathEntry> getClassPathEntries() throws Exception {
		if (classPathEntries != null) {
			return classPathEntries;
		}

		classPathEntries = new ArrayList<ClassPathEntry>();

		Document doc = documentBuilder.parse(classPathFile);
		NodeList nodeList = (NodeList) entryXPath.evaluate(doc,
				XPathConstants.NODESET);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			ClassPathEntry entry = new ClassPathEntry.Builder(getAttributeValue(node, "kind"))
				.path(getAttributeValue(node, "path"))	
				.output(getAttributeValue(node, "output"))
				.excluding(getAttributeValue(node, "excluding"))
				.build();
			
			classPathEntries.add(entry);
		}

		return classPathEntries;
	}

	private String getAttributeValue(Node node, String name) {
		return node.getAttributes().getNamedItem(name) == null ? null : node
				.getAttributes().getNamedItem(name).getNodeValue();
	}
}
