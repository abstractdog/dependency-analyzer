package com.abstractdog.dependency.analyzer.eclipse.project.projectfile;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ProjectFileParser {
	private static XPathExpression nameXPath;
	private static DocumentBuilder documentBuilder;

	static {
		try {
			nameXPath = XPathFactory.newInstance().newXPath()
					.compile("/projectDescription/name/text()");
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
	
	private File projectFile;

	public ProjectFileParser(File projectFile) {
		this.projectFile = projectFile;
	}

	public String getName() throws Exception {
		Document doc = documentBuilder.parse(projectFile);

		return ((NodeList) nameXPath.evaluate(doc, XPathConstants.NODESET))
				.item(0).getNodeValue();
	}
}
