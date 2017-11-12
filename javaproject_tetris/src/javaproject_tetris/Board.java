package javaproject_tetris;


import javaproject_tetris.Shape.Piece;

public class Board {
	int width;//테트리스 배경 가로 길이
	int height;//테트리스 배경 세로 길이
	
	boolean started=false;//시작
	boolean paused=false;//멈춤
	boolean finished=false;//끝
	
	int currentX=0;//블록의 X좌표
	int currentY=0;//블록의 Y좌표
	Shape currentShape;//블록의 모양

	
	public Board(){//생성자
		width=10;
		height=20;
		currentShape = new Shape();//블록생성
		clearBoard();//테트리스 배경 초기화
	}
	//테트리스 게임을 시작하는 함수
	public void start(){
		if(paused)//정지 상태인 경우 정지
			return;
		started=true;
		finished=false;
		clearBoard();//테트리스 배경 초기화
		
		newPiece();//새로운 블럭 생성
	}
	//게임 정지 혹은 다시 시작
	private void pause(){
		if(!started){
			return;
		}
		paused=!paused;//정지상태로
		if(paused){//정지할때
			System.out.println("paused");//paused 메시지 띠우기. 아직 모름.
		}else{
			start();//다시 시작
		}
	}
	//테트리스 블록 한번에 떨어 트리기-스페이스바 사용
	private void dropDown(){
		int newY=currentY;//현재 블록 Y좌표를 변수 newY에 저장
		
		while(newY>0){
			if(!tryMove(currentShape,currentX,newY-1))
				break;
			--newY;
		}
		pieceDrop();//블럭 쌓기
	}
	//블록 이동(한칸 아래로 떨어지기)-아래방향키 또는 타이머로인한.
	private void oneLineDown(){
		if(!tryMove(currentShape,currentX,currentY-1))
			pieceDrop();
	}
	//테트리스 배경 초기화
	private void clearBoard(){
		for(int i=0;i<width*height;i++){
			//Piece.NoShape해아함. 여기 잘 모르겠음.
		}
	}
	//테트리스 블럭 쌓기
	private void pieceDrop(){
		//잘 모르겠음.
		//블록이 쌓이고 만약에 블록이 한줄 가득차면 지우고 새로운 랜덤 블록 생성.
	}
	//새로운 블록 생성
	private void newPiece(){
		currentShape.setRandomShape();//랜덤 블록 설정
		//처음 블록 생성 위치는 상단 중앙이다.이거 구현 모르겠음.
		//게임 오버 조건을 확인한다.
	}
	//블록 이동 유효 검사
	private boolean tryMove(Shape newPiece,int newX, int newY){
		//4개의 작은 블록 확인
		//벽이면 더 못감. 옆에 다른 블록있으면 더 못감.
		//블럭이 바닥이거나 밑에 다른 블럭 있다면 새로운 블럭 시작
		return true;//임시
		}
	//줄 삭제
	private void removeFullLine(){
		//가로줄에 빈공간 없으면 해당 줄 삭제
		
	}
	
}
