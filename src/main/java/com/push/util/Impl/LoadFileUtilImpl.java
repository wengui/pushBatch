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
import java.util.Calendar;
import java.util.Date;
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
	 * 日期格式
	 */
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

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
				String patientName = "";// 患者姓名
				int age = 0;// 患者年龄
				while ((lineTxt = bufferedReader.readLine()) != null) {

					if (lineTxt.startsWith("H")) {
						// 取得测试时间
						testtime = lineTxt.substring(6, 20);
					} else if (lineTxt.startsWith("P")) {

						int nameStart = getCharacterPosition(lineTxt, 5);
						int nameEnd = getCharacterPosition(lineTxt, 6);
						int ageStart = getCharacterPosition(lineTxt, 7);
						int ageEnd = getCharacterPosition(lineTxt, 8);
						String birthday = "";
						String name = "";

						// 取得患者姓名
						name = lineTxt.substring(nameStart + 1, nameEnd);
						if ("".equals(name) || name == null) {
							// 如果患者姓名没有取到的场合
							patientName = "患者";
						} else {
							patientName = name;
						}
						// 患者年龄
						birthday = lineTxt.substring(ageStart + 1, ageEnd);

						// 患者年龄
						if ("".equals(birthday) || birthday == null) {
							// 如果年龄未取到的场合
							age = 0;
						} else {
							age = getAgeByBirthday(sdf1.parse(birthday));
						}

					} else if (lineTxt.startsWith("R") && !lineTxt.contains("Control")) {
						NanoCheckerResult result = new NanoCheckerResult();
						result.setPatientname(patientName);// 患者姓名
						result.setTesttime(sdf.parse(testtime));// 测试时间
						result.setAge(age);// 患者年龄

						// 项目名称设定
						int lastIndex = lineTxt.lastIndexOf("^");
						// 操作内容的字符串
						String itemnameTxt = lineTxt.substring(lastIndex + 1, lineTxt.length());
						int itemnameTxtIndex = itemnameTxt.indexOf("|");

						if (!(itemnameTxtIndex == -1)) {
							result.setItemname(itemnameTxt.substring(0, itemnameTxtIndex));// 项目名称
						}

						// 项目值设定
						String valueTxt = itemnameTxt.substring(itemnameTxtIndex + 1, itemnameTxt.length());
						int valueTxtIndex = valueTxt.indexOf("|");

						if (!(valueTxtIndex == -1)) {
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
	 * 
	 * @param originalPath
	 *            原文件路径
	 * @param newPath
	 *            复制目的路径
	 * @return true:成功； false：失败
	 */
	public void move(String originalPath, String newPath) {

		File fold = new File(originalPath);// 某路径下的文件
		File fnewpath = new File(newPath);
		// 如果目标目录不存在，则拷贝
		if (!fnewpath.exists())
			fnewpath.mkdirs();
		File fnew = new File(newPath + fold.getName());
		fold.renameTo(fnew);
	}

	/**
	 * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
	 * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
	 * 
	 * @param path
	 *            path为该目录的路径
	 */
	public boolean deleteDirectory(String path) {
		boolean flag = true;
		File dirFile = new File(path);

		flag = dirFile.delete(); // 删除空目录
		return flag;
	}

	/**
	 * 根据用户生日计算年龄
	 */
	public int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("出生日期不是今天之前的日期！");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 字符串第N次出现的位置
	 * 
	 * @param string
	 * @return
	 */
/*	public int getCharacterPosition(String string, int times) {
		// 这里是获取"/"符号的位置
		Matcher slashMatcher = Pattern.compile("|").matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"/"符号第N次出现的位置
			if (mIdx == times) {
				break;
			}
		}
		int nextIndex = slashMatcher.start();
		System.out.println(nextIndex);

		return slashMatcher.start();
	}*/

	public int getCharacterPosition(String string, int times)
	{
		int count = 0;
		int num = 0;
		char arr[] = string.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if ('|' == arr[i]) {
				count++;
				if (times == count) {
					num = i;
					break;
				}
			}
		}
		return num;
	}
}
