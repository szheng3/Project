
/*
 * Author: Shuai Zheng reference://www.java2s.com/Code/Java/2D-Graphics-GUI/Scalinganobject.htm
 * Edited By: Josue Galeas
 * Last Edit: May 8, 2016
 * Description: Class for graphing that data points.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TransformScale extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int choice;
	Shape axes, shape;
	int length = 700, arrowLength = 7, tickSize = 4;
	List<Coordinate<Double>> input;
	int originalNodes;

	public TransformScale(int originalNodes, List<Coordinate<Double>> input2) {
		this.originalNodes = originalNodes;
		this.input = input2;
		axes = createAxes();
		shape = createShape();
		JFrame f = new JFrame();
		f.getContentPane().add(this);
		f.getContentPane().setBackground(Color.WHITE);
		f.setSize(1000, 1000);
		f.setVisible(true);
		// try {
		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();
		graphics2D.fillRect(0, 0, image.getWidth(), image.getHeight());
		f.paint(graphics2D);

		try {
			ImageIO.write(image, "png", new File("./output.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Shape createAxes() {
		GeneralPath path = new GeneralPath();

		// Axes.
		path.moveTo(-length, 0);
		path.lineTo(length, 0);
		path.moveTo(0, -length);
		path.lineTo(0, length);
		// Arrows.
		path.moveTo(length - arrowLength, -arrowLength);
		path.lineTo(length, 0);
		path.lineTo(length - arrowLength, arrowLength);
		path.moveTo(-arrowLength, length - arrowLength);
		path.lineTo(0, length);
		path.lineTo(arrowLength, length - arrowLength);
		// Half-centimeter tick marks
		float cm = 72 / 2.54f;
		float lengthCentimeter = length / cm;
		for (float i = 0.5f; i < lengthCentimeter; i += 1.0f) {
			float tick = i * cm;
			path.moveTo(tick, -tickSize / 2);
			path.lineTo(tick, tickSize / 2);
			path.moveTo(-tick, -tickSize / 2);
			path.lineTo(-tick, tickSize / 2);
			path.moveTo(-tickSize / 2, tick);
			path.lineTo(tickSize / 2, tick);
			path.moveTo(-tickSize / 2, -tick);
			path.lineTo(tickSize / 2, -tick);
		}
		// Full-centimeter tick marks
		for (float i = 1.0f; i < lengthCentimeter; i += 1.0f) {
			float tick = i * cm;
			path.moveTo(tick, -tickSize);
			path.lineTo(tick, tickSize);
			path.moveTo(-tick, -tickSize);
			path.lineTo(-tick, tickSize);
			path.moveTo(-tickSize, tick);
			path.lineTo(tickSize, tick);
			path.moveTo(-tickSize, -tick);
			path.lineTo(tickSize, -tick);
		}
		return path;
	}

	protected Shape createShape() {
		float cm = 72 / 2.54f;
		return new Rectangle2D.Float(cm, cm, 2 * cm, cm);
	}

	public void paint(Graphics g) {

		try {
			Graphics2D g2 = (Graphics2D) g;

			// Use antialiasing.
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Move the origin
			AffineTransform at = AffineTransform.getTranslateInstance(10, 10);
			g2.transform(at);

			// Draw the shapes in their original locations.
			g2.setPaint(Color.black);
			g2.draw(axes);

			Graphics2D g2D;
			g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// float X = null, Y = null, datastring = null;

			double X, Y;
			int X1, Y1;
			if (choice == 2) {

				for (int i = 0; i < originalNodes; i++) {

					Image img = new ImageIcon(this.getClass().getResource("/O.png")).getImage();

					X = input.get(i).getX();
					Y = input.get(i).getY();
					X1 = -(int) X / 100 - 500 - 200;
					Y1 = ((int) Y / 100 - 300) * 3 - 100;

					g2D.drawImage(img, X1, Y1, this);

				}
				int entries = input.size();

				for (int i = originalNodes; i < entries; i++) {
					Image img2 = new ImageIcon(this.getClass().getResource("/X.png")).getImage();
					X = input.get(i).getX();
					Y = input.get(i).getY();
					X1 = -(int) X / 100 - 500 - 200;
					Y1 = ((int) Y / 100 - 300) * 3 - 100;

					g2D.drawImage(img2, X1, Y1, this);

				}
			} else if (choice == 1) {
				for (int i = 0; i < originalNodes; i++) {

					Image img = new ImageIcon(this.getClass().getResource("/O.png")).getImage();

					X = input.get(i).getX();
					Y = input.get(i).getY();
					X1 = (int) X / 50;
					Y1 = (int) Y / 50;

					g2D.drawImage(img, X1, Y1, this);

				}
				int entries = input.size();

				for (int i = originalNodes; i < entries; i++) {
					Image img2 = new ImageIcon(this.getClass().getResource("/X.png")).getImage();
					X = input.get(i).getX();
					Y = input.get(i).getY();
					X1 = (int) X / 50;
					Y1 = (int) Y / 50;

					g2D.drawImage(img2, X1, Y1, this);

				}
			}

		} catch (Exception e) {
		}

	}

}