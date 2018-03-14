package com.joaquintech.bowling.springboot.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.joaquintech.bowling.springboot.model.Game;
import com.joaquintech.bowling.springboot.model.Player;

@Component
public class BowlingService {

	private static List<Game> games = new ArrayList<>();
	
	static {
		//Initialize Data
		Player player1 = new Player("1", "Player 1", 0);
		Player player2 = new Player("2", "Player 2", 0);
		Player player3 = new Player("3", "Player 3", 0);
		Player player4 = new Player("4", "Player 4", 0);
		
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		Game game1 = new Game("1", "4 Player Game", players, null);
		
		List<Player> players2 = new ArrayList<Player>();
		players2.add(player1);
		players2.add(player2);
		
		Game game2 = new Game("2", "2 Player Game", players2, null);
		
		List<Player> players3 = new ArrayList<Player>();
		players3.add(player1);
		
		Game game3 = new Game("3", "Solo Game", players3, null);
		
		games = new ArrayList<Game>();
		games.add(game1);
		games.add(game2);
		games.add(game3);
	}
	
	public List<Game> retrieveAllGames() {
		return games;
	}
	
	public Game retrieveGame(String gameId) {
		for(Game game : games) {
			if(game.getId().equals(gameId)) {
				return game;
			}
		}
		return null;
	}
	
	public List<Player> retrievePlayers(String gameId) {
		Game game = retrieveGame(gameId);
		if(game == null) {
			return null;
		}
		return game.getPlayers();
	}
	
	public Player getPlayer(String gameId, String playerId) {
		Game game = retrieveGame(gameId);
		if(game == null) {
			return null;
		}
		
		for(Player player : game.getPlayers()) {
			if(player.getId().equals(playerId)) {
				return player;
			}
		}
		
		return null;
	}
	
	private SecureRandom random = new SecureRandom();
	
	public Player addPlayer(String gameId, Player player) {
		Game game = retrieveGame(gameId);
		if (game == null) {
			return null;
		}
		
		String randomId = new BigInteger(130, random).toString(32);
		player.setId(randomId);
		game.getPlayers().add(player);
		return player;
	}
}
