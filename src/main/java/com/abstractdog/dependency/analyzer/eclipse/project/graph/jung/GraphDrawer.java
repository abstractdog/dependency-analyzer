package com.abstractdog.dependency.analyzer.eclipse.project.graph.jung;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import com.google.common.base.Function;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.renderers.VertexLabelAsShapeRenderer;

public class GraphDrawer {
	private Graph<? extends Object, String> graph;
	private Function<Object, String> vertexStringer;
	private Color vertexLabelColor = Color.WHITE;
	private Color vertexBackgroundColor = Color.RED;
	private Position vertexLabelPosition = Position.CNTR;
	private Font vertexLabelFont = new Font("Courier New", Font.BOLD, 14);

	public GraphDrawer(Graph<? extends Object, String> graph) {
		this.graph = graph;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BufferedImage invoke() throws Exception {
		Layout layout = new KKLayout(graph);
		layout.setSize(new Dimension(graph.getVertexCount() * 30, graph.getVertexCount() * 30));
		
		VisualizationImageServer<Object, String> vis = new VisualizationImageServer<Object, String>(
				layout, layout.getSize());
		if (vertexStringer != null){
			vis.getRenderContext().setVertexLabelTransformer(vertexStringer);
		}
		
		vis.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(vertexLabelColor));
		vis.setForeground(vertexLabelColor);
				
		vis.getRenderer().getVertexLabelRenderer().setPosition(vertexLabelPosition);
		
		Function<Object, Font> vertexFont = new Function<Object, Font>() {
			@Override
			public Font apply(Object element) {
				Font font = vertexLabelFont;
				return font;
			}
		};
		vis.getRenderContext().setVertexFontTransformer(vertexFont);

		Function<Object,Paint> vertexColor = new Function<Object,Paint>() {
			@Override
			public Paint apply(Object element) {
				return vertexBackgroundColor;
			}
		};
		vis.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		
		VertexLabelAsShapeRenderer vlasr = new VertexLabelAsShapeRenderer(vis.getRenderContext());
		vis.getRenderContext().setVertexShapeTransformer(vlasr);

		
		// Create the buffered image
		BufferedImage image = (BufferedImage) vis.getImage(new Point2D.Double(
				vis.getGraphLayout().getSize().getWidth() / 2, 
				vis.getGraphLayout().getSize().getHeight() / 2),
				new Dimension(vis.getGraphLayout().getSize()));

		return image;
	}

	public GraphDrawer withLabelTransformer(Function<? super Object,String> vertexStringer) {
		this.vertexStringer = vertexStringer;
		return this;
	}
	
	public GraphDrawer withVertexLabelColor(Color vertexLabelColor) {
		this.vertexLabelColor = vertexLabelColor;
		return this;
	}
	
	public GraphDrawer withVertexLabelPosition(Position vertexLabelPosition) {
		this.vertexLabelPosition = vertexLabelPosition;
		return this;
	}
	
	public GraphDrawer withVertexLabelFont(Font vertexLabelFont) {
		this.vertexLabelFont = vertexLabelFont;
		return this;
	}
	
	public GraphDrawer withVertexBackgroundColor(Color vertexBackgroundColor) {
		this.vertexBackgroundColor = vertexBackgroundColor;
		return this;
	}
}
