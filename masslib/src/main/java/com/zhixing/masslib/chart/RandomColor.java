/**
 * @Title:RandomColor.java
 * @Package:com.fusionchart.model
 * @Description:生成随机颜色
 * @version V1.0
 */
package com.zhixing.masslib.chart;
 
import java.util.Random;
 
/**

 */
public class RandomColor {
 
	/**
	 * @Title:main
	 * @Description:生成随机颜色
	 * @param:@param args
	 * @return: void
	 * @throws
	 */
	public static void main(String[] args) 
	{
		//红色
		String red; 
		//绿色
		String green;
		//蓝色
		String blue;
		//生成随机对象
		Random random = new Random();  
		//生成红色颜色代码
		red = Integer.toHexString(random.nextInt(256)).toUpperCase();
		//生成绿色颜色代码
		green = Integer.toHexString(random.nextInt(256)).toUpperCase(); 
		//生成蓝色颜色代码
		blue = Integer.toHexString(random.nextInt(256)).toUpperCase();  
	       
		//判断红色代码的位数
		red = red.length()==1 ? "0" + red : red ;  
		//判断绿色代码的位数
		green = green.length()==1 ? "0" + green : green ; 
		//判断蓝色代码的位数
		blue = blue.length()==1 ? "0" + blue : blue ;
		//生成十六进制颜色值
		String color = "#"+red+green+blue;
		
		System.out.println(color);  
	}
}
