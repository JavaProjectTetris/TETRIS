package tetris2;

/*
Class 설명
- Block : 블록의 X좌표 Y좌표의 정보를 가지고 있는 블록 클래스.
- Item : 블록을 가지고 테트리스 아이템(모양) 을 만든다. (부모클래스)
- Rect, OneThree, ThreeOne ... : Item클래스를 상속받아 각블록위치정보를 셋팅한다.
 */
import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

//블록 클래스
class Block {
	private int x;
	private int y;
	
	//생성자
	public Block() {
	}
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//해당 포인트맛큼 감산
	public void move(int xPlus, int yPlus) {
		this.x += xPlus;
		this.y += yPlus;
	}
	
	//X포인트 반환
	public int getX() {
		return this.x;
	}
	
	//Y포인트 반환
	public int getY() {
		return this.y;
	}
	
	//자신 반환
	public Block getBlock() {
		return this;
	}
	
	//XY셋팅
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

//아이템 클래스
public class Item {
	JPanel[] panel;		//판넬
	Block[] block;		//현재포인트
	Block[][] block_info;		//각 각도별 포인트정보
	//앞의배열 0~3 각도 0-0도 1-90도 2-180도 3-270도
	//뒤의 배열은 판넬 갯수
	
	Block currentXY;
	int cnt;				//총판넬개수
	int angle;				//총각도개수
	int current_angle;		//현재각도값
	int xCnt;				//가로값

	Color color;		//색
	int area;			//넓이

	public Item(int area, int angle, int cnt, int xCnt) {
		this.angle = angle;
		this.cnt = cnt;
		this.panel = new JPanel[cnt];				//판넬개수 셋팅
		this.block = new Block[cnt];				//포인트 셋팅
		this.block_info = new Block[angle][cnt];	//포인트 각도, 개수셋팅
		this.area = area;									
		this.currentXY = new Block(0,0);			//현재값
		this.xCnt = xCnt;

		for(int i=0; i<cnt; i++) {			//패널생성
			this.panel[i] = new JPanel();
		}
	}
	
	public void setDefaultRandom() {
		this.current_angle = (int)(Math.random() * this.angle);
		this.block = this.block_info[this.current_angle];
	}
	
	//컨테이너에 등록
	public void setItem(Container c) {
		for(int i=0; i<panel.length; i++) {
			panel[i].setBackground(this.color);		//배경색
			panel[i].setSize(area, area);			//넓이
			panel[i].setLocation(((block[i].getX()) * area)-100, ((block[i].getY()) * area)-100);	//기본위치 안보이는곳에 생성
			panel[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
			c.add(panel[i]);	//컨테이너에 등록
		}
	}
	
	//다음위치조정
	public void setNextLocation() {
		for(int i=0; i<panel.length; i++) {
			int x = block[i].getX() + (xCnt-3);
			int y = block[i].getY() + 1;
			panel[i].setLocation(x * area, y * area);
		}
		this.currentXY.setXY((xCnt-3),1);
	}
	
	//시작위치조정
	public void setDefaultLocation() {
		for(int i=0; i<panel.length; i++) {
			int x = block[i].getX() + xCnt/2-2;
			int y = block[i].getY() +2;
			panel[i].setLocation(x * area, y * area);
		}
		this.currentXY.setXY(xCnt/2-2,2);
	}
	
	//대기상태 위치조정
	public void setReadyLocation() {
		for(int i=0; i<panel.length; i++) {
			panel[i].setLocation(((block[i].getX()) * area)-100, ((block[i].getY()) * area)-100);
		}
	}
	
	//현재위치조정
	public void setCurrentXY(int x, int y) {
		this.currentXY.move(x,y);
	}
	
	//현재위치반환
	public Block getCurrentXY() {
		return this.currentXY;
	}
	
	//현재 포인트 리턴
	public Block[] getBlock() {
		Block[] temp = new Block[cnt];
		for(int i=0; i<block.length; i++) {
			int x = block[i].getX() + this.currentXY.getX();
			int y = block[i].getY() + this.currentXY.getY();
			temp[i] = new Block(x,y);
		}
		return temp;
	}
	
	//다음움직일각도의 포인트정보 반환
	public Block[] getNextBlock() {
		int nextAngle;
		if(this.angle==1)	return getBlock();	//각도가1개뿐이면 리턴
		else if(this.angle-1 == this.current_angle)	nextAngle=0;	//마지막앵글이면 1번앵글로
		else	nextAngle=this.current_angle+1;	//다음각도 셋팅

		Block[] temp = new Block[cnt];
		for(int i=0; i<block.length; i++) {
			int x = block_info[nextAngle][i].getX() + this.currentXY.getX();
			int y = block_info[nextAngle][i].getY() + this.currentXY.getY();
			temp[i] = new Block(x,y);
		}
		return temp;
	}
	
	//현재앵글리턴
	public int getCurrentAngle(){
		return this.current_angle;
	}
	
	//로테이트
	public void moveRotate() {
		if(this.angle==1)	return;	//각도가1개뿐이면 리턴
		if(this.current_angle+1 == this.angle){	//최고각도면 처음각도로
			this.block = this.block_info[0];
			this.current_angle = 0;
		} else {
			this.current_angle++;
			this.block = this.block_info[this.current_angle];
		}
		this.setMove();
	}
	
	//현재의 포인트 정보를 판넬에 적용하여 움직여라 
	public void setMove() {
		for(int i=0; i<panel.length; i++) {
			//현재블록의 x,y값에 현재x,y포인트값을 더한값을 각area값과 곱한다.
			int x = this.block[i].getX() + this.currentXY.getX();
			int y = this.block[i].getY() + this.currentXY.getY();
			panel[i].setLocation(x * area, y * area);
		}
	}
	
	//아래로 한칸 움직임
	public void moveDown() {
		this.currentXY.move(0,1);		
		this.setMove();
	}
	
	//오른쪽으로 한칸 움직임
	public void moveRight() {
		this.currentXY.move(1,0);
		this.setMove();
	}
	
	//왼쪽으로 한칸 움직임
	public void moveLeft() {
		this.currentXY.move(-1,0);		
		this.setMove();
	}
	
	//현재 색 리턴
	public Color getColor() {
		return this.color;
	}
	
	//현재 색 셋팅
	public void setColor(Color c) {
		this.color = c;
		for(int i=0; i<panel.length; i++) {
			panel[i].setBackground(this.color);
		}
	}
}





//사각형 
class Rect extends Item {
	public Rect(int area, Container con, int xCnt) {
		super(area, 1, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,0);
		block_info[0][1] = new Block(0,1);
		block_info[0][2] = new Block(1,0);
		block_info[0][3] = new Block(1,1);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//기억자
class OneThree extends Item
{
	public OneThree(int area, Container con, int xCnt){
		super(area, 4, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,0);
		block_info[0][1] = new Block(0,1);
		block_info[0][2] = new Block(1,1);
		block_info[0][3] = new Block(2,1);

		block_info[1][0] = new Block(0,2);
		block_info[1][1] = new Block(1,2);
		block_info[1][2] = new Block(1,1);
		block_info[1][3] = new Block(1,0);

		block_info[2][0] = new Block(2,1);
		block_info[2][1] = new Block(2,0);
		block_info[2][2] = new Block(1,0);
		block_info[2][3] = new Block(0,0);

		block_info[3][0] = new Block(1,0);
		block_info[3][1] = new Block(0,0);
		block_info[3][2] = new Block(0,1);
		block_info[3][3] = new Block(0,2);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//기억자 반대
class ThreeOne extends Item
{
	public ThreeOne(int area, Container con, int xCnt){
		super(area, 4, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,1);
		block_info[0][1] = new Block(0,0);
		block_info[0][2] = new Block(1,0);
		block_info[0][3] = new Block(2,0);

		block_info[1][0] = new Block(1,2);
		block_info[1][1] = new Block(0,2);
		block_info[1][2] = new Block(0,1);
		block_info[1][3] = new Block(0,0);

		block_info[2][0] = new Block(2,0);
		block_info[2][1] = new Block(2,1);
		block_info[2][2] = new Block(1,1);
		block_info[2][3] = new Block(0,1);

		block_info[3][0] = new Block(0,0);
		block_info[3][1] = new Block(1,0);
		block_info[3][2] = new Block(1,1);
		block_info[3][3] = new Block(1,2);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//일자
class LineBlock extends Item
{
	public LineBlock(int area, Container con, int xCnt){
		super(area, 2, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,-1);
		block_info[0][1] = new Block(0,0);
		block_info[0][2] = new Block(0,1);
		block_info[0][3] = new Block(0,2);

		block_info[1][0] = new Block(-1,0);
		block_info[1][1] = new Block(0,0);
		block_info[1][2] = new Block(1,0);
		block_info[1][3] = new Block(2,0);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//┷ 요모양~ ㅋㅋ
class Triangle extends Item
{
	public Triangle(int area, Container con, int xCnt){
		super(area, 4, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(1,0);
		block_info[0][1] = new Block(0,1);
		block_info[0][2] = new Block(1,1);
		block_info[0][3] = new Block(2,1);

		block_info[1][0] = new Block(0,0);
		block_info[1][1] = new Block(0,1);
		block_info[1][2] = new Block(0,2);
		block_info[1][3] = new Block(1,1);

		block_info[2][0] = new Block(0,0);
		block_info[2][1] = new Block(1,0);
		block_info[2][2] = new Block(2,0);
		block_info[2][3] = new Block(1,1);

		block_info[3][0] = new Block(0,1);
		block_info[3][1] = new Block(1,0);
		block_info[3][2] = new Block(1,1);
		block_info[3][3] = new Block(1,2);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//_|- 요모양? ㅋㅋ
class RightBlock extends Item
{
	public RightBlock(int area, Container con, int xCnt){
		super(area, 2, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,0);
		block_info[0][1] = new Block(0,1);
		block_info[0][2] = new Block(1,1);
		block_info[0][3] = new Block(1,2);

		block_info[1][0] = new Block(1,0);
		block_info[1][1] = new Block(0,0);
		block_info[1][2] = new Block(0,1);
		block_info[1][3] = new Block(-1,1);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}

//-|_ 요모양 ㅋㅋㅋ
class LeftBlock extends Item
{
	public LeftBlock(int area, Container con, int xCnt){
		super(area, 2, 4, xCnt);	//영역길이, 각도갯수, 판넬개수

		block_info[0][0] = new Block(0,0);
		block_info[0][1] = new Block(1,0);
		block_info[0][2] = new Block(1,1);
		block_info[0][3] = new Block(2,1);

		block_info[1][0] = new Block(0,1);
		block_info[1][1] = new Block(0,0);
		block_info[1][2] = new Block(1,0);
		block_info[1][3] = new Block(1,-1);

		this.setDefaultRandom();	//랜덤셋팅
		this.setItem(con);			//컨테이너에 등록
	}
}