package com.dksazxv.dskf.com.model;

import java.util.ArrayList;

public class Matrix<T> {
    private int mRows;
    private int mCols;
    private ArrayList<T> mArray;


    public Matrix(int rows, int cols) throws Exception {
        if(rows <= 0 || cols <= 0)
            throw new Exception("Error1: negative value: rows=" + rows + ";cols=" + cols);
        this.mCols = cols; this.mRows = rows;
        this.mArray = new ArrayList<>(rows * cols);
        for(int i = 0; i < rows * cols; ++i)
            this.mArray.add(null);
    }
    public Matrix(T[][] source) throws Exception {
        this.onSetMatrix(source);
    }

    public void onSetMatrix(T[][] source) throws Exception {
        if(source == null || source[0] == null)
            throw new Exception("Error2: null references source array");

        this.mCols  = source[0].length;
        this.mRows  = source.length;
        this.mArray = new ArrayList<>(this.mCols * this.mRows);

        for(int i = 0; i < this.mRows; ++i){
            if(source[i] == null)
                throw new Exception("Error2: null references source array");
            for(int j = 0; j < this.mCols; ++j){
                if(source[i][j] == null)
                    throw new Exception("Error3: null data references source array ");
                this.mArray.add(source[i][j]);
            }
        }
    }
    public T onGetItem(int indexRows, int indexCols) throws Exception{
        if(indexRows < 0 || indexRows >= this.mRows) throw new ArrayIndexOutOfBoundsException(indexRows);
        if(indexCols < 0 || indexCols >= this.mCols) throw new ArrayIndexOutOfBoundsException(indexCols);
        return this.mArray.get(indexRows * this.mCols + indexCols);
    }
    public void onSetItem(int indexRows, int indexCols, T value) throws Exception{
        if(indexRows < 0 || indexRows >= this.mRows) throw new ArrayIndexOutOfBoundsException(indexRows);
        if(indexCols < 0 || indexCols >= this.mCols) throw new ArrayIndexOutOfBoundsException(indexCols);
        this.mArray.set(indexRows * this.mCols + indexCols, value);
    }
    public int onGetRows(){
        return this.mRows;
    }
    public int onGetCols(){
        return this.mCols;
    }
}
