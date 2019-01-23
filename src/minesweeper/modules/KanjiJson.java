package minesweeper.modules;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class KanjiJson {
    private List<Kanji> kanjiList = new ArrayList<>();

    private String readAll(final String path) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    KanjiJson() throws IOException {
        String fileName = "resources/kanji/kanji.json";
        String kanjiJsonString = readAll(fileName);
        JsonArray kanjiJson = Json.parse(kanjiJsonString).asArray();
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
