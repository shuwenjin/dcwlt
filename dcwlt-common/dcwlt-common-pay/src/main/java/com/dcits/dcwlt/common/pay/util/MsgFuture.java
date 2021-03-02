package com.dcits.dcwlt.common.pay.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 异步转同步工具类
 *
 *
 */
public class MsgFuture implements Future<Object>{
	
	private volatile Object response;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private Condition wait = lock.newCondition();
	
	private volatile boolean done;

	

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		if(isDone()) {
			return this.response;
		}
		
		this.lock.lock();
		
		try {
			while(!isDone()) {
				this.wait.await();
			}
		} finally {
			this.lock.unlock();
		}
		
		Throwable cause = cause();
		if (cause == null) {
			return this.response;
		}
		throw new ExecutionException(cause);
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if(isDone()) {
			return this.response;
		}
		
		this.lock.lock();
		
		try {
			while(!isDone()) {
				boolean flag = this.wait.await(timeout, unit);
				if(!flag) {
					throw new TimeoutException();
				}
			}
		} finally {
			this.lock.unlock();
		}
		
		Throwable cause = cause();
		if (cause == null) {
			return this.response;
		}
		throw new ExecutionException(cause);
	}
	
	protected void setSuccess(Object response) {
		if(isDone()) {
			return;
		}
		
		this.lock.lock();
		
		if(isDone()) {
			return;
		}
		
		try {
			this.response = response;
			
			this.done = true;
			
			this.wait.signal();
		} finally {
			this.lock.unlock();
		}
	}
	
	protected void setFailure(Throwable cause) {
		if(isDone()) {
			return;
		}
		
		this.lock.lock();
		
		if(isDone()) {
			return;
		}
		
		try {
			this.response = new CauseHolder(cause);
			
			this.done = true;
			
			this.wait.signal();
		} finally {
			this.lock.unlock();
		}
	}
	
	public Throwable cause() {
		if ((response instanceof CauseHolder)) {
			return ((CauseHolder) response).cause;
		}
		return null;
	}
	
	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}
	
	private static final class CauseHolder {
		final Throwable cause;

		private CauseHolder(Throwable cause) {
			this.cause = cause;
		}
	}
}
