package com.push.util.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.push.bean.gen.NanoCheckerResult;
import com.push.util.ILoadFile;

/**
 * @author
 */
@Component
public class LoadFileUtilImpl implements ILoadFile {

	/**
	 * 缓冲区大小
	 */
	private static final Integer size = 1024;
	/**
	 * 复制文件类型
	 */
	private static final String types = ".txt;";
	
	/**
	 * 日期格式
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  

	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public ArrayList<NanoCheckerResult> readTxtFile(String filePath) {
		ArrayList<NanoCheckerResult> resultList = null;
		try {
			
			resultList = new ArrayList<NanoCheckerResult>();
			
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				String testtime = "";
				String patientName = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {

					if(lineTxt.startsWith("H")){
						// 取得测试时间
						testtime = lineTxt.substring(6, 20);
					}else if(lineTxt.startsWith("P")){
						// 取得患者姓名
						int lastIndex = lineTxt.lastIndexOf("|");
						if(!(lastIndex == - 1)){
							patientName = lineTxt.substring(lastIndex + 1, lineTxt.length());
						}
						
					}else if(lineTxt.startsWith("R") && !lineTxt.contains("Control")){
						NanoCheckerResult result = new NanoCheckerResult();
						result.setPatientname(patientName);// 患者姓名
						result.setTesttime(sdf.parse(testtime));// 测试时间
						//TODO 现在年龄还没有设置
						result.setAge(25);// 患者年龄
						
						// 项目名称设定
						int lastIndex = lineTxt.lastIndexOf("^");
						// 操作内容的字符串
						String itemnameTxt = lineTxt.substring(lastIndex + 1, lineTxt.length());
						int itemnameTxtIndex = itemnameTxt.indexOf("|");
						
						if(!(itemnameTxtIndex == - 1)){
							result.setItemname(itemnameTxt.substring(0, itemnameTxtIndex));// 项目名称
						}
						
						// 项目值设定
						String valueTxt = itemnameTxt.substring(itemnameTxtIndex+1, itemnameTxt.length());
						int valueTxtIndex = valueTxt.indexOf("|");
						
						if(!(valueTxtIndex == - 1)){
							result.setValue(valueTxt.substring(0, valueTxtIndex));// 项目名称
						}
						
						// 从文中读出的结果设定到list中
						resultList.add(result);
					}
				}
				read.close();
			} else {
				// 文件读取失败
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		return resultList;

	}

	/**
	 * 返回文件夹下文件列表
	 * 
	 * @param path
	 * @return
	 */
	public ArrayList<String> getAllFileName(String path) {
		ArrayList<String> fileName = new ArrayList<String>();
		File file = new File(path);
		String[] names = file.list();
		if (names != null)
			fileName.addAll(Arrays.asList(names));

		return fileName;
	}

	/**
	 * 取得目标文件夹下的所有文件名称
	 * 
	 * @param path
	 * @return 文件名称
	 */
	public String[] getFileName(String path) {
		// 取得文件列表
		File file = new File(path);
		String[] fileName = file.list();
		return fileName;
	}

	/**
	 * 
	 * @param originalPath
	 *            原文件路径
	 * @param newPath
	 *            复制目的路径
	 * @return true:成功； false：失败
	 */
	public boolean copy(String originalPath, String newPath) {
		File file = new File(originalPath);
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		boolean flag = true;
		if (types.indexOf(fileType) > -1) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				byte[] b = new byte[size];
				int i = 0;
				File oFile = new File(newPath + fileName);
				fos = new FileOutputStream(oFile);
				if (!oFile.exists()) {
					oFile.createNewFile();
				}
				while ((i = fis.read(b)) != -1) {
					fos.write(b);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				flag = false;
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("无匹配文件类型");
			flag = false;
		}
		return flag;
	}

	/**
	 * 从旧文件夹移动到新文件夹
	 * @param originalPath
	 *            原文件路径
	 * @param newPath
	 *            复制目的路径
	 * @return true:成功； false：失败
	 */
	public void move(String originalPath, String newPath){
		
		   File fold = new File(originalPath);//某路径下的文件
		   File fnewpath = new File(newPath);
		   if(!fnewpath.exists())
		     fnewpath.mkdirs();
		   File fnew = new File(newPath+fold.getName());
		   fold.renameTo(fnew);
		 }
	
}
