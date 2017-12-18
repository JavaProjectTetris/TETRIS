package main;

import java.io.File;
import java.io.FileInputStream;
import sun.audio.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	int width = 10; 
	int height = 22; 

	Timer timer;
	boolean started = false;
	boolean paused = false;
	boolean fallingFinished = false;

	int currentX = 0; 
	int currentY = 0; 

	Shape currentShape;
	int[][] cells; 

	public Board() {
		setFocusable(true);
		addKeyListener(new Adapter());
		currentShape = new Shape();
		timer = new Timer(400, this);
		timer.start();

		cells = new int[width][height];
		clearBoard();
	}

	public void start() { 
		if (paused)
			return;

		started = true;
		clearBoard();

		newPiece();
		timer.start();
		Music introMusic = new Music("bgm.mp3", true);
		introMusic.start();
	}

	private void pause() { 
		if (!started)
			return;

		paused = !paused;
		if (paused) {
			timer.stop();
		} else {
			timer.start();
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - height * squareHeight();

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (shapeAt(j, height - i - 1) != 0) {
					drawShape(g, (int) j * squareWidth(), boardTop + i * squareHeight(), shapeAt(j, height - i - 1));
				}
			}
		}

		for (int i = 0; i < 4; ++i) {
			int x = currentX + currentShape.getX(i);
			int y = currentY - currentShape.getY(i);
			drawShape(g, (int) x * squareWidth(), boardTop + (height - y - 1) * squareHeight(),
					currentShape.getShapeType());
		}

	}

	int squareWidth() {
		return (int) getSize().getWidth() / width;
	}

	int squareHeight() {
		return (int) getSize().getHeight() / height;
	}

	private void drawShape(Graphics g, int x, int y, int colorType) {
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0) };

		Color color = colors[colorType];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}

	private void clearBoard() {
		cells = new int[width][height];
	}

	public void dropDown() {
		int newY = currentY;
		while (newY > 0) {
			if (!tryMove(currentShape, currentX, newY - 1))
				break;
			newY--;
		}
		pieceDrop();
	}

	public void oneLineDown() {
		if (!tryMove(currentShape, currentX, currentY - 1)) {
			pieceDrop();
		}
	}

	private void removeFullLine() {

		for (int i = height - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < width; ++j) {
				if (shapeAt(j, i) == 0) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				for (int k = i; k < height - 1; ++k) {
					for (int j = 0; j < width; ++j) {
						cells[j][k] = shapeAt(j, k + 1);
					}
				}
			}
		}
	}

	public void pieceDrop() {
		for (int i = 0; i < 4; i++) {
			int x = currentX + currentShape.getX(i);
			int y = currentY - currentShape.getY(i);
			cells[x][y] = currentShape.getShapeType();
		}

		removeFullLine();

		if (!fallingFinished) {
			newPiece();
		}

	}

	private void newPiece() {
		currentShape = new Shape();
		currentX = width / 2 + 1; 
		currentY = height - 1 + currentShape.minY();
		if (!tryMove(currentShape, currentX, currentY)) {
			currentShape = new Shape(0);
			timer.stop();
			started = false;
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (fallingFinished) {
			fallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	};

	
	public boolean tryMove(Shape newPiece, int x, int y) {
		for (int i = 0; i < 4; ++i) {
			int tempX = x + newPiece.getX(i);
			int tempY = y - newPiece.getY(i);
	
			if (tempX < 0 || tempX >= width || tempY < 0 || tempY >= height) {
				return false;
			}

			if (shapeAt(tempX, tempY) != 0) {
				return false;
			}
		}

		currentShape = newPiece;
		currentX = x;
		currentY = y;
		repaint();
		return true;
	}

	private int shapeAt(int x, int y) {
		return cells[x][y];
	}

	public boolean preRotate(int x, int y) {
		int tmpX = x + currentX;
		int tmpY = currentY - y;
		if (tmpX < 0 || tmpX >= width || tmpY < 0 || tmpY >= height) {
			return false;
		}
		return true;
	}

	class Adapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			if (started) {
				if (e.getKeyCode() != KeyEvent.VK_ENTER) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_SPACE:
							dropDown();
							break;
						case KeyEvent.VK_LEFT:
							tryMove(currentShape, currentX - 1, currentY);
							break;
						case KeyEvent.VK_RIGHT:
							tryMove(currentShape, currentX + 1, currentY);
							break;
						case KeyEvent.VK_UP:
							boolean t = true;
							for (int i = 0; i < 4; i++) {
								int x = currentShape.getY(i);
								int y = currentShape.getX(i);
								if (!preRotate(x, y))
									t = false;
							}
							if (t)
								tryMove(currentShape.rotate(), currentX, currentY);
							break;
					}
				}else{
					pause();
				}
			} else
				return;

		}

	}

}
