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

	int width = 10; // 테트리스 가상 가로 길이
	int height = 22; // 테트리스 가상 세로 길이

	Timer timer;
	boolean started = false;
	boolean paused = false;
	boolean fallingFinished = false;

	int currentX = 0; // 블록의 x좌표
	int currentY = 0; // 블록의 y좌표a

	Shape currentShape; // 블록의 형태
	int[][] cells; // 가상 테트리스 필드

	public Board() {
		setFocusable(true);// 포커스 설정
		// 테트리스 게임에 대한 키보드 리스너 등록
		addKeyListener(new Adapter());
		currentShape = new Shape();
		timer = new Timer(400, this);// 일정 시간마다 actionPerformed 메소드 실행(imp
		timer.start();// 타이머 시작

		// 쌓이는 블록에 대한 정보를 저장하는 필드 생성
		cells = new int[width][height];

		clearBoard();
	}

	public void start() { // 게임 시작
		if (paused)// 정지 상태인 경우 정지
			return;

		started = true;
		clearBoard();

		newPiece();
		timer.start();
		Music introMusic=new Music("bgm.mp3", true);
		introMusic.start();
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - height * squareHeight();

		// 쌓여 있는 블록을 그린다.
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				// TODO 쌓여있는 도형 색 판별법
				// Shape temp =
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
	}// 도형 넓이

	int squareHeight() {
		return (int) getSize().getHeight() / height;
	}// 도형 높이

	// TODO panel변화시 수정
	private void drawShape(Graphics g, int x, int y, int colorType) {
		// 색 정의 모양과 같은 위치에 해당하는 것의 색이 정의되어 있다.
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0) };

		// 선택된 모양의 컬러 가져오기
		Color color = colors[colorType];

		// 사각형의 내부
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		// 사각형의 외부 상단, 왼쪽
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);
		// 선

		// 사각형의 외부 하단, 오른쪽
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}

	// 새로운 보드 화면
	private void clearBoard() {
		cells = new int[width][height];
	}

	// 블럭 떨어뜨리기(스페이스바 사용시)
	public void dropDown() {
		int newY = currentY;
		while (newY > 0) {// y좌표가 0보다 크면 즉 맨 밑에 닿을때 까지 y좌표를 감소시켜준다
			if (!tryMove(currentShape, currentX, newY - 1))
				break;
			newY--;
		}
		pieceDrop();
	}

	// 블럭 떨어뜨리기(방향키 아래키 사용시)
	public void oneLineDown() {
		if (!tryMove(currentShape, currentX, currentY - 1)) {// 한칸 아래로
			pieceDrop();
		}
	}

	// 줄 삭제 메소드
	private void removeFullLine() {

		for (int i = height - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < width; ++j) {// 줄이 가득찼는지 확인
				if (shapeAt(j, i) == 0) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				for (int k = i; k < height - 1; ++k) {
					for (int j = 0; j < width; ++j) {
						// cells[(k * width) + j] = shapeAt(j, k + 1);
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
		currentShape = new Shape(); // 랜덤블록 생성
		currentX = width / 2 + 1; // 중앙에 새로운 블록 생성
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

	// 이동 유효 검사 메소드
	public boolean tryMove(Shape newPiece, int x, int y) {
		for (int i = 0; i < 4; ++i) {
			int tempX = x + newPiece.getX(i);
			int tempY = y - newPiece.getY(i);
			// 벽이면 더 못감
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

	public boolean preRotate(int x, int y){
		int tmpX = x + currentX;
		int tmpY = currentY - y ;
		if (tmpX < 0 || tmpX >= width || tmpY < 0 || tmpY >= height) {
			return false;
		}
		return true;
	}
	class Adapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Method명따라 변경
			if (started) {
				switch (e.getKeyCode()) {

				case KeyEvent.VK_DOWN: // 아래 방향키를 눌렀을 경우
					dropDown(); // 한 줄 떨어지기
					break;
				case KeyEvent.VK_LEFT: // 왼쪽 방향키를 눌렀을 경우
					tryMove(currentShape, currentX - 1, currentY); // 왼쪽으로 한 칸 이동
					break;
				case KeyEvent.VK_RIGHT: // 오른쪽 방향키를 눌렀을 경우
					tryMove(currentShape, currentX + 1, currentY); // 오른쪽으로 한 칸이동
					break;
				case KeyEvent.VK_UP: // 위쪽 방향키를 눌렀을 경우
					boolean t = true;
					for(int i=0; i<4; i++){
						int x= currentShape.getY(i);
						int y= currentShape.getX(i);
						if(!preRotate(x,y))
							t= false;
					}
					if(t)
						tryMove(currentShape.rotate(), currentX, currentY); // 모양
					break;
				case KeyEvent.VK_SPACE: // 스페이스 바를 눌렀을 경우
					pause(); // 일시정지
					break;
				}
			} else
				return;

		}

	}

}
