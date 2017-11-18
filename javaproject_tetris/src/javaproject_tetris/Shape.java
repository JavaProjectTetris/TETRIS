package javaproject_tetris;

import java.util.Random;
import java.lang.Math;

public class Shape {
	private static Random random = new Random();

	private int shapeType;//��� ���� ����
	private int rotateIndex;//��� ȸ�� ���� �ε���(0=0��,1=90��,2=180��,3=270��)
	private int x,y;//����� ������ǥ(0,0)�̶� ����.
	private Coordinate[]coordinates = new Coordinate[4];//��� 4ĭ�� x,y��ǥǥ��.

	public Shape(){//������� ����
		this.shapeType=random.nextInt(7)+1;//1~7������ ������ ������ ������ ��������� �ǹ��� ��.
		this.rotateIndex=0;
		calculateCoordinate();
	}

	public Shape(int shapeType){//���õ� ����� ����
		this.shapeType=shapeType;
		this.rotateIndex=0;
		calculateCoordinate();
	}

	public int minX(){//����� ���� 4�� ����� ��ǥ �� ���� ���� x��ǥ
		int min=Integer.MAX_VALUE;//�ִ� 10�ڸ� ���ڸ� �޾ƾ� �ּڰ��� �ٷ� ����.intŸ������ �ϸ� RuntimeError�� �߻��Ҽ��ִ�.
		for(int i=0;i<4;i++){
			min=Math.min(min,coordinates[i].x);
		}
		return min;
	}

	public int minY(){//����� ���� 4�� ����� ��ǥ �� ���� ���� y��ǥ
		int min=Integer.MAX_VALUE;
		for(int i=0;i<4;i++){
			min=Math.min(min, coordinates[i].y);
		}
		return min;
	}

	public void moveDown(){//�Ʒ��� ���������� ���� 4�� ����� ��ǥ
		--y;
		calculateCoordinate();
	}

	public void moveLeft(){//�������� ���������� ���� 4�� ����� ��ǥ
		--x;
		calculateCoordinate();
	}

	public void moveRight(){//���������� ���������� ���� 4�� ����� ��ǥ
		++x;
		calculateCoordinate();
	}

	public void rotate(){
		++rotateIndex;
		if(rotateIndex>=4){
			rotateIndex=0;//ȸ���� 270���� ������ ��,360���� 0���� ����.
		}
		calculateCoordinate();
	}

	public Coordinate getCoordinate(int index){//��� 4ĭ�� ���ϴ� 1�� ����� x,y��ǥ
		return coordinates[index];
	}

	/* 7���� ��� ���ǥ��
	 * 
	 * 		1	2	3	4	5	6	 7
	 * 		#	##	 #	#	 ##	##	 #
	 * 		#	##	 #	#	##	 ##	###
	 * 		#		##	##	 	 
	 * 		#
	 */
	//1�� IShape������ ������ ��ǥ
	private static Coordinate[][] IShape = new Coordinate[][]{
		//0��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(0,-2)},
		//90��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-2,0)},
		//180��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,2)},
		//270��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(2,0)}
	};
	//2�� OShape������ ������ ��ǥ
	private static Coordinate[][] OShape = new Coordinate[][]{
		//0��
		{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0), new Coordinate(1,1)},
		//90��
		{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0), new Coordinate(1,1)},
		//180��
		{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0), new Coordinate(1,1)},
		//270��
		{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0), new Coordinate(1,1)}
	};
	//3�� JShape������ ������ ��ǥ
	private static Coordinate[][] JShape = new Coordinate[][]{
		//0��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-1,-1)},
		//90��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-1,1)},
		//180��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,1)},
		//270��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(1,-1)}
	};
	//4�� LShape������ ������ ��ǥ
	private static Coordinate[][] LShape = new Coordinate[][]{
		//0��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(1,-1)},
		//90��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-1,-1)},
		//180��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(-1,1)},
		//270��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(1,1)}
	};
	//5�� SShape������ ������ ��ǥ
	private static Coordinate[][] SShape = new Coordinate[][]{
		//0��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(-1,-1)},
		//90��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-1,1)},
		//180��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,1)},
		//270��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(1,-1)}
	};
	//6�� ZShape������ ������ ��ǥ
	private static Coordinate[][] ZShape = new Coordinate[][]{
		//0��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(1,-1)},
		//90��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(-1,-1)},
		//180��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(-1,1)},
		//270��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(1,1)}
	};
	//7�� TShape������ ������ ��ǥ
	private static Coordinate[][] TShape = new Coordinate[][]{
		//0��
		{new Coordinate(0,1), new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(1,0)},
		//90��
		{new Coordinate(1,0), new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,-1)},
		//180��
		{new Coordinate(0,-1), new Coordinate(0,0), new Coordinate(1,0), new Coordinate(-1,0)},
		//270��
		{new Coordinate(-1,0), new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(0,1)}
	};
	private static Coordinate[][] GetShapeCor(int shapeType){//���õ� ��ϸ�纰 �����ǥ.
		//�����ǥ�� ��� ������ǥ���� ������� �Ÿ�.
		Coordinate[][] r =null;
		switch(shapeType){
		case 1: r=IShape;
		break;
		case 2: r=OShape;
		break;
		case 3: r=JShape;
		break;
		case 4: r=LShape;
		break;
		case 5: r=SShape;
		break;
		case 6: r=ZShape;
		break;
		case 7: r=TShape;
		break;
		}
		return r;
	}
	private void calculateCoordinate(){//���� ��� 4���� ��ǥ�� ����Ͽ� �����ϴ� �޼ҵ�.
		//����� ������ǥ+�����ǥ=4ĭ�� ��ǥ
		Coordinate[][] cor = GetShapeCor(this.shapeType);//�����ǥ
		for(int i=0;i<4;i++){
			coordinates[i].x=x+cor[rotateIndex][i].x;
			coordinates[i].y=y+cor[rotateIndex][i].y;		
		}
	}

}