/*
 * Faith-Anne Kocadag
 * Drawing Strings, like ASCII Art
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Project4 extends JApplet {

	JTextField text;
	JTextArea textArea;

	public Project4() {
		text = new JTextField(30);
		text.setText("Enter a String");
		textArea = new JTextArea(20, 80);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		JButton print = new JButton("Print");

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(text);
		top.add(print);

		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(textArea);

		// Register listener for button
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String s = text.getText();
				if (s.equals(""))
					text.setText("Enter a String!");

				// Instantiate a BufferedImage and Graphics2D object to draw it
				BufferedImage image = new BufferedImage(75, 20,
						BufferedImage.TYPE_INT_BGR);
				Graphics2D g2 = (Graphics2D) image.getGraphics();

				g2.setFont(new Font("Serif", Font.BOLD, 20));
				g2.drawString(s, 5, 15); // String, x, y

				// Obtain pixel values
				Raster ras = image.getData();
				textArea.setText(""); // Clear Panel

				// Create StringBuffer of non-white pixels
				for (int i = 0; i < 20; i++) {
					StringBuffer draw = new StringBuffer("");
					for (int j = 0; j < 75; j++) {
						// If any color exists in the pixel, add a *
						if (ras.getSample(j, i, 1) > 0) {
							draw.append("*");
						} else { // Else leave a space
							draw.append(" ");
						}
					}
					System.out.println(draw);
					textArea.append(draw + "\n");
				}
			}
		});
	}

	/** Applet init method */
	public void init() {
		setSize(500, 320);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Faith-Anne Kocadag");
		Project4 applet = new Project4();
		frame.add(applet);
		frame.setSize(500, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
