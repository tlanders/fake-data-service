package biz.lci.demo.fakedataservice.controller;

import biz.lci.demo.fakedataservice.domain.FakeData;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FakeDataController {
    protected List<FakeData> fakeDataList;

    @PostConstruct
    public void initFakeData() {
        fakeDataList = new ArrayList<>();
        fakeDataList.add(new FakeData(1L, "First", "First fake data", 100));
        fakeDataList.add(new FakeData(2L, "Second", "Second fake data", 200));
        fakeDataList.add(new FakeData(3L, "Third", "Third fake data", 300));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/data")
    public List<FakeData> getData() {
        return fakeDataList;
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<FakeData> getData(@PathVariable Long id) {
        if(id == null) return ResponseEntity.badRequest().build();

        FakeData data = getFakeData(id);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

    @Nullable
    private FakeData getFakeData(Long id) {
        return fakeDataList.stream().filter(d -> id.equals(d.id())).findFirst().orElse(null);
    }

    private int getFakeDataIndex(Long id) {
        for(int i = 0; i < fakeDataList.size(); i++) {
            if(id.equals(fakeDataList.get(i).id())) return i;
        }
        return -1;
    }

    @PostMapping("/data/{id}")
    public ResponseEntity<FakeData> postData(@PathVariable Long id, @RequestBody FakeData fakeData) {
        int index = getFakeDataIndex(id);
        if(index == -1) return ResponseEntity.notFound().build();

        fakeDataList.set(index, fakeData);
        return ResponseEntity.ok(fakeData);
    }
}