package com.joaquintech.bowling.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.joaquintech.bowling.springboot.controllers.BowlingController;
import com.joaquintech.bowling.springboot.model.Player;
import com.joaquintech.bowling.springboot.service.BowlingService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BowlingController.class, secure = false)
public class BowlingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BowlingService bowlingService;
	
	Player mockPlayer = new Player("1", "Bob", 10);
	String examplePlayerJson = "{\"name\":\"Bob\",\"handicap\":\"20\"}";
	String frames = "{count:2,turn:0,result:\u0000\u0000,score:0,empty:true,complete:false,gutter:false,strike:false,spare:false}";
	String frames10 = "{count:3,turn:0,result:\u0000\u0000\u0000,score:0,empty:true,complete:false,gutter:false,strike:false,spare:false}";
	@Test
	public void retrieveDetailsForPlayer() throws Exception {
		Mockito.when(bowlingService.getPlayer(Mockito.anyString(), Mockito.anyString())).thenReturn(mockPlayer);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/games/1/players/1").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse());
		
		String expectedFrames = "";
		for(int i = 0; i< 9; i++) {
			expectedFrames += frames + ",";
		}
		expectedFrames += frames10;
		
		String expected = "{id:1,name:Bob,handicap:10,frames:[" + expectedFrames +  "],scores:[0,0,0,0,0,0,0,0,0,0],turn:0,score:0}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}
}
