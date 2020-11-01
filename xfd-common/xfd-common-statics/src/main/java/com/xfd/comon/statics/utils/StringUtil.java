package com.xfd.comon.statics.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author xfd
 *
 * 2019年3月23日
 */
public class StringUtil
{
	private static final Pattern KVP_PATTERN = Pattern.compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)");
	
	private StringUtil() { }
	
	/**
     * 中文标点符号
     */
    static final String[] SBC =
    { "，", "。", "；", "“", "”", "？", "！", "（", "）", "：", "——", "、" };

    /**
     * 英文标点符号
     */
    static final String[] DBC =
    { ",", ".", ";", "\"", "\"", "?", "!", "(", ")", ":", "_", "," };

    /**
     * 换行符
     * @see 
     */
    private static String sLF = null;
    
    /**
     * 判断字符串是否为空
     * @param str
     * @return {@code str}为null、空串、或仅包含空格时返回<code>true</code>,否则返回<code>false</code>
     */
    public static boolean isEmpty(String str)
    {
        return (str == null) || (str.trim().length() == 0);
    }
    
    /**
     * 判断传入对象是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str)
    {
        return (str == null) || (str.toString().trim().length() == 0);
    }
    
    public static Map<String, String> parseQueryString(String qs) 
    {
        if (qs == null || qs.length() == 0)
        {
            return new HashMap<String, String>();
        }
        return parseKeyValuePair(qs, "\\&");
    }
    private static Map<String, String> parseKeyValuePair(String str, String itemSeparator) 
    {
        String[] tmp = str.split(itemSeparator);
        Map<String, String> map = new HashMap<String, String>(tmp.length);
        for (int i = 0; i < tmp.length; i++) 
        {
            Matcher matcher = KVP_PATTERN.matcher(tmp[i]);
            if (matcher.matches() == false)
            {
                continue;
            }
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }
    /**
     * 过滤空格，仅字符串前后
     * @param str
     * @return
     */
    public static String trim(Object str)
    {
    	return str == null ? null : str.toString().trim();
    }
    
    /**
     * 过滤所有空格信息，包括全角半角空格，包括字符串前后和内部的空格
     * @param str
     * @return
     */
    public static String trimAllWC(Object str)
    {
    	if (str == null)
    	{
    		return null;
    	}
    	
    	StringBuilder builder = new StringBuilder();
    	
    	char[] arrays = str.toString().toCharArray();
    	
    	for (char character : arrays)
		{
			if (character == '　' || character == ' ')
			{
				continue;
			}
			
			builder.append(character);
		}
    	
    	return builder.toString();
    }
    
    /**
     * 过滤所有空格信息，包括字符串前后和内部的空格
     * @param str
     * @return
     */
    public static String trimAll(Object str)
    {
    	if (str == null)
    	{
    		return null;
    	}
    	
    	StringBuilder builder = new StringBuilder();
    	
    	char[] arrays = str.toString().toCharArray();
    	
    	for (char character : arrays)
		{
			if (character == ' ')
			{
				continue;
			}
			
			builder.append(character);
		}
    	
    	return builder.toString();
    }
    
    /**
     * 检查两个字符串是否相同 （null safe）
     * @param str1 待比较的字符串1
     * @param str2 待比较的字符串2
     * @return 当满足以下条件时，返回<code>true</code>，否则<code>false</code>
     * <ul>
     * <li><code>str1</code>不为<code>null</code></li>
     * <li><code>str2</code>不为<code>null</code></li>
     * <li><code>str2.equals(str1)</code></li>
     * </ul>
     * @see #equalsIgnoreCase(String, String)
     */
    public static boolean equals(String str1, String str2)
    {
        return str1 == null ? false : str2 == null ? true : str1.equals(str2);
    }

    /**
     * 检查两个字符串是否相同(无视大小写,null safe)
     * @param str1
     * @param str2
     * @see #equals(String , String )
     * @return 当满足以下条件时，返回<code>true</code>，否则<code>false</code>
     * <ul>
     * <li><code>str1</code>不为<code>null</code></li>
     * <li><code>str2</code>不为<code>null</code></li>
     * <li><code>str2.equalsIgnoreCase(str1)</code></li>
     * </ul>
     */
    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 == null ? false : str2 == null ? true : str1.equalsIgnoreCase(str2);
    }

    /**
     * 截取字符串str的左边len位
     * <p>例如:
     *  <blockquote><code>left("booway",3)</code>, 返回"boo"</blockquote>
     *
     * @param str 待截取的字符串
     * @param len 截取的长度
     * @return 截取后的字符串
     */
    public static String left(String str, int len)
    {
        if (str == null)
        {
            return null;
        }
        if (len < 0)
        {
            return "";
        }
        if (str.length() <= len)
        {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * 截取字符串str的后边len位
     * example: left("booway",3), 返回"way"
     * @param str 待截取的字符串
     * @param len 截取的长度
     * @return 截取后的字符串
     */
    public static String right(String str, int len)
    {
        if (str == null)
        {
            return null;
        }
        if (len < 0)
        {
            return "";
        }
        if (str.length() <= len)
        {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * 截取字符串str中间从pos开始的len位置(位置从0开始）
     * example: mid("booway",1,2)，返回"oo"
     * @param str 待截取的字符串
     * @param pos 开始的位置
     * @param len 长度
     * @return 截取后的字符串
     */
    public static String mid(String str, int pos, int len)
    {
        if (str == null)
        {
            return null;
        }
        if ((len < 0) || (pos > str.length()))
        {
            return "";
        }
        if (pos < 0)
        {
            pos = 0;
        }
        if (str.length() <= pos + len)
        {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    /**
     * 去掉回车换行符
     * @param str 待处理的字符串 
     * @return 去掉回车换行符后的字符串
     */
    public static String chomp(String str)
    {
        if (isEmpty(str))
        {
            return str;
        }

        if (str.length() == 1)
        {
            char ch = str.charAt(0);
            if ((ch == '\r') || (ch == '\n'))
            {
                return "";
            }
            return str;
        }

        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);

        if (last == '\n')
        {
            if (str.charAt(lastIdx - 1) == '\r')
            {
                lastIdx--;
            }
        } else if (last != '\r')
        {
            lastIdx++;
        }
        return str.substring(0, lastIdx);
    }

    /**
     * 用<code>newPattern</code>替换掉<code>inString</code>中的<code>oldPattern</code>
     * @param inString 待替换的字符串
     * @param oldPattern 原
     * @param newPattern
     * @return
     */
    public static String replace(String inString, String oldPattern, String newPattern)
    {
        if (isEmpty(inString) || isEmpty(oldPattern) || isEmpty(newPattern))
        {
            return inString;
        }
        StringBuffer sbuf = new StringBuffer();

        int pos = 0;
        int index = inString.indexOf(oldPattern);

        int patLen = oldPattern.length();
        while (index >= 0)
        {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        return sbuf.toString();
    }

    /**
     * 将stream以encode编码转换成字符串
     * @param stream
     * @param encode
     * @return 转换后的字符串
     * @throws IOException
     */

    public static String changeStreamToString(InputStream stream, String encode) throws IOException
    {
        byte[] b100k = new byte[200000];

        int pos = 0;
        while (true)
        {
            int len = stream.read(b100k, pos, b100k.length - pos);
            if (len <= 0)
            {
                break;
            }
            pos += len;
        }

        if (pos >= b100k.length - 1)
        {
            throw new IOException("ERROR:The stream size is more than " + b100k.length + " bytes");
        }

        return new String(b100k, 0, pos, encode);
    }
    /**
     * 将字符串中的中文标点转换成英文标点
     * @param sSource
     * @return
     */
    public static String symbolSBCToDBC(String sSource)
    {
        if ((sSource == null) || (sSource.length() <= 0))
        {
            return sSource;
        }
        int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
        for (int i = 0; i < iLen; i++)
        {
            sSource = replace(sSource, SBC[i], DBC[i]);
        }
        return sSource;
    }

    /**
     * 将字符串中的英文标点转换成中文标点
     * @param sSource
     * @return
     */
    public static String symbolDBCToSBC(String sSource)
    {
        if ((sSource == null) || (sSource.length() <= 0))
        {
            return sSource;
        }
        int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
        for (int i = 0; i < iLen; i++)
        {
            sSource = replace(sSource, DBC[i], SBC[i]);
        }
        return sSource;
    }

    /**
     * 查找inString中的pattern，并删除（用空字符串替换）
     * @param inString
     * @param pattern
     * @return
     */
    public static String delete(String inString, String pattern)
    {
        return replace(inString, pattern, "");
    }

    /**
     * 删除inString中在charsToDelete中包含的任何字符
     * @param inString
     * @param charsToDelete
     * @return
     */
    public static String deleteAny(String inString, String charsToDelete)
    {
        if (isEmpty(inString) || isEmpty(charsToDelete))
        {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++)
        {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1)
            {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * 将str用英文单引号包裹
     * @param str
     * @return
     */
    public static String quote(String str)
    {
        return str != null ? "'" + str + "'" : null;
    }

    /**
     * 判断str是否以prefix开头（null safe,无视大小写）
     * @param str
     * @param prefix
     * @return
     */
    public static boolean startsWithIgnoreCase(String str, String prefix)
    {
        if ((str == null) || (prefix == null))
        {
            return false;
        }
        if (str.startsWith(prefix))
        {
            return true;
        }
        if (str.length() < prefix.length())
        {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * 判断str是否以suffix结尾（null safe,无视大小写）
     * @param str
     * @param prefix
     * @return
     */
    public static boolean endsWithIgnoreCase(String str, String suffix)
    {
        if ((str == null) || (suffix == null))
        {
            return false;
        }
        if (str.endsWith(suffix))
        {
            return true;
        }
        if (str.length() < suffix.length())
        {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);
    }

    /**
     * 去掉str最后的separator
     * @param str
     * @param separator
     * @return
     */
    public static String chomp(String str, String separator)
    {
        if ((isEmpty(str)) || (separator == null))
        {
            return str;
        }
        if (str.endsWith(separator))
        {
            return str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    /**
     * 将集合中的字符串用surround包裹，然后再以delimiter串联
     * 如：delimiter= ","  surround = "'"
     * 集合包含：test,test2,test3返回值形如: 'test','test2','test3'
     * @param coll 集合
     * @param delimiter 分隔符
     * @param surround 集合元素包裹字符 可以为空
     * @return
     */
    public static String concatEntries(Collection<String> coll, String delimiter, String surround)
    {
        if ((coll == null) || (StringUtil.isEmpty(delimiter)))
        {
            return "";
        }

        if (isEmpty(surround))
        {
            surround = "";
        }

        StringBuffer strBuff = new StringBuffer();
        for (String str : coll)
        {
            if (strBuff.length() > 0)
            {
                strBuff.append(coll + surround + str + surround);
            } else
            {
                strBuff.append(surround + str + surround);
            }
        }
        return strBuff.toString();
    }

    /**
     * 将字符串转换成大写
     * @param str
     * @return
     */
    public static String upperCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        return str.toUpperCase();
    }

    /**
     * 将字符串转换成小写
     * @param str
     * @return
     */
    public static String lowerCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        return str.toLowerCase();
    }

    /**
     * 将字符串的首字母大写
     * @param str
     * @return
     */
    public static String capitalize(String str)
    {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
        {
            return str;
        }
        return strLen + Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 将字符串中的字符反转
     * @param str
     * @return
     */
    public static String reverse(String str)
    {
        if (str == null)
        {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 将str用delimiter分隔
     * @param str 待分隔的字符串
     * @param delimiter 分隔符
     * @return 分隔后形成的list
     */
    public static List<String> split(String str, char delimiter)
    {
        if ((str == null) || ("".equals(str)))
        {
            return new ArrayList<String>();
        }

        ArrayList<String> parts = new ArrayList<String>();

        int previousIndex = 0;
        int currentIndex;
        while ((currentIndex = str.indexOf(delimiter, previousIndex)) > 0)
        {
            String part = str.substring(previousIndex, currentIndex).trim();
            parts.add(part);
            previousIndex = currentIndex + 1;
        }

        parts.add(str.substring(previousIndex, str.length()).trim());

        return parts;
    }

    /**
     * 
     * @return 换行符
     */
    public static String getLF()
    {
        String lf = sLF;
        if (lf == null)
        {
            try
            {
                lf = System.getProperty("line.separator");
                sLF = lf == null ? "\n" : lf;
            } catch (Throwable t)
            {
                sLF = lf = "\n";
            }
        }
        return lf;
    }

    /**
     * 附加换行符
     * @param sb
     */
    public static void appendLF(StringBuffer sb)
    {
        sb.append(getLF());
    }

    /**
     * 从str开头开始匹配，删除任何出现在stripChars中的字符，直到出现没有包含在stripChars中的字符
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars)
    {
        if (isEmpty(str) || isEmpty(stripChars))
        {
            return str;
        }
        int start = 0;
        int strLen = str.length();
        while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1))
        {
            start++;
        }
        return str.substring(start);
    }

    /**
     * 从str结尾开始匹配，删除任何出现在stripChars中的字符，直到出现没有包含在stripChars中的字符
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars)
    {
        if (isEmpty(str) || isEmpty(stripChars))
        {
            return str;
        }
        int end = str.length();
        while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1))
        {
            end--;
        }
        return str.substring(0, end);
    }

    /**
     * 对str进行stripStart和stripEnd操作。
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripAll(String str, String stripChars)
    {
        if (isEmpty(str) || isEmpty(stripChars))
        {
            return str;
        }
        return stripEnd(stripStart(str, stripChars), stripChars);

    }

    /**
     * 截取分隔符前的字符串
     * @param str
     * @param separator
     * @return
     */
    public static String substringBefore(String str, String separator)
    {
        if ((isEmpty(str)) || (separator == null))
        {
            return str;
        }
        if (separator.length() == 0)
        {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1)
        {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取分隔符后的字符串
     * @param str
     * @param separator
     * @return
     */
    public static String substringAfter(String str, String separator)
    {
        if (isEmpty(str))
        {
            return str;
        }
        if (separator == null)
        {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1)
        {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取最后一个分隔符前的字符串
     * @param str
     * @param separator
     * @return
     */

    public static String substringBeforeLast(String str, String separator)
    {
        if ((isEmpty(str)) || (isEmpty(separator)))
        {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1)
        {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取最后一个分隔符后的字符串
     * @param str
     * @param separator
     * @return
     */
    public static String substringAfterLast(String str, String separator)
    {
        if (isEmpty(str))
        {
            return str;
        }
        if (isEmpty(separator))
        {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if ((pos == -1) || (pos == str.length() - separator.length()))
        {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取字符串str中的被tag包裹的字符串
     * @param str
     * @param tag
     * @return
     */
    public static String substringBetween(String str, String tag)
    {
        return substringBetween(str, tag, tag);
    }

    /**
     * 截取字符串str中从open到close之间的字符串（单次匹配）
     * @param str
     * @param open
     * @param close
     * @return
     * @see #substringsBetween(String, String, String)
     */
    public static String substringBetween(String str, String open, String close)
    {
        if ((str == null) || (open == null) || (close == null))
        {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1)
        {
            int end = str.indexOf(close, start + open.length());
            if (end != -1)
            {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * 截取字符串str中从open到close之间的字符串 （多次匹配）
     * @param str
     * @param open
     * @param close
     * @return
     * @see #substringBetween(String, String, String)
     */
    public static String[] substringsBetween(String str, String open, String close)
    {
        if ((str == null) || (isEmpty(open)) || (isEmpty(close)))
        {
            return null;
        }
        int strLen = str.length();
        int closeLen = close.length();
        int openLen = open.length();
        List<String> list = new ArrayList<String>();
        int pos = 0;
        while (pos < strLen - closeLen)
        {
            int start = str.indexOf(open, pos);
            if (start < 0)
            {
                break;
            }
            start += openLen;
            int end = str.indexOf(close, start);
            if (end < 0)
            {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.size() > 0)
        {
            return list.toArray(new String[list.size()]);
        }
        return null;
    }
    
    /**
     * 将列表内所有字符串拼接在一起
     * @param strs
     * @param split
     * @return
     */
    public static String appendStrList(List<String> strs, String split)
    {
        
        if (strs == null || strs.size() == 0)
        {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        // 剔除null的情况
        split  = split == null ? "" : split;
        
        for (String str : strs)
        {
            builder.append(str).append(split);
        }
        // 删除最后一个分隔符
        builder.delete(builder.length() - split.length(), builder.length());
        return builder.toString();
    }
}
