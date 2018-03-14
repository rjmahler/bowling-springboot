package com.joaquintech.bowling.springboot.model;

import com.google.common.base.Enums;
import com.google.common.base.Optional;

public class Frame {
	
	private int count;
	private int turn;
	private char[] result;
	private int score;
	
	private enum Values {
		ONE('1'),
		TWO('2'),
		THREE('3'),
		FOUR('4'),
		FIVE('5'),
		SIX('6'),
		SEVEN('7'),
		EIGHT('8'),
		NINE('9'),
		SPARE('/'), 
		STRIKE('X'), 
		GUTTER('-');
		
		private char result;
		private Values(char result) {
			this.result = result;
		}
		public char getResult() {
			return result;
		}
		
	}
	
	public Frame(int count) {
		if(count < 2 || count > 3) {
			throw new IllegalArgumentException("A frame can only contain 2 or 3 turns");
		}
		this.count = count;
		this.turn = 0;
		this.score = 0;
		result = new char[count];
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public char[] getResult() {
		return result;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn, char value) {
		if(turn >= count) {
			throw new IllegalArgumentException("Attempting to set an invalid turn");
		}
		if(Optional.absent().equals(Enums.getIfPresent(Values.class, String.valueOf(value)))){
			throw new IllegalArgumentException("Attempting to set an invalid value");
		}
		
		result[turn] = value;
		this.turn = turn;
	}
	public boolean isComplete() {
		boolean complete = (result[0] != '\u0000' && result[1] != '\u0000') || Values.STRIKE.getResult() == result[0];
		if(count == 2) {
			return complete;
		}
		else {
			return complete && result[2] != '\u0000';
		}
	}
	public boolean isSpare() {
		return count == 2 ? (result[1] == Values.SPARE.getResult()) : 
			(result[1] == Values.SPARE.getResult() || result[2] == Values.SPARE.getResult());
	}
	public boolean isStrike() {
		return count == 2 ? result[0] == Values.STRIKE.getResult() : 
			(result[0] == Values.STRIKE.getResult() || result[1] == Values.STRIKE.getResult() || result[2] == Values.STRIKE.getResult());
	}
	
	public boolean isGutter() {
		return result[1] == Values.GUTTER.getResult() && result[2] == Values.GUTTER.getResult();
	}
	public boolean isEmpty() {
		boolean empty = result[0] == '\u0000' && result[1] == '\u0000';
		if(count == 2) {
			return empty;
		}
		else {
			return empty && result[2] == '\u0000';
		}
	}
	
}
