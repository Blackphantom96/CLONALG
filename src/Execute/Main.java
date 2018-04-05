package Execute;

import Model.Abstraction.AntiBody;
import Model.impl.ClonalgImpl;
import Model.impl.OCRAntiBody;
import Model.impl.OCRAntigen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class Main {
	static int h = 0;
	static int w = 0;

	public static void main(String[] args) throws Exception {
		OCRAntigen[] ags = new OCRAntigen[10];
		ClonalgImpl clonalg = new ClonalgImpl();
		final int MAXCHAR = 1;
		ArrayList<AntiBody<boolean[]>[]> trainedAB = new ArrayList<>();
		for (int folder = 0; folder <= MAXCHAR; folder++) {
			System.out.println("char: " + folder);

			for (int i = 1; i < 11; i++) {
				// printNumber(temp, w, h);
				ags[i - 1] = new OCRAntigen(getAntigen(folder, i));
			}
			clonalg = new ClonalgImpl();
			// AB AG Ngen n d L Beta
			AntiBody<boolean[]>[] res = clonalg.main(clonalg.generate(10, h * w), ags, 1900, 5, 3, h * w, 10);
			trainedAB.add(res);
			for (AntiBody<boolean[]> a : res) {
				System.out.println(a.getAffinity());
				// printNumber(a.getData(), w, h);
			}
		}
		Scanner a = new Scanner(System.in);
		while (true) {
			for (int folder = 0; folder <= MAXCHAR; folder++) {
				System.out.println("Char: " + folder + "\n insert image number of folder " + folder);
				int image = a.nextInt();
				OCRAntigen reg = new OCRAntigen(getAntigen(folder, image));
				int x=0;
				for (AntiBody<boolean[]>[] abs : trainedAB) {
					int promed = 0;
					for (AntiBody<boolean[]> ab : abs) {
						promed += ((clonalg.calculateAffinity(ab, reg) / (h * w)) * 100);
					}
					promed /= abs.length;
					promed = 100-promed;
					System.out.println(x++ + " " + promed + "%");
				}
			}
		}

	}

	public static void printNumber(boolean[] in, int w, int h) {
		int index = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				System.out.print((in[index++] ? "*" : ".") + "");
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < w; i++)
			System.out.print("+");
		System.out.println("\n");
	}

	public static boolean[] getAntigen(int folder, int i) throws IOException {
		BufferedImage imgBuffer = ImageIO
				.read(new File(System.getProperty("user.dir") + "/src/DataSet/" + folder + "/" + i + ".png"));
		int index = 0;
		h = imgBuffer.getHeight();
		w = imgBuffer.getWidth();
		boolean[] temp = new boolean[h * w];
		for (int j = 0; j < h; j++) {
			for (int k = 0; k < w; k++) {
				int x = (imgBuffer.getRGB(k, j) + 1) / -16777215;
				temp[index++] = x == 1;
			}
		}
		return temp;
	}

}
