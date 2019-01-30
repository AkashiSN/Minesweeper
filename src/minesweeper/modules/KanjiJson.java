package minesweeper.modules;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import minesweeper.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class KanjiJson {
    private List<Kanji> kanjiList = new ArrayList<>();

    KanjiJson() throws IOException, URISyntaxException {
        String fileName = "/minesweeper/resources/kanji/kanji.json";
        InputStream is =  Main.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        StringBuilder kanjiJsonString = new StringBuilder();
        while((line = reader.readLine()) != null){
            kanjiJsonString.append(line);
        }
        reader.close();
        JsonArray kanjiJson = Json.parse(kanjiJsonString.toString()).asArray();
        for (JsonValue value : kanjiJson){
            List<String> yomi = new ArrayList<>();
            String kanji = value.asObject().get("kanji").asString();
            for (JsonValue value1 : value.asObject().get("yomi").asArray()){
                yomi.add(value1.asString());
            }
            String imi = value.asObject().get("imi").asString();
            kanjiList.add(new Kanji(kanji,yomi,imi));
        }
    }

    List<Kanji> getKanjiList(){
        Collections.shuffle(kanjiList);
        return kanjiList;
    }
}
