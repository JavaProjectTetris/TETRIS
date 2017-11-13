package javaproject_tetris;


import javaproject_tetris.Shape.Piece;

public class Board {
	int width;//��Ʈ���� ��� ���� ����
	int height;//��Ʈ���� ��� ���� ����
	
	boolean started=false;//����
	boolean paused=false;//����
	boolean finished=false;//��
	
	int currentX=0;//����� X��ǥ
	int currentY=0;//����� Y��ǥ
	Shape currentShape;//����� ���

	
	public Board(){//������
		width=10;
		height=20;
		currentShape = new Shape();//��ϻ���
		clearBoard();//��Ʈ���� ��� �ʱ�ȭ
	}
	//��Ʈ���� ������ �����ϴ� �Լ�
	public void start(){
		if(paused)//���� ������ ��� ����
			return;
		started=true;
		finished=false;
		clearBoard();//��Ʈ���� ��� �ʱ�ȭ
		
		newPiece();//���ο� �� ����
	}
	//���� ���� Ȥ�� �ٽ� ����
	private void pause(){
		if(!started){
			return;
		}
		paused=!paused;//�������·�
		if(paused){//�����Ҷ�
			System.out.println("paused");//paused �޽��� ����. ���� ��.
		}else{
			start();//�ٽ� ����
		}
	}
	//��Ʈ���� ��� �ѹ��� ���� Ʈ����-�����̽��� ���
	private void dropDown(){
		int newY=currentY;//���� ��� Y��ǥ�� ���� newY�� ����
		
		while(newY>0){
			if(!tryMove(currentShape,currentX,newY-1))
				break;
			--newY;
		}
		pieceDrop();//�� �ױ�
	}
	//��� �̵�(��ĭ �Ʒ��� ��������)-�Ʒ�����Ű �Ǵ� Ÿ�̸ӷ�����.
	private void oneLineDown(){
		if(!tryMove(currentShape,currentX,currentY-1))
			pieceDrop();
	}
	//��Ʈ���� ��� �ʱ�ȭ
	private void clearBoard(){
		for(int i=0;i<width*height;i++){
			//Piece.NoShape�ؾ���. ���� �� �𸣰���.
		}
	}
	//��Ʈ���� �� �ױ�
	private void pieceDrop(){
		//�� �𸣰���.
		//����� ���̰� ���࿡ ����� ���� �������� ����� ���ο� ���� ��� ����.
	}
	//���ο� ��� ����
	private void newPiece(){
		currentShape.setRandomShape();//���� ��� ����
		//ó�� ��� ���� ��ġ�� ��� �߾��̴�.�̰� ���� �𸣰���.
		//���� ���� ������ Ȯ���Ѵ�.
	}
	//��� �̵� ��ȿ �˻�
	private boolean tryMove(Shape newPiece,int newX, int newY){
		//4���� ���� ��� Ȯ��
		//���̸� �� ����. ���� �ٸ� ��������� �� ����.
		//���� �ٴ��̰ų� �ؿ� �ٸ� �� �ִٸ� ���ο� �� ����
		return true;//�ӽ�
		}
	//�� ����
	private void removeFullLine(){
		//�����ٿ� ����� ������ �ش� �� ����
		
	}
	
}
