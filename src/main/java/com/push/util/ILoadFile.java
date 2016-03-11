package com.push.util;

import java.util.ArrayList;

import com.push.bean.gen.NanoCheckerResult;

public interface ILoadFile {

	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public ArrayList<NanoCheckerResult> readTxtFile(String filePath);
	
	/**
	 * 返回文件夹下文件列表
	 * 
	 * @param path
	 * @return
	 */
	public ArrayList<String> getAllFileName(String path);
	
	/**
	 * 取得目标文件夹下的所有文件名称
	 * 
	 * @param path
	 * @return 文件名称
	 */
	public String[] getFileName(String path);
	
	/**
	 * 
	 * @param originalPath
	 *            原文件路径
	 * @param newPath
	 *            复制目的路径
	 * @return true:成功； false：失败
	 */
	public boolean copy(String originalPath, String newPath);
	
	/**
	 * 从旧文件夹移动到新文件夹
	 * @param originalPath
	 *            原文件路径
	 * @param newPath
	 *            复制目的路径
	 * @return true:成功； false：失败
	 */
	public void move(String originalPath, String newPath);
	
    /** 
     * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹 
     * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete 
     *  
     * @param path 
     *            path为该目录的路径 
     */  
	public boolean deleteDirectory(String path);
}
