package biz.lci.demo.fakedataservice.domain;

public record FakeData(
        Long id,
        String name,
        String description,
        Integer value) {
}
