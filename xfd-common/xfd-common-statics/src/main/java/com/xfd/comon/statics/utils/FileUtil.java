package com.xfd.comon.statics.utils;

import java.io.File;

/**
 * 文件工具类
 * @author xfd
 *
 * 2018年12月27日
 *
 */
public class FileUtil
{
	private FileUtil() { }

	/**
	 * 获取临时路径
	 * @return
	 */
	public static File getTempFilePath()
	{
		String folder = System.getProperty("java.io.tmpdir");
		File tmpFolder = new File(folder, CommonUtil.getUUID());
		if (!tmpFolder.exists())
		{
			if (!tmpFolder.mkdirs())
			{
				throw new RuntimeException("创建临时目录[" + tmpFolder.getAbsolutePath() + "]失败...");
			}
		}
		return tmpFolder;
	}

	/**
	 * 在临时目录内创建一个给定名称的文件夹
	 * @param dirName
	 * @return
	 */
	public static File getTmpFileByDir(String dirName)
	{
		String folder = System.getProperty("java.io.tmpdir");
		File tmpFolder = new File(folder, dirName);

		if (!tmpFolder.exists() && !tmpFolder.mkdirs())
		{
			throw new RuntimeException("创建临时目录[" + tmpFolder.getAbsolutePath() + "]失败...");
		}
		return tmpFolder;
	}

	/**
	 * 删除整个文件夹
	 * @param baseDir 需要删除的文件夹
	 * @param delFlag 是否删除一级目录
	 * @return 删除结果
	 */
	public static boolean deleteDir(File baseDir, boolean delFlag)
	{
		if (CommonUtil.isEmpty(baseDir) || !baseDir.exists() || !baseDir.isDirectory())
		{
			return false;
		}
		if (delFlag)
		{
			deleteDir(baseDir);
		} else
		{
			File[] files = baseDir.listFiles();
			for (int i = 0; files != null && i < files.length; i++)
			{
				deleteDir(files[i]);
			}
		}
		return true;
	}

	/**
     * 删除整个文件夹(含一级目录)
     * @param baseDir 需要删除的文件夹
     * @return 删除结果
     */
    public static boolean deleteDir(File baseDir)
    {
    	if (CommonUtil.isEmpty(baseDir))
    	{
    		return false;
    	}
    	if (!baseDir.isDirectory())
		{
			return baseDir.delete();
		} else if (baseDir.isDirectory())
		{
			File[] files = baseDir.listFiles();
			for (int i = 0; files != null && i < files.length; i++)
			{
				if (!files[i].isDirectory())
				{
					files[i].delete();
				} else
				{
					deleteDir(files[i]);
				}
			}
		}
    	return baseDir.delete();
    }

	/**
	 * 创建新的文件名称
	 * @param fileName
	 * @return
	 */
	public static String newUidName(String fileName)
	{
		String ext = getLowerFileExtName(fileName);
		return null == ext ? fileName : (CommonUtil.getUid() + "." + ext);
	}
	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName)
	{
		if (null == fileName || 0 == fileName.trim().length())
		{
			return null;
		}
		int index = -1;
		if ((index = fileName.lastIndexOf(".")) == -1)
		{
			return null;
		}
		return fileName.substring(index + 1, fileName.length());
	}
	/**
	 * 获取小写的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getLowerFileExtName(String fileName)
	{
		String ext = getFileExtName(fileName);
		return ext == null ? null : ext.trim().toLowerCase();
	}
}
