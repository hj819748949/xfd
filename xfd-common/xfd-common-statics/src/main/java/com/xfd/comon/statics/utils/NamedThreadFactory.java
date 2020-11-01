package com.xfd.comon.statics.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程命名工厂, 使用线程时尽可能的对线程进行命名, 
 * 方便后续异常的排查
 * 
 * @author xfd
 * 
 * 2020年4月20日
 *
 */
public class NamedThreadFactory implements ThreadFactory
{
	/** 线程序号 */
	private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);
	
	/** 线程数 */
	private final AtomicInteger mThreadNum = new AtomicInteger(1);

	/** 线程名称前缀 */
	private final String mPrefix;
	
	/** 是否守护线程 */
	private final boolean mDaemo;

	/** 线程组 */
	private final ThreadGroup mGroup;

	public NamedThreadFactory()
	{
		this("pool-" + POOL_SEQ.getAndIncrement(), false);
	}
	public NamedThreadFactory(String prefix)
	{
		this(prefix, false);
	}
	public NamedThreadFactory(String prefix, boolean daemo)
	{
		mPrefix = prefix + "-thread-";
		mDaemo = daemo;
		SecurityManager s = System.getSecurityManager();
		mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
	}
	public Thread newThread(Runnable runnable)
	{
		String name = mPrefix + mThreadNum.getAndIncrement();
		Thread ret = new Thread(mGroup, runnable, name, 0);
		ret.setDaemon(mDaemo);
		return ret;
	}
	public ThreadGroup getThreadGroup()
	{
		return mGroup;
	}
}
