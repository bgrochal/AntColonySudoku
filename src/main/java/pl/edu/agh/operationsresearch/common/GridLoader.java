package pl.edu.agh.operationsresearch.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import pl.edu.agh.operationsresearch.grid.model.Grid;

public class GridLoader {
    public static Grid load(File file) {
        Grid grid = null;
        Scanner sc = null;
        int digit;
        int index = 0;
        
        if(file == null){
            return null;
        }

        try {
            sc = new Scanner(file);
            grid = new Grid();

            while (sc.hasNext()) {
                try {
                    digit = Integer.parseInt(sc.next());
                } catch (Exception e) {
                    digit = 0;
                }

                grid.set(index / 9, index % 9, digit);
                index++;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (index != Grid.GRID_SIZE * Grid.GRID_SIZE) {
            grid = null;
        }

        if (sc != null) {
            sc.close();
        }

        return grid;
    }
}
