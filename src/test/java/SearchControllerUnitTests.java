import app.search.SearchController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchControllerUnitTests {
    @InjectMocks
    private SearchController searchController;
    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void getAllHotelsV1Test() throws Exception {
        this.mvc.perform(get("/v1/search")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getHotelByNameV1Test() throws Exception {
        //Mock is empty so 404 is the expected
        this.mvc.perform(get("/v1/search?hotelName=Hotel1")).andExpect(status().is4xxClientError());
    }

    @Test
    public void getHotelsV2Test() throws Exception {
        //Mock is empty so 404 is the expected
        this.mvc.perform(get("/v2/search?destination=London")).andExpect(status().is4xxClientError());
    }

}