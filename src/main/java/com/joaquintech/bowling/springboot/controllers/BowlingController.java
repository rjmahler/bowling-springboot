package com.joaquintech.bowling.springboot.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaquintech.bowling.springboot.model.Player;
import com.joaquintech.bowling.springboot.service.BowlingService;

@RestController
public class BowlingController {
	@Autowired
	private BowlingService bowlingService;
	
	@GetMapping("/games/{gameId}/players")
	public List<Player> retrievePlayersForGame(@PathVariable String gameId) {
		return bowlingService.retrievePlayers(gameId);
	}
	
	@GetMapping("/games/{gameId}/players/{playerId}")
	public Player retrievePlayerForGame(@PathVariable String gameId, @PathVariable String playerId) {
		return bowlingService.getPlayer(gameId, playerId);
	}

	@PostMapping("/games/{gameId}/players")
	public ResponseEntity<Void> registerPlayerForGame(@PathVariable String gameId, @RequestBody Player newPlayer) {
		Player player = bowlingService.addPlayer(gameId, newPlayer);
		if (player == null) {
			return ResponseEntity.notFound().build();
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(player.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}

