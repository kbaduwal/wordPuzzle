package io.mkbaduwa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
    private int gridSize ;
    private char[][] contents;
    private List<Coordinate> coordinates = new ArrayList<>();

    private enum Direction{
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE

    }

    private class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public Grid(int gridSize){
        this.gridSize = gridSize;
        contents = new char[gridSize][gridSize];

        for (int i=0; i<gridSize; i++){
            for (int j=0; j<gridSize; j++){
                coordinates.add(new Coordinate(i,j));
                contents[i][j]='_';
            }
        }
    }

    public void fillGrid(List<String> words){
        Collections.shuffle(coordinates);
        for (String word : words) {
            for (Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(word,coordinate);
                if (selectedDirection != null) {
                    switch (selectedDirection){
                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;

                    }
                    break;

                }
            }
        }
        randomFillGrid();
    }

    public void displayGrid(){
        for (int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++){
                System.out.print(contents[i][j] +" ");
            }
            System.out.println(" ");
        }
    }

    private void randomFillGrid(){
        String allCapLetters ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++) {
                if(contents[i][j]=='_'){
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j] = allCapLetters.charAt(randomIndex);

                }
            }
        }
    }

    private Direction getDirectionForFit(String word, Coordinate coordinate){
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction: directions){
            if(doesFit(word,coordinate,direction)){
                return direction;
            }

        }
        return null;
    }

    private boolean doesFit(String word, Coordinate coordinate, Direction direction){
        int wordLength = word.length();
        switch (direction){
            case HORIZONTAL :
                if(coordinate.y + wordLength >gridSize) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x][coordinate.y+i]!='_'){
                        return false;
                    }
                }
                break;
            case VERTICAL:
                if(coordinate.x + wordLength >gridSize) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x+i][coordinate.y]!='_'){
                        return false;
                    }
                }
                break;
            case DIAGONAL:
                if(coordinate.y + wordLength >gridSize || coordinate.x + wordLength >gridSize) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x+i][coordinate.y+i]!='_'){
                        return false;
                    }
                }
                break;
            case HORIZONTAL_INVERSE:
                if(coordinate.y < wordLength) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x][coordinate.y-i]!='_'){
                        return false;
                    }
                }
                break;
            case VERTICAL_INVERSE:
                if(coordinate.x < wordLength) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x-i][coordinate.y]!='_'){
                        return false;
                    }
                }
                break;
            case DIAGONAL_INVERSE:
                if(coordinate.y  <wordLength || coordinate.x < wordLength) return false;
                for (int i=0; i< wordLength; i++){
                    if(contents[coordinate.x-i][coordinate.y-i]!='_'){
                        return false;
                    }
                }
                break;

        }
        return true;
    }

}
