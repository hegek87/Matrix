public class Matrix{
	private double[][] mat;
	int rows, cols;
	
	public Matrix(int row, int col){
		mat = new double[row][col];
		this.rows = row;
		this.cols = col;
	}
	
	public Matrix(double[][] mat){
		this.mat = mat;
		this.rows = mat.length;
		this.cols = mat[0].length;
	}
	
	public void gaussianElim(){}
	
	public void reducedRowEchelonForm() throws Exception{
		int[] currPivot = new int[]{0,0};
		while(!isEchelonForm()){
			int row = currPivot[0]+1;
			while(row < this.rows){
				//if(mat[currPivot[0]][currPivot[1]] == 0){ throw new ArithmeticException(); }
				double factor = -1 * (mat[row][currPivot[1]] / mat[currPivot[0]][currPivot[1]]);
				addMulRow(factor, currPivot[0], row);
				++row;
			}
			currPivot = findNextPivot(currPivot[0], currPivot[1]);
		}
	}
	
	public int[] findNextPivot(int currentPivotX, int currentPivotY){
		if(currentPivotX+1 < this.rows){
			++currentPivotX;
			while(zeroRow(currentPivotX)){
				++currentPivotX;
			}
		}
		if(currentPivotX < this.rows){
			while(mat[currentPivotX][currentPivotY] == 0){
				++currentPivotY;
			}
		}
		return new int[]{currentPivotX, currentPivotY};
	}
	
	
	public Matrix mult(Matrix mul){
		if(this.cols != mul.rows){ throw new IllegalStateException(); }
		Matrix result = new Matrix(this.rows, mul.cols);
		
		for(int row = 0; row < this.rows; ++row){
			for(int col = 0; col < mul.cols; ++col){
				result.mat[row][col] = mulRowCol(row, col, mul);
			}
		}
		
		return result;
	}
	
	private int mulRowCol(int row, int col, Matrix mul){
		int temp = 0; 
		for(int i = 0; i < this.cols; ++i){
			temp += this.mat[row][i] * mul.mat[i][col];
		}
		
		return temp;
	}
	
	public Matrix transpose(){
		Matrix result = new Matrix(this.cols, this.rows);
		for(int i = 0; i < this.rows; ++i){
			for(int j = 0; j < this.cols; ++j){
				result.mat[j][i] = this.mat[i][j];
			}
		}
		
		return result;
	}
	
	private boolean isEchelonForm(){
		int row = 0;
		while(row < this.rows){
			int i = 0;
			if(zeroRow(row)){
				if(row == this.rows-1){ return true; }
				if(!zeroRow(row+1)){
					return false;
				}
				return true;
			}
			while(mat[row][i] == 0){ 
				++i;
			}
			if(!isPivot(row, i)){ return false; }
			++row;
		}
		return true;
	}
	
	private boolean isReducedREF(){
		if(!isEchelonForm()){
			return false;
		}
		int row = 0;
		while(row < this.rows){
			int i = 0;
			if(zeroRow(row)){ return true; }
			while(mat[row][i] == 0){ ++i; }
			if(!zeroesAbove(row, i)){ return false; }
			++row;
		}
		return true;
	}
	
	private boolean zeroRow(int row){
		if(row >= this.rows){ return false; }
		for(int i = 0; i < mat[row].length; ++i){
			if(mat[row][i] != 0){ return false; }
		}
		return true;
	}
	
	private boolean isPivot(int x, int y){
		if(mat[x][y] == 0){ return false; }
		if(x>=this.rows){ return true; }
		for(int i = 0; i < rows-x-1; ++i){
			if(mat[x+i+1][y] != 0){ return false; }
		}
		return true;
	}
	
	private boolean zeroesAbove(int x, int y){
		if(mat[x][y] == 0){ return false; }
		for(int i = 0; i < x; ++i){
			if(mat[x-i-1][y] != 0){ return false; }
		}
		return true;
	}
	
	
	public void mulRow(double c, int row) throws Exception{
		if(c==0) throw new Exception();
		for(int i = 0; i < mat[row].length; ++i){
			mat[row][i] *= c;
		}
	}
	//adds c times row 1 to row 2 and stores the result in row 2
	public void addMulRow(double c, int row1, int row2) throws Exception{
		//if(c==0) throw new Exception();
		double[] temp = new double[mat[row1].length];
		for(int i = 0; i < temp.length; ++i){
			temp[i] = c * mat[row1][i];
		}
		
		for(int i = 0; i < mat.length; ++i){
			mat[row2][i] += temp[i];
		}
	}
	
	public void swapRows(int row1, int row2){
		double[] temp = mat[row1];
		mat[row1] = mat[row2];
		mat[row2] = temp;
	}
	
	@Override public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < mat.length; ++i){
			sb.append("[");
			for(int j=0; j < mat[i].length; ++j){
				sb.append(mat[i][j] + " | ");
			}
			sb.append("]\n");
		}
		
		return sb.toString();
	}
	
	public double getElement(int row, int col){
		return mat[row][col];
	}
	
	public static void main(String[] args)throws Exception{
		double[][] t = new double[][] {{1,0,0,0,-6},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,0,0}};
		double[][] t2 = new double[][] {{1,2,3}, {2,3,4}, {5, 6, 7}};
		double[][] t3 = new double[][] {{1,2,3,4,3},{2,3,4,5,5},{3,8,7,2,7},{1,1,1,10,2}};
		Matrix mat3 = new Matrix(t3);
		Matrix mat2 = new Matrix(t2);
		Matrix mat = new Matrix(t);
		//System.out.println(mat2.transpose());
		//System.out.println(mat + "*\n" + mat2 + "=\n" + mat.mult(mat2));
		System.out.println(mat3);
		mat3.reducedRowEchelonForm();
		System.out.println(mat3);
		
		mat2.reducedRowEchelonForm();
		System.out.println(mat2);
		mat.reducedRowEchelonForm();
		System.out.println(mat);
		//System.out.println(mat.isEchelonForm());
		//System.out.println(mat.isReducedREF());
		
		/*
		int[] currPivot = new int[]{0,0};
		int row = currPivot[0]+1;
		while(row < mat3.rows){
			double factor = -1*( mat3.mat[row][0] / mat3.mat[0][0]);
			mat3.addMulRow(factor, currPivot[0], row);
			++row;
		}
		currPivot = mat3.findNextPivot(currPivot[0],currPivot[1]);
		System.out.println(mat3);
		System.out.println(currPivot[0] + " " + currPivot[1]);
		row = currPivot[0]+1;
		while(row < mat3.rows){
			double factor = -1*( mat3.mat[row][currPivot[1]] / mat3.mat[currPivot[0]][currPivot[1]]);
			mat3.addMulRow(factor, currPivot[0], row);
			++row;
		}
		currPivot = mat3.findNextPivot(0,0);
		System.out.println(mat3);
		System.out.println(currPivot[0] + " " + currPivot[1]);
		System.out.println(mat3.isEchelonForm());
		*/
	}
}