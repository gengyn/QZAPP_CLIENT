package com.qingzhou.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * 文件操作相关工具
 * @author hihi
 *
 */
public class FileUtils {
	
	
	/**
	 * 检查SD卡
	 * @param sdPath
	 * @return
	 */
	public static boolean checkSD(String sdPath)
	{	
		return mkDir(sdPath);
	}
	/**
	 * 建立目录，非只读
	 * @param dirPath
	 * @return
	 */
	public static boolean mkDir(String dirPath)
	{
		return mkDir(dirPath,false);
	}
	
	/**
	 * 如目录目录不存在，则建立，并且可设置该目录是否只读
	 * @param dirPath  目录路径
	 * @param isReadOnly  是否只读
	 * @return
	 */
	public static boolean mkDir(String dirPath,boolean isReadOnly)
	{
		boolean isOk = true;
		try{
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) isOk = dirFile.mkdirs();
			if (isReadOnly) dirFile.setReadOnly();
			return isOk;
		}catch(Exception ex)
		{
			System.err.println(ex.toString());
			return isOk;
		}
	}
	/**
	 * 读取文件，返回文件内容的字符串
	 * @param filePath 源文件路径
	 * @return
	 */
	public static String readFile(String filePath)
	{
		StringBuilder fileSb = new StringBuilder();
		try{
			FileReader fr = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(fr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileSb.append(line + "\n");// 按行读取放入StringBuilder中
			}
			reader.close();
		}catch(IOException ioex)
		{
			System.err.println(ioex.toString());
		}
		return fileSb.toString();
	}
	/**
	 * 写文件方法
	 * @param filePath 文件地址
	 * @param content 文件内容
	 * @return
	 * @throws IOException
	 */
	public static boolean writeFile(String filePath,String content)
	{
		try
		{
			FileWriter fw = new FileWriter(filePath);
			PrintWriter out = new PrintWriter(fw);
			out.write(content);
			out.println();
			fw.close();
			out.close();
			return true;
		}catch(IOException ioex)
		{
			System.err.println(ioex.toString());
			return false;
		}
	
	}
	
	/**
	 * 将输入流写入指定文件
	 * @param dirPath
	 * @param filename
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String dirPath,String filename,InputStream content)
	{
		try
		{
			File dir = new File(dirPath);
			if (!dir.exists())
				dir.mkdir();
			File f = new File(dirPath+filename);
			FileOutputStream fos = new FileOutputStream(f);
			byte buf[] = new byte[1024];
			int numread = 0;
			while((numread = content.read(buf))!=-1){   		   		
	    		fos.write(buf,0,numread);
	    	};
	    	fos.flush();
	    	fos.close();
			return true;
		}catch(IOException ioex)
		{
			System.err.println(ioex.toString());
			return false;
		}
	
	}
	/**
	 * 删除某一个文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath)
	{
		File delFile = new File(filePath);
		if (delFile.exists() || delFile.canWrite())
			return delFile.delete();
		else	
			return false;
	}
	/**
     * 递归删除整个目录
     * @param savePath
     * @return
     */
    public static void deleteDir(String savePath) {
    	File f = new File(savePath);//定义文件路径         
    	if(f.exists() && f.isDirectory()){//判断是文件还是目录  
    		if(f.listFiles().length==0){//若目录下没有文件则直接删除  
    			f.delete();  
    		}else{//若有则把文件放进数组，并判断是否有下级目录  
    			File delFile[]=f.listFiles();  
    			int i =f.listFiles().length;  
    			for(int j=0;j<i;j++){  
    				if(delFile[j].isDirectory()){  
    					deleteDir(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径  
    				}  
    				delFile[j].delete();//删除文件  
    			}  
    		}  
    	}
    }  
    
    /**
     * 判断文件是否存在
     * @param filepath
     * @return
     */
    public static boolean isExists(String filepath)
    {
    	File f = new File(filepath);
    	return f.exists();
    }

}
