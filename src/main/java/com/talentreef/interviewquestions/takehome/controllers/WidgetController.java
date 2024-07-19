package com.talentreef.interviewquestions.takehome.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

    private final WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        Assert.notNull(widgetService,
                "widgetService must not be null");
        this.widgetService = widgetService;
    }

    @GetMapping
    public ResponseEntity<List<Widget>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @GetMapping("/search")
    public ResponseEntity<Widget> getWidgetByName(@RequestParam String name) {
        return ResponseEntity.ok(widgetService.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<Widget> createWidget(@RequestBody @Valid Widget widget) {
        return ResponseEntity.ok(widgetService.createWidget(widget));
    }

    @PutMapping
    public ResponseEntity<Widget> updateWidget(@RequestBody @Valid Widget widget) {
        return ResponseEntity.ok(widgetService.updateWidget(widget));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWidgetByName(@RequestParam String name) {
        widgetService.deleteWidget(name);

        return ResponseEntity.noContent()
                .build();
    }

}
