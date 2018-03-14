package com.joaquintech.bowling.springboot.model;

public class Player {
	
	private String id;
	private String name;
	private int handicap = 0;
	private Frame[] frames = new Frame[10];
	private short[] scores = new short[10];
	private short turn = 0;
	private int score = 0;
	
	// Needed by Caused by: com.fasterxml.jackson.databind.JsonMappingException:
	public Player() {
		setupFrames();
	}
	
	public Player(String id, String name, int handicap) {
		super();
		this.id = id;
		this.name = name;
		this.handicap = handicap;
		setupFrames();
	}
	
	public void setupFrames() {
		for(int i = 0; i < frames.length-1; i++) {
			frames[i] = new Frame(2);
		}
		frames[frames.length-1] = new Frame(3);
	}
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getHandicap() {
		return handicap;
	}
	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}
	public Frame[] getFrames() {
		return frames;
	}
	public void setFrames(Frame[] frames) {
		this.frames = frames;
	}
	public short[] getScores() {
		return scores;
	}
	public void setScores(short[] scores) {
		this.scores = scores;
	}
	public short getTurn() {
		return turn;
	}
	public void setTurn(short turn) {
		this.turn = turn;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Player [id=%s, name=%s, score=%s, turn=%s, handicap=%s", id, name, score, turn, handicap);
	}
	public void start() {
		
	}

	/*
	 * Brains behind the score calculations
	 */
	public void computeScore() {
		int total = 0; 
		boolean finished = false;
		while(!finished) {
			for(int i = 0; i < 9; i++) {
				if(frames[i].isEmpty() || frames[i].isComplete()) {
					finished = true;
					break;
				}
				else if (frames[i].isGutter()) {
					frames[i].setScore(total);
				}
				// look at (up to) next two frames
				else if(frames[i].isStrike()) {
					if(i < 7) {
						if(frames[i+1].isStrike()) {
							if(frames[i+2].isEmpty() || !frames[i+2].isComplete()) {
								finished = true;
								break;
							}
							else if(frames[i+2].isSpare()) {
								frames[i].setScore(total + 20);
								total = frames[i].getScore();
							}
						}
						else {
							if(frames[i+1].isEmpty() || !frames[i+1].isComplete()) {
								finished = true;
								break;
							}
							else if(frames[i+1].isSpare()) {
								frames[i].setScore(total + 20);
								total = frames[i].getScore();
							}
							else if(frames[i+1].isGutter()) {
								
							}
						}
					}
					else if(i == 7) {
						
					}
					//else if(i == 8) 
					else {
						
					}
				}
				// look at next frame
				else if(frames[i].isSpare()) {
				
				}
				else {
					frames[i].setScore(total + (int)frames[i].getResult()[0] + (int)frames[i].getResult()[1]);
					total = frames[i].getScore();
				}
			}
		}
		
		score = total;
	}
	
	public boolean hasCompletedGame() {
		for(Frame frame : frames) {
			if(!frame.isComplete()) {
				return false;
			}
		}
		return true;
	}
}
