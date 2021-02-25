package diplom.controller;

import diplom.service.GlobalSettingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GeneralControllerTest extends AbstractWebController {

    @MockBean
    private GlobalSettingService globalSettingService;

    @Autowired
    private GeneralController generalController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(mockMvc);
        Assert.assertNotNull(generalController);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(generalController).build();
    }

    //==================================================================================================================

    @Test
    public void getSettingsTest() throws Exception {
        Map<String, Boolean> map = Map.of(
                "MULTIUSER_MODE", true,
                "POST_PREMODERATION", true,
                "STATISTICS_IS_PUBLIC", true
        );

        Mockito.doReturn(map)
                .when(globalSettingService)
                .getSettings();

        mockMvc.perform(get("/api/settings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(asJsonString(map)))
                .andReturn();
    }
}