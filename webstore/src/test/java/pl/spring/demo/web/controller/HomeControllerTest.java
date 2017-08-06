package pl.spring.demo.web.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.controller.HomeController;



public class HomeControllerTest {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
				.setViewResolvers(viewResolver).build();
	}

	
	@Test
	public void shouldPrepareHomePage() throws Exception {
		
		// when
		ResultActions resultActions = mockMvc.perform(get("/"));
		
		// then
		resultActions.andExpect(view().name("welcome"))
				.andExpect(model().attribute(ModelConstants.GREETING, "WELCOME TO THE LIBRARY"))
				.andExpect(model().attribute(ModelConstants.INFO, "please choose action below"));
	}
	
}

