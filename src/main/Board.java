package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import temp.Shape.Tetrominoes;

public class Board extends JPanel implements ActionListener {
	
	int width = 10; // 테트리스 가상 가로 길이
	int height = 22; // 테트리스 가상 세로 길이

	Timer timer;
	boolean started = false;
	boolean paused = false;
	boolean fallingFinished = false;

	int currentX = 0; // 블록의 x좌표
	int currentY = 0; // 블록의 y좌표

	Shape currentShape; // 블록의 형태

	int[][] cells; // 가상 테트리스 필드
	// TODO 추후수정

	public Board() {
		setFocusable(true);// 포커스 설정
		currentShape = new Shape();
		timer = new Timer(400, this);// 일정 시간마다 actionPerformed 메소드 실행(imp
										// ActionListener) 400 ms
		timer.start();// 타이머 시작

		// 쌓이는 블록에 대한 정보를 저장하는 필드 생성
		// board = new Piece[width * height];
		// TODO 추후수정
		// 테트리스 게임에 대한 키보드 리스너 등록
		addKeyListener(new Adapter());
		clearBoard();
	}

	public void start() { // 게임 시작
		if (paused)// 정지 상태인 경우 정지
			return;

		started = true;
		clearBoard();

		newPiece();
		timer.start();
	}

	private void pause() { // 게임 정지 혹은 다시 시작
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

		// 쌓여 있는 블록을 그린다.
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				// TODO panel-->변화시 다시 코딩
				Piece shape = board[((height - i - 1) * width) + j];
				if (shape != Piece.NoShape)
					drawShape(g, 0 + j * ((int) size.getWidth() / width),
							boardTop + i * ((int) size.getHeight() / height), shape);
			}
		}

		// TODO panel --> 변화시 다시 코딩
		// 현재 떨어지고 있는 테트리스 블록을 그린다.
		if (currentShape.getPiece() != Piece.NoShape) {
			// 테트리스 블록을 그린다.
			for (int i = 0; i < 4; ++i) {
				int x = currentX + currentShape.getX(i);
				int y = currentY - currentShape.getY(i);
				drawShape(g, 0 + x * ((int) size.getWidth() / width),
						boardTop + (height - y - 1) * ((int) size.getHeight() / height), currentShape.getPiece());
			}
		}
	}

	// TODO panel변화시 수정
	private void drawShape(Graphics g, int x, int y, Piece shape) {
		// 색 정의 모양과 같은 위치에 해당하는 것의 색이 정의되어 있다.
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0) };

		// 선택된 모양의 컬러 가져오기
		Color color = colors[shape.ordinal()];

		// 사각형의 내부
		g.setColor(color);
		g.fillRect(x + 1, y + 1, ((int) getSize().getWidth() / width) - 2, ((int) getSize().getHeight() / height) - 2);

		// 사각형의 외부 상단, 왼쪽
		g.setColor(color.brighter());
		g.drawLine(x, y + ((int) getSize().getHeight() / height) - 1, x, y); // 왼쪽
																				// 선
		g.drawLine(x, y, x + ((int) getSize().getWidth() / width) - 1, y); // 상단
																			// 선

		// 사각형의 외부 하단, 오른쪽
		g.setColor(color.darker());
		g.drawLine(x + 1, y + ((int) getSize().getHeight() / height) - 1, x + ((int) getSize().getWidth() / width) - 1,
				y + ((int) getSize().getHeight() / height) - 1);// 하단
		g.drawLine(x + ((int) getSize().getWidth() / width) - 1, y + ((int) getSize().getHeight() / height) - 1,
				x + ((int) getSize().getWidth() / width) - 1, y + 1);// 오른쪽

	}

	public void dropDown() {
		int newY = currentY;
		while (newY > 0) {// y좌표가 0보다 크면 즉 맨 밑에 닿을때 까지 y좌표를 감소시켜준다
			if (!tryMove(currentShape, currentX, newY - 1))
				break;
			--newY;
		}
		pieceDrop();
	}

	public void oneLineDown() {
		if (!tryMove(currentShape, currentX, currentY - 1)) {// 한칸 아래로
			pieceDrop();
		}

	}

	public void pieceDrop() {// 아직 덜 했음!

		removeFullLine();

		if (fallingFinished) {
			newPiece();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	};
	
	private void clearBoard(){
		cells = new int[width][height];
	}

	private boolean tryMove(Shape newPiece, int x , int y){
		for (int i = 0; i < 4; ++i) {
            int tempX = x + newPiece.getX(i);
            int tempY = y - newPiece.getY(i);
            if (tempX < 0 || tempX >= width || tempY < 0 || tempY >= height)
                return false;
            if (shapeAt(tempY, tempY) != 1)
                return false;
        }

        currentShape = newPiece;
        currentX = x;
        currentY = y;
        repaint();
        return true;

	}
	
	private int shapeAt(int x, int y){
		return cells[x][y];
	}
	class Adapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e){
			if(!this.isKey)	
				return;
			//TODO Method명따라 변경
			switch (e.getKeyCode()){
				case KeyEvent.VK_DOWN: // 아래 방향키를 눌렀을 경우
					moveDown(); // 한 줄 떨어지기
					break;
				case KeyEvent.VK_LEFT: // 왼쪽 방향키를 눌렀을 경우
					moveLeft(); // 왼쪽으로 한 칸 이동
					break;
				case KeyEvent.VK_RIGHT: //  오른쪽 방향키를 눌렀을 경우
					moveRight(); // 오른쪽으로 한 칸 이동
					break;
				case KeyEvent.VK_UP: // 위쪽 방향키를 눌렀을 경우
					rotate(); // 모양 변경
					break;
				case KeyEvent.VK_SPACE: // 스페이스 바를 눌렀을 경우
					pause(); // 일시정지
					break;
			}
			
		}
		}
}
