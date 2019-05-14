package com.base.zhixing.www.common;

import android.content.Context;
import android.util.Base64;

import com.base.zhixing.www.util.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;

public class FileUtils {

	// 递归方式 计算文件的大小
	public static long getTotalSizeOfFilesInDir(final File file) {
		if (file.isFile())
			return file.length();
		final File[] children = file.listFiles();
		long total = 0;
		if (children != null)
			for (final File child : children)
				total += getTotalSizeOfFilesInDir(child);
		return total;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		if (fileS == 0) {
			return "0.00";
		}
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	/**
	 * 打印参数表，排查错误用
	 * @param parsm
	 */
	public static void parms(Map  parsm){
		Set<String> ey = parsm.keySet();
		Iterator it = ey.iterator();
		JSONObject object =new JSONObject();
		while(it.hasNext()){
			String key = it.next().toString();
			P.c(key+"==="+parsm.get(key));
			try {
				object.put(key,parsm.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		P.c(object.toString());
	}
	/**
	 * 打印参数表，排查错误用
	 * @param parsm
	 */
	public static String parmsRet(Map  parsm){
		Set<String> ey = parsm.keySet();
		Iterator it = ey.iterator();
		JSONObject object =new JSONObject();
		while(it.hasNext()){
			String key = it.next().toString();
			P.c(key+"==="+parsm.get(key));
			try {
				object.put(key,parsm.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return  object.toString();
	}
	/**
	 * 文件转base64字符串
	 * @param file
	 * @return
	 */
	public static String fileToBase64(File file) {
		String base64 = null;
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] bytes = new byte[in.available()];
			int length = in.read(bytes);
			base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return base64;
	}



	public static <T> T clone(T obj) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bout);
		oos.writeObject(obj);

		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bin);
		return (T) ois.readObject();

		// 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
		// 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
	}


	public static Map<String,Integer> formatStr(String txt){
		int numbers, capitalLetters, smallLetters, others;
		numbers = capitalLetters = smallLetters = others = 0;
		Map<String,Integer> map = new HashMap<>();

		for (int i = 0; i < txt.length(); i++)

		{

			char c = txt.charAt(i);

			if (Character.isLowerCase(c))

			{

				smallLetters++;

			}

			else if (Character.isDigit(c))

			{

				numbers++;

			}

			else if (Character.isUpperCase(c))

			{

				capitalLetters++;

			}

			else

			{

				others++;

			}

		}

		/*System.out.println("numbers =" + numbers);

		System.out.println("capitalLetters =" + capitalLetters);

		System.out.println("smallLetters =" + smallLetters);

		System.out.println("others =" + others);*/

		map.put("en",numbers+capitalLetters+smallLetters);
		map.put("zh",others);

		return map;
	}
	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}

	/**
	 *
	 * @param zipFile
	 *            源文件
	 * @param folderPath
	 *            目标目录
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */

	// 读取指定路径文本文件
	public static synchronized String read(String filePath) {
		StringBuilder str = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String s;
			try {
				while ((s = in.readLine()) != null)
					str.append(s + '\n');
			} finally {
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.toString();
	}
	public static   boolean deleteDir(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	public static  void addIgnore(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		File ignoreFile = new File(filePath + "/.nomedia");
		if (ignoreFile.exists() && ignoreFile.isFile()) {
			return;
		}

		try {
			ignoreFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 新名字和旧名字一样就生成，不一样就重新生成
	 * @param file
	 * @param fileNs
	 * @param childFileName
	 * @return
	 */
	public static String name(File file, String fileNs[], String childFileName){
		if(fileNs!=null){
			for(int i=0;i<fileNs.length;i++){
				if(fileNs[i].substring(0,fileNs[i].lastIndexOf(".")).equals(childFileName)){
					return childFileName;
				}
			}
			if(fileNs.length!=0){
				deleteDir(file);
			}
		}
		//
		return childFileName;
	}

	public static void writeLog(String text,String tag) {
		String childFileName = TimeUtil.getTimeLog(System.currentTimeMillis());
		String logPath = Common.LOG_DIR;
		File file = new File(Common.LOG_DIR);
		if(!file.exists()){
			file.mkdirs();
			//不存在就创建目录
		}
		String fileNs[] = file.list();

		String realName = name(file, fileNs, childFileName)+".txt";
		if (text == null)
			return;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(logPath+realName,
					true));
			try {
				out.write(tag+"-----"+TimeUtil.getTime(System.currentTimeMillis())+"\n");
				out.write(text);
				out.write("\n");
			} finally {
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	// 写入指定的文本文件，append为true表示追加，false表示重头开始写，
	// text是要写入的文本字符串，text为null时直接返回
	public static void write(String filePath, boolean append, String text) {
		if (text == null)
			return;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
					append));
			try {
				out.write(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获得指定文件的byte数组
	 */
	private byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath+"\\"+fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public static double add(double m1, double m2) {
  BigDecimal p1 = new BigDecimal(Double.toString(m1));
 BigDecimal p2 = new BigDecimal(Double.toString(m2));
  return p1.add(p2).doubleValue();
  }


	/**
	 * 格式化double数据
	 */
	public static double formatDouble(double fromDouble){
		BigDecimal b = new BigDecimal(fromDouble);
		// 保留2位小数
		double targetDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return targetDouble;
	}


	public static float formatFloat(float fromDouble){
		float targetDouble = 0;
		try {
			BigDecimal b = new BigDecimal(fromDouble);
			// 保留2位小数
			targetDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP)
				  .floatValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return targetDouble;
	}
	/**
	 * 433
	 *
	 * @param temp
	 * @return
	 */
	public static String getReals(String temp) {
		return temp.replaceAll("^(0+)", "");
	}

	/**
	 * 格式化json
	 * @return
	 * @throws JSONException
	 */
	public static String formatJson(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("d");
	}


	public static String formatImageUrl(String url){
		return  url.replace("\\","/");
	}

}
