package searchengine.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Slf4j
@Getter
public class Snippet {

    private List<Integer> indexes = new ArrayList<>();
    private int count = 0;
    private String text = "";

    public void update(List<Integer> indexes, String text) {
        int size = indexes.size();
        if (this.count < size) {
            this.count = size;
            this.text = text;
            this.indexes = indexes;
        }
    }

}
