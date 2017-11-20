package ��Ʈ����;
import java.lang.Math;
import java.util.Random;


public class Shape {
	int coordinate[][];
	int angle;
	private int x;
	private int y;
	


	public Shape() {
		coordinate=new int [4][2]; //��ǥ ������ ���� �迭 ���� 
		coordinate=Piece.NoShape;
	}
	public void setX(int index) {
		coordinate[index][0]=this.x;	//x��ǥ 
	}
	public void setY(int index, int y) {
		coordinate[index][1]=this.y; //y��ǥ
	}
	 
	public int getX(int index) {
		return coordinate[index][0];  //x��ǥ ����
	}
	public int getY(int index) {
		return coordinate[index][1];  //y��ǥ ���� 
	}
	public int minX() {
		int m=coordinate[0][0]; //ù��°x��ǥ 
		for(int i=0; i<4; i++) {
			m=Math.min(m, coordinate[i][0]);//x��ǥ�� ��
			
		}
		return m;
	}
	public int minY() {
		int m=coordinate[0][1];
		for(int i=0; i<4; i++) {
			m=Math.min(m, coordinate[i][1]); //y��ǥ�� ���� ���� ����
		}
		return m;
	}
	public Shape rotate() {
		for(int i=0; i<4; i++) {
			//x,y ��ǥ �ٲ��ֱ�
		}
	}
	public Piece getPiece() { //�������� ���ڸ� �޾� ��Ʈ���� ���� ����
		int random=(int)Math.random()*7;
		
		switch(random){
		case 0: return Piece.IShape;
			
		case 1: return Piece.JShape;
		
		case 2: return Piece.LShape;
		
		case 3: return Piece.OShape;
		
		case 4: return Piece.SShape;
		
		case 5: return Piece.TShape;
		
		case 6: return Piece.ZShape;

	}

	}




class NoShape extends Shape{

	private int[][] coordsTable=new int [][] {
		{0,0}, {0,0},{0,0},{0,0}
	};
	
	
}

class IShape extends Shape{//����
	
	private int [][]coordsTable=new int [][] {
		{0,-1}, {0,0},{0,1},{0,2}
	};
	
	
	
}
class JShape extends Shape{//�����
	
	private int [][]coordsTable=new int [][] {
		{-1,-1}, {0,-1},{0,0},{0,1}
	};
	
}
class LShape extends Shape{//���ݴ��
	private int [][]coordsTable=new int [][] {
		{1,-1}, {0,-1},{0,0},{0,1}
	};
	
	
}

class OShape extends Shape{//�׸�
	private int [][]coordsTable=new int [][] {
		{0,0}, {1,0},{0,1},{1,1}
	};
	
}
class SShape extends Shape{//z��� �ݴ�� 
	private int [][]coordsTable=new int [][] {
		{0,-1}, {0,0},{-1,0},{-1,1}
	};
	
	
}
class TShape extends Shape{//�Ǹ��
	private int [][]coordsTable=new int [][] {
		{-1,0}, {0,0},{1,0},{0,1}
	};
	
	
}
class ZShape extends Shape{//z���
	private int [][]coordsTable=new int [][] {
		{0,-1}, {0,0},{1,0},{1,1}
	};


}


enum Piece {

	NoShape, IShape, JShape, LShape, OShape, SShape, TShape, ZShape

}
}
