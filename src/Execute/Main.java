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
	public static void main(String[] args) throws Exception {
		OCRAntigen[] ags = new OCRAntigen[10];
		int h = 0;
		int w = 0;
		for (int i = 0; i < 10; i++) {
			BufferedImage imgBuffer = ImageIO
					.read(new File(System.getProperty("user.dir") + "/src/images/" + i + ".png"));
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
			//printNumber(temp, w, h);
			ags[i] = new OCRAntigen(temp);
		}
		ClonalgImpl clonalg = new ClonalgImpl();
												//           AB           AG  Ngen  n  d    L   Beta
		AntiBody<boolean[]>[] res = clonalg.main(clonalg.generate(10, 120), ags,100, 5, 3, 120, 10 );
		for (AntiBody<boolean[]> a: res) {
			System.out.println(a.getAffinity());
			printNumber(a.getData(), w, h);
		}
	}
	public static void printNumber(boolean[] in, int w, int h) {
		int index=0;
		for(int i=0; i<h;i++) {
			for(int j=0;j<w;j++) {
				System.out.print((in[index++]?"*":".") +" ");
			}
			System.out.println();
		}
		System.out.println("----------------------------");
	}
	
}
