package com.mstst33.echo;

import java.io.Serializable;
import java.util.ArrayList;

public class EchoBall implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public float x, y;
	public int color;
	public float rad;
	public int alpha;
	
	public int date;
	public int isNew;
	public ArrayList<String> info;
	
	/** eBall information
	 * eBall.get(0) -> date
	 * eBall.get(1) -> join
	 * eBall.get(2) -> echo
	 * eBall.get(3) -> newState
	 * eBall.get(4) -> selectedInfo
	 * eBall.get(5) -> writing_num
	 * eBall.get(6) -> isEcho
	 */
	
	EchoBall(float x, float y, int color, float rad, ArrayList<String> info){
		this.x = x;
		this.y = y;
		this.color = color;
		this.rad = rad;
		this.alpha = 200;
		this.info = info;
		
		String data = "";
		String[] sp = info.get(1).split("\\.");
		for(int i = 0; i < sp.length; ++i)
			data += sp[i];
		
		date = Integer.parseInt(data);
		
		if(Boolean.parseBoolean(info.get(4)))
			isNew = 1;
		else
			isNew = 0;
	}
	
	public boolean contain(float x, float y){
		if(this.x - rad < x && x < this.x + rad)
			if(this.y - rad < y && y < this.y + rad)
				return true;
		
		return false;
	}
	
	public void setIsNew(){
		if(Boolean.parseBoolean(info.get(4))){
			info.set(4, "false");
			isNew = 0;
		}
		else{
			info.set(4, "true");
			isNew = 1;
		}
	}
	
	public void setRad(float rad){
		this.rad = rad;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
}