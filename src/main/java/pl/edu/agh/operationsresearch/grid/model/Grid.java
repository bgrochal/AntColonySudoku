package pl.edu.agh.operationsresearch.grid.model;

import java.util.Arrays;


public class Grid {
    private int[][] values;
    private int selected;
    public static final int GRID_SIZE = 9;
    
    public Grid(){
        selected = 0;
        values = new int[GRID_SIZE][GRID_SIZE];
    }
    
    public Grid(int[][] grid){
        set(grid);
    }
    
    public int selected(){
        return selected;
    }
    
    public void set(int[][] grid){
        values = grid;
        selected = 0;
        for(int i=0;i<GRID_SIZE;i++){
            for(int j=0;j<GRID_SIZE;j++){
                if(isset(i,j)){
                    selected++;
                }
            }
        }
    }
    
    public boolean set(int row, int col, int value){
        if(digitInRow(row, value) || digitInColumn(col, value) || digitInSubgrid(row, col, value)){
            return false;
        }
        
        if(!isset(row, col)){
            selected++;
        }
        values[row][col] = value;
        return true;
    }
    
    public int get(int row, int col){
        return values[row][col];
    }
    
    public boolean isset(int row, int col){
        return get(row, col) != 0;
    }
    
    public boolean digitInColumn(int col, int digit){
        for(int i=0; i<GRID_SIZE; i++) {
            if(values[i][col] == digit) {
                return true;
            }
        }
        return false;
    }
    
    public boolean digitInRow(int row, int digit){
        for(int i=0; i<GRID_SIZE; i++) {
            if(values[row][i] == digit) {
                return true;
            }
        }
        return false;
    }
    
    public boolean digitInSubgrid(int row, int col, int digit){
        row /= 3;
        col /= 3;
        
        for(int i=row*3; i<(row+1)*3; i++) {
            for(int j=col*3; j<(col+1)*3; j++) {
                if(get(i,j) == digit) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int[][] values(){
        int[][] copy = new int[GRID_SIZE][GRID_SIZE];
        
        for(int i=0; i<GRID_SIZE; i++) {
            copy[i] = Arrays.copyOf(values[i], values[i].length);
        }
        
        return copy;
    }
}
