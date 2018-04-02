package Execute;

import Model.impl.ClonalgImpl;
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
		for (int i = 0; i < 10; i++) {
			if (i != 5 && i != 7) {
				BufferedImage imgBuffer = ImageIO
						.read(new File(System.getProperty("user.dir") + "/src/images/" + i + ".png"));
				int index = 0;
				int h = imgBuffer.getHeight();
				int w = imgBuffer.getWidth();
				boolean[] temp = new boolean[h * w];
				for (int j = 0; j < h; j++) {
					for (int k = 0; k < w; k++) {
						int x = (imgBuffer.getRGB(k, j) + 1) / -16777215;
						System.out.print( x + " ");
						temp[index++] = x==0 ;
					}
					System.out.println();
				}
				System.out.println("---------------------------------------");
				// System.out.println(Arrays.toString(temp));
			}
		}

	}
}
