package main;

import javax.swing.JFrame;

public class Tetris extends JFrame{
	
	public Tetris() {

        Board board= new Board();
        add(board);
        board.start();

        setSize(800, 1000);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

    public static void main(String[] args){
    	Tetris tetris = new Tetris();
    	tetris.setLocationRelativeTo(null);
    	tetris.setVisible(true);
    }

}
