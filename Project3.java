/*
 * Faith-Anne Kocadag
 * Affine transformations on a shape
 */

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;

public class Project3 extends JApplet {

	private ArrowPanel ap = new ArrowPanel();
	private Path2D.Double path = new Path2D.Double();
	private Shape line;

	// Initial position
	private int x = 245;
	private int y = 70;

	// Matrix fields
	private JTextField a, b, c, d, e, f;
	private double a1 = 1;
	private double b1 = 0;
	private double c1 = 0;
	private double a2 = 0;
	private double b2 = 1;
	private double c2 = 0;

	/** Default constructor */
	public Project3() {

		// Create Shapes
		path = (Path2D.Double) createArrow();
		line = new Line2D.Double(x, y + 100, x, y + 200);

		// Matrix panel
		JPanel panel = new JPanel(new GridLayout(2, 4));
		JButton apply, reset;
		panel.add(a = new JTextField("1"));
		panel.add(b = new JTextField("0"));
		panel.add(c = new JTextField("0"));
		panel.add(apply = new JButton("Apply"));
		panel.add(d = new JTextField("0"));
		panel.add(e = new JTextField("1"));
		panel.add(f = new JTextField("0"));
		panel.add(reset = new JButton("Reset"));

		// Add components
		add(ap);
		add(panel, BorderLayout.SOUTH);

		// Register listeners for buttons
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				a1 = Double.parseDouble(a.getText());
				b1 = Double.parseDouble(b.getText());
				c1 = Double.parseDouble(c.getText());
				a2 = Double.parseDouble(d.getText());
				b2 = Double.parseDouble(e.getText());
				c2 = Double.parseDouble(f.getText());
				ap.repaint();
			}
		});

		reset.addActionListener(new ActionListener() {
			/* Resets fields and initial position */
			@Override
			public void actionPerformed(ActionEvent ev) {
				a.setText("1");
				b.setText("0");
				c.setText("0");
				d.setText("0");
				e.setText("1");
				f.setText("0");
				a1 = 1;
				b1 = 0;
				c1 = 0;
				a2 = 0;
				b2 = 1;
				c2 = 0;

				// Reinstantiate shapes and repaint
				path = new Path2D.Double();
				path = (Path2D.Double) createArrow();
				line = new Line2D.Double(x, y + 100, x, y + 200);
				ap.repaint();
			}
		});
	}

	/* Create Arrow Shape */
	public Shape createArrow() {
		path.moveTo(x, y);
		path.lineTo(x + 100, y + 100);
		path.lineTo(x + 50, y + 100);
		path.lineTo(x + 50, y + 300);
		path.quadTo(x, y + 200, x - 50, y + 300);
		path.lineTo(x - 50, y + 100);
		path.lineTo(x - 100, y + 100);
		path.closePath();
		return path;
	}

	/** ArrowPanel Class */
	class ArrowPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// Set stroke and gradient
			Stroke stroke = new BasicStroke(12, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_MITER);
			g2.setStroke(stroke);

			GradientPaint grad = new GradientPaint(x, y, Color.WHITE, x + 15,
					y + 15, Color.BLUE, true);
			g2.setPaint(grad);

			// Affine transformations
			AffineTransform tr = new AffineTransform(a1, a2, b1, b2, c1, c2);
			line = tr.createTransformedShape(line);
			path = (Path2D.Double) tr.createTransformedShape(path);
			g2.draw(line);
			g2.draw(path);
		}

	}

	/** Applet init method */
	public void init() {
		setSize(500, 550);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Faith-Anne Kocadag");
		Project3 applet = new Project3();
		frame.add(applet);
		frame.setSize(500, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
