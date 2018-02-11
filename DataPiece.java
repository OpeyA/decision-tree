
public class DataPiece {
	private int[] data;
	private boolean pieceValue;
	
	public DataPiece(int[] pData){
		data = pData;
		if(data[data.length-1] == 1){
			setPieceValue(true);
		}
		else
			setPieceValue(false);
	}
	
	public int getDataIndex(int pIndex){
		return data[pIndex];
	}
	
	public void setDataIndex(int pIndex, int pData){
		data[pIndex] = pData;
	}
	
	public int[] getData(){
		return data;
	}
	
	public String toString(){
		String temp = "";
		for(int i = 0; i < data.length; i++){
			temp += getDataIndex(i) + " ";
		}
		return temp;
	}

	public boolean isPieceValue() {
		return pieceValue;
	}

	public void setPieceValue(boolean pieceValue) {
		this.pieceValue = pieceValue;
	}
}
