/*
 * Faith-Anne Kocadag
 * Cellular Automata
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.awt.Graphics2D;

public class Project5 extends JApplet {

	boolean[][] cells = new boolean[301][301];
	boolean[][] cells2 = new boolean[301][301];
	private Timer timer = new Timer(1000, new TimerListener());
	private CellPanel panel;

	public Project5() {
		for (int i = 0; i < 301; i++) {
			for (int j = 0; j < 301; j++) {
				cells[i][j] = false;		// Fill cells with all false values
			}
		}
		cells[150][150] = true;				// center is true
		panel = new CellPanel();
		add(panel);
		timer.start();
	}

	class CellPanel extends JPanel implements MouseListener {

		public CellPanel() {
			addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			BufferedImage image = new BufferedImage(501, 501,
					BufferedImage.TYPE_INT_RGB);
			WritableRaster raster = image.getRaster();
			int[] black = { 0, 0, 0 };
			int[] white = { 255, 255, 255 };

			for (int i = 0; i < 301; i++) {
				for (int j = 0; j < 301; j++) {

					// White is false, Black is true
					if (cells[i][j]) {
						raster.setPixel(j, i, black);
					} else {
						raster.setPixel(j, i, white);
					}
				}
			}
			g2.drawImage(image, 0, 0, this);	// draw
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// Pause timer if it is running, restart if it is not
			if (timer.isRunning())timer.stop();
			else timer.restart();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	// Counts neighboring cells
	private int neighbors(boolean[][] cells, int x, int y) {

		int count = 0;
		if (cells[x - 1][y]) count++;
		if (cells[x][y - 1]) count++;
		if (cells[x + 1][y]) count++;
		if (cells[x][y + 1]) count++;

		return count;
	}

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean[][] tempcells = cells;	// Put original cells in tempcells
			for (int i = 1; i < 300; i++) {	// Bounds do not include edge pixels
				for (int j = 1; j < 300; j++) {
					
					// Copy each tempcells value to cells2
					cells2[i][j] = tempcells[i][j];
					// Count neighbors
					int neighbors = neighbors(tempcells, i, j);

					// Update cells2 with new values
					// white is false, black is true
					if (!cells[i][j] && neighbors != 1)
						cells2[i][j] = true;
					else if (cells[i][j] && (neighbors == 1 || neighbors == 3))
						cells2[i][j] = true;
					else
						cells2[i][j] = false;
				}
			}
			
			cells = cells2;					// Make cells cells2
			cells2 = tempcells;				// Make cells2 tempcells
			repaint();						// Repaint for new values
		}
	}

	/** Applet init method */
	@Override
	public void init() {
		setSize(318, 340);
	}

	/** Applet stop method */
	@Override
	public void stop() {
		timer.stop();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Faith-Anne Kocadag");
		Project5 applet = new Project5();
		frame.add(applet);
		frame.setSize(318, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
