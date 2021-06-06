package P1;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class MagicSquare {
	public static boolean isLegalMagicSquare(String fileName) {
		int i, j;
		int tarSum = 0;
		int curSum = 0;
		int temColInt = 0;
		int rowInt = 0;
		int colInt = 0;
		int sumFlag = 0;
		int val=0;
		int x = 0,y=0;
		List<Integer> magicList = new ArrayList<>();
		BufferedReader bw = null;
		try {
			bw = new BufferedReader(new FileReader(fileName));
		

			String line;
			while ((line = bw.readLine()) != null) {
				rowInt++;
				//�ָ��ж�
				try {
					String[] str2 = line.split("\t");
				} catch (Exception ex) {
					System.out.println("not be seperated by \t");
					return false;
				}
				String[] str2 = line.split("\t");
				if (temColInt == 0) {
					temColInt = str2.length;
				}
				//�Ƚ�ÿ��Ԫ�����Ƿ����
				if (temColInt != 0 && temColInt != str2.length) {
					System.out.println("false:numbers are missing");
					return false;
				}
				
				
				//�ж����Ƿ�Ϊ������
				for (j = 1; j <= str2.length; j++) {
					try {
						val = Integer.valueOf(str2[j - 1]);
					} catch (Exception ex) {
						System.out.println("The numbers in this matrix are not all integers");
						return false;
					}
					if (val <= 0) {
						System.out.println("The numbers in this matrix are not all positive");
						return false;
					}
					
					magicList.add(val);
				}
				

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
				}
			}
		}
		
		colInt = temColInt;
		if (colInt != rowInt) {
			System.out.println("false:not a square");
			return false;
		}
		//��arrayList��ɶ�ά����
		int magic[][]=new int[rowInt][colInt];
		for(i=0;i<magicList.size();++i) {
			magic[x][y]=magicList.get(i);
			if(y+1==colInt) {
				y=0;
				x+=1;
			}else {
				y+=1;
			}
		}
		for (j = 0; j < colInt; ++j) {
			tarSum += magic[0][j];
		}
		//ÿ�кͱȽ�
		for (i = 0; i < rowInt; ++i) {
			for (j = 0; j < colInt; ++j) {
				curSum += magic[i][j];
			}
			if (curSum != tarSum) {
				sumFlag = 1;
				break;
			}
			curSum = 0;
		}
		//ÿ�кͱȽ�
		for (j = 0; j < colInt; ++j) {
			for (i = 0; i < rowInt; ++i) {
				curSum += magic[i][j];
			}
			if (curSum != tarSum) {
				sumFlag = 1;
				break;
			}
			curSum = 0;
		}
		//�Խ���֮�ͱȽ�
		for (i = 0; i < rowInt; ++i) {
			curSum += magic[i][i];
		}
		if (curSum != tarSum) {
			sumFlag = 1;
		}
		curSum = 0;
		for (i = 0; i < rowInt; ++i) {
			curSum += magic[i][colInt-1-i];
		}
		if (curSum != tarSum) {
			sumFlag = 1;
		}
		curSum = 0;
		if (sumFlag == 1) {
			System.out.println("not a MagicSquare");
			return false;
		}
		System.out.println("true");
		return true;
	}

	public static boolean generateMagicSquare(int n) {
		if (n < 0) {
			System.out.println("false��n is negative");
			return false;
		}
		if (n % 2 == 0) {
			System.out.println("false��n is even");
			return false;
		}
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;//��1д�ڵ�һ�е��м�
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;//�Ի÷�������������
			if (i % n == 0)//����������n�ı�������һ��Ҫ�����λ������һ��
				row++;
			else {			//�����������ڵ�һ�л������һ�У���һ��Ҫ���������-1 ����+1
				if (row == 0)//���Ҫ������ڵ�һ�л������һ�У���һ��Ҫ����������һ�л��ߵ�һ��
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("src/P1/txt/6.txt");
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					fileWriter.write(String.valueOf(magic[i][j]) + "\t");
				}
				fileWriter.write("\n");
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public static void main(String[] args) {

		System.out.println("Case1:");
		isLegalMagicSquare("src/P1/txt/1.txt");
		System.out.println("Case2:");
		isLegalMagicSquare("src/P1/txt/2.txt");
		System.out.println("Case3:");
		isLegalMagicSquare("src/P1/txt/3.txt");
		System.out.println("Case4:");
		isLegalMagicSquare("src/P1/txt/4.txt");
		System.out.println("Case5:");
		isLegalMagicSquare("src/P1/txt/5.txt");
		Scanner input = new Scanner(System.in);
		System.out.println("please input an odd number��");
		int length = input.nextInt();
		if (generateMagicSquare(length)) {
			isLegalMagicSquare("src/P1/txt/6.txt");
		}

	}

}
