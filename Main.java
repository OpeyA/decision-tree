import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static ArrayList<String> attributes = new ArrayList<String>();
	private static ArrayList<Integer> initialInput = new ArrayList<Integer>();
	private static ArrayList<DataPiece> dataInput = new ArrayList<DataPiece>();
	private static int numData;
	private static int numAtts;
	private static double infoGain;
	private static boolean solved = true;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Welcome to my program.");
		System.out.println("Please enter a file name");
		Scanner input = new Scanner(System.in);
		//File file = new File(input.nextLine());
		File file = new File("test1.txt");
		Scanner fileRead = new Scanner(file);
		while(fileRead.hasNext()){
			String temp = fileRead.next();
			attributes.add(temp);
			if(temp.equals(temp.toUpperCase()))
				break;
		}
		numAtts = attributes.size();

		//System.out.println(fileRead.next());
		while(fileRead.hasNext()){
			int[] tempIntArr = convertToUse(fileRead.next());
			for(int i = 0; i < tempIntArr.length; i++){
				Integer tempINT = new Integer(tempIntArr[i]);
				initialInput.add(tempINT);
				dataInput.add(new DataPiece(tempIntArr));
				
			}
		}
		numData = dataInput.size();
		/*int[][] trainingData = new int[numData][numAtts];
		for(int i = 0; i < numData; i++){
			for(int j = 0; j < numAtts; j++){
				trainingData[i][j] = initialInput.get(0).intValue();
				initialInput.remove(0);
			}
		}*/
		for(String j: attributes)
			System.out.println(j);
	
		/*for(int i = 0; i < numData; i++){
			for(int j = 0; j < numAtts; j++){
				System.out.print(trainingData[i][j] + " ");
			}
			System.out.println("\n");
		}*/
		for(DataPiece i: dataInput){
			System.out.println(i);
		}
		calcInfoGain(dataInput);
		System.out.println(infoGain);
		/*double maxInfoGain = calcAttInfoGain(0, trainingData);*/
		double maxInfoGain = calcAttInfoGain(0,dataInput);
		int numAttOfMax = 0;
		double newCalc;
		System.out.println(maxInfoGain);
		for(int i = 1; i < numAtts-1; i++){
			/*newCalc = calcAttInfoGain(i, trainingData);*/
			newCalc = calcAttInfoGain(i,dataInput);
			if(newCalc > maxInfoGain){
				maxInfoGain = newCalc;
				numAttOfMax = i;
			}
			System.out.println(newCalc);
		}
		System.out.println(attributes.get(numAttOfMax));
		System.out.println();
		Node root = new Node(attributes.get(numAttOfMax));
		while(!solved){
			
		}
		
	}
	
	
	public static double calcAttInfoGain(int pNumAtt, ArrayList<DataPiece> pData){
		double numTrue = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 1)
				numTrue++;
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue()){
				numTrue++;
			}
		}
		double attTrue = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][pNumAtt] == 1)
				attTrue++;
		}*/
		for(DataPiece i: pData){
			if(i.getDataIndex(pNumAtt) == 1){
				attTrue++;
			}
		}
		double attAndFinTrue = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 1 && pData[i][pNumAtt] == 1)
				attAndFinTrue++;
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue() && i.getDataIndex(pNumAtt) == 1){
				attAndFinTrue++;
			}
		}
		double attTrueFinFalse = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 0 && pData[i][pNumAtt] == 1)
				attTrueFinFalse++;
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue() == 0 && i.getDataIndex(pNumAtt) == 1){
				attTrueFinFalse++;
			}
		}
		double attFalseFinTrue = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 1 && pData[i][pNumAtt] == 0){
				attFalseFinTrue++;
			}
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue() == 1 && i.getDataIndex(pNumAtt) == 0){
				attFalseFinTrue++;
			}
		}
		double attAndFinFalse = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 0 && pData[i][pNumAtt] == 0)
				attAndFinFalse++;
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue() == 0 && i.getDataIndex(pNumAtt) == 0){
				attAndFinFalse++;
			}
		}
		double retVal;
		if(attTrue == 0){
			if(attFalseFinTrue == 0){
				retVal = ((double)(numData-attTrue)/(double)numData) * 
						(-1) * (attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2));
			}
			else if(attAndFinFalse == 0){
				retVal = ((double)(numData-attTrue)/(double)numData) * 
						((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2)));
			}
			else{
			retVal = ((double)(numData-attTrue)/(double)numData) * 
					(((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2)) -
					(attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2))));
			}
		}
		else if((numData-attTrue) == 0){
			if(attAndFinTrue == 0){
				retVal = (attTrue/(double)numData) * 
				(-1) * ((attTrueFinFalse/attTrue) * (Math.log(attTrueFinFalse/attTrue)))/Math.log(2);
			}
			else if(attTrueFinFalse == 0){
				retVal = (attTrue/(double)numData) * 
				((-1) * (attAndFinTrue/attTrue) * (Math.log(attAndFinTrue/attTrue))/Math.log(2));
			}
			else{
			retVal = (attTrue/(double)numData) * 
					(((-1) * (attAndFinTrue/attTrue) * (Math.log(attAndFinTrue/attTrue))/Math.log(2)) -
					((attTrueFinFalse/attTrue) * (Math.log(attTrueFinFalse/attTrue)))/Math.log(2));
			}
		}
		else{
			if(attAndFinTrue == 0){
				retVal = (attTrue/(double)numData) * 
				((-1) * (attTrueFinFalse/attTrue) * ((Math.log(attTrueFinFalse/attTrue))/Math.log(2))) +
				((double)(numData-attTrue)/(double)numData) * 
				(((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2)) -
				(attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2))));;
			}
			else if(attTrueFinFalse == 0){
				retVal = (attTrue/(double)numData) * 
				(((-1) * (attAndFinTrue/attTrue) * ((Math.log(attAndFinTrue/attTrue))/Math.log(2)))) +
				((double)(numData-attTrue)/(double)numData) * 
				(((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2)) -
				(attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2))));		
			}
			else if(attFalseFinTrue == 0){
				retVal = (attTrue/(double)numData) * 
				(((-1) * (attAndFinTrue/attTrue) * ((Math.log(attAndFinTrue/attTrue))/Math.log(2)))) -
				(((attTrueFinFalse/attTrue) * ((Math.log(attTrueFinFalse/attTrue))/Math.log(2)))) +
				((double)(numData-attTrue)/(double)numData) * 
				((-1) * attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2));
			}
			else if(attAndFinFalse == 0){
				retVal = (attTrue/(double)numData) * 
				(((-1) * (attAndFinTrue/attTrue) * ((Math.log(attAndFinTrue/attTrue))/Math.log(2)))) -
				(((attTrueFinFalse/attTrue) * ((Math.log(attTrueFinFalse/attTrue))/Math.log(2)))) +
				((double)(numData-attTrue)/(double)numData) * 
				(((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2))));
			}
			else{
				retVal = (attTrue/(double)numData) * 
				((-1) * (attAndFinTrue/attTrue) * ((Math.log(attAndFinTrue/attTrue))/Math.log(2)) -
				((attTrueFinFalse/attTrue) * ((Math.log(attTrueFinFalse/attTrue))/Math.log(2)))) +
				((double)(numData-attTrue)/(double)numData) * 
				(((-1) * (attFalseFinTrue/(double)(numData-attTrue)) * ((Math.log(attFalseFinTrue/(double)(numData-attTrue)))/Math.log(2))) -
				((attAndFinFalse/(double)(numData-attTrue)) * (Math.log(attAndFinFalse/(double)(numData-attTrue))/Math.log(2))));
			}
		}	
		retVal = infoGain - retVal;

		return retVal;
	}
	
	public static void calcInfoGain(ArrayList<DataPiece> pData){
		int numTrue = 0;
		/*for(int i = 0; i < numData; i++){
			if(pData[i][numAtts-1] == 1)
				numTrue++;
		}*/
		for(DataPiece i: pData){
			if(i.isPieceValue()){
				numTrue++;
			}
		}
		double trueOverTot = ((double)numTrue/(double)numData);
		double falseOverTot = ((double)numData-(double)numTrue)/(double)numData;
		infoGain = ((-1)*(trueOverTot) * (Math.log(trueOverTot))/(Math.log(2))) - 
				((falseOverTot) * (Math.log(falseOverTot)/Math.log(2)));
		
	}
	
	
	public static int[] convertToUse(String pString){
		String temp = pString.replace(',', ' ');
		char[] strToChar;
		int[] tempI = new int[((temp.length()/2) + 1)];
		strToChar = temp.toCharArray();
		int intTemp = 0;
		for(int i = 0; i < strToChar.length; i+=2){
			tempI[intTemp] = (int)strToChar[i] - 48;
			intTemp++;
		}
		return tempI;
	}
}
