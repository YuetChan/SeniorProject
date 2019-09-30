package com.example.demo.counter;

public class Counter {
	
	private String id;
	private int seq;
	
	public Counter(String id, int seq) {
		
		super();
		this.id = id;
		this.seq = seq;
		
	}
	
	public String getId() {
		
		return id;
		
	}
	
	public void setId(String id) {
		
		this.id = id;
		
	}
	
	public int getSeq() {
		
		return seq;
		
	}
	
	public void setSeq(int seq) {
		
		this.seq = seq;
		
	}

}
