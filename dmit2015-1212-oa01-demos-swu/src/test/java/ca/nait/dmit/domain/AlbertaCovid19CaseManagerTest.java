package ca.nait.dmit.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AlbertaCovid19CaseManagerTest {

    @Test
    void getAlbertaCovid19CaseList() throws IOException {
//        AlbertaCovid19CaseManager caseManager = new AlbertaCovid19CaseManager();
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(436495, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList2() throws IOException {
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(436495, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList3() throws IOException {
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(436495, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList4() throws IOException {
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(436495, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList5() throws IOException {
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(436495, caseManager.getAlbertaCovid19CaseList().size());
    }
}