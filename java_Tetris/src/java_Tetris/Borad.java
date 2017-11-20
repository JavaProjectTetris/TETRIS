package java_Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	int width = 10; // ��Ʈ���� ���� ���� ����
	int height = 22; // ��Ʈ���� ���� ���� ����

	Timer timer;
	boolean started = false;
	boolean paused = false;

	int currentX = 0; // ����� x��ǥ
	int currentY = 0; // ����� y��ǥ

	Shape currentShape; // ����� ����

	Piece[] board; // ���� ��Ʈ���� �ʵ�

	public Board() {
		setFocusable(true);// ��Ŀ�� ����
		currentShape = new Shape();
		timer = new Timer(400, this);// ���� �ð����� actionPerformed �޼ҵ� ����(imp ActionListener) 400 ms
		timer.start();// Ÿ�̸� ����

		// ���̴� ��Ͽ� ���� ������ �����ϴ� �ʵ� ����
		board = new Piece[width * height];

		// ��Ʈ���� ���ӿ� ���� Ű���� ������ ���
		addKeyListener(new Adapter());
		clearBoard();
	}

	public void start() { // ���� ����
		if (paused)// ���� ������ ��� ����
			return;

		started = true;
		clearBoard();

		newPiece();
		timer.start();
	}

	private void pause() { // ���� ���� Ȥ�� �ٽ� ����
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

	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - height * ((int) size.getHeight() / height);

		// �׿� �ִ� ����� �׸���.
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Piece shape = board[((height - i - 1) * width) + j];
				if (shape != Piece.NoShape)
					drawShape(g, 0 + j * ((int) size.getWidth() / width),
							boardTop + i * ((int) size.getHeight() / height), shape);
			}
		}

		// ���� �������� �ִ� ��Ʈ���� ����� �׸���.
		if (currentShape.getPiece() != Piece.NoShape) {
			// ��Ʈ���� ����� �׸���.
			for (int i = 0; i < 4; ++i) {
				int x = currentX + currentShape.getX(i);
				int y = currentY - currentShape.getY(i);
				drawShape(g, 0 + x * ((int) size.getWidth() / width),
						boardTop + (height - y - 1) * ((int) size.getHeight() / height), currentShape.getPiece());
			}
		}
	}

	private void drawShape(Graphics g, int x, int y, Piece shape) {
		// �� ���� ���� ���� ��ġ�� �ش��ϴ� ���� ���� ���ǵǾ� �ִ�.
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0) };

		// ���õ� ����� �÷� ��������
		Color color = colors[shape.ordinal()];

		// �簢���� ����
		g.setColor(color);
		g.fillRect(x + 1, y + 1, ((int) getSize().getWidth() / width) - 2, ((int) getSize().getHeight() / height) - 2);

		// �簢���� �ܺ� ���, ����
		g.setColor(color.brighter());
		g.drawLine(x, y + ((int) getSize().getHeight() / height) - 1, x, y); // ���� ��
		g.drawLine(x, y, x + ((int) getSize().getWidth() / width) - 1, y); // ��� ��

		// �簢���� �ܺ� �ϴ�, ������
		g.setColor(color.darker());
		g.drawLine(x + 1, y + ((int) getSize().getHeight() / height) - 1, x + ((int) getSize().getWidth() / width) - 1,
				y + ((int) getSize().getHeight() / height) - 1);// �ϴ�
		g.drawLine(x + ((int) getSize().getWidth() / width) - 1, y + ((int) getSize().getHeight() / height) - 1,
				x + ((int) getSize().getWidth() / width) - 1, y + 1);// ������

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	};

}
