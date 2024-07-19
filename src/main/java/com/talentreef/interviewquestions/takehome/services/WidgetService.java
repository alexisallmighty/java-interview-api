package com.talentreef.interviewquestions.takehome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WidgetService {

    private final WidgetRepository widgetRepository;

    @Autowired
    private WidgetService(WidgetRepository widgetRepository) {
        Assert.notNull(widgetRepository,
                "widgetRepository must not be null");
        this.widgetRepository = widgetRepository;
    }

    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    public Widget searchByName(String name) {
        Widget found = widgetRepository.findById(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Widget not found"));

        return found;
    }

    public Widget createWidget(Widget widget) {
        String name = widget.getName();

        Optional<Widget> existing = widgetRepository.findById(name);

        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Widget already exists with the name: " + name);
        }

        return widgetRepository.save(widget);
    }

    public Widget updateWidget(Widget widget) {
        Widget existing = searchByName(widget.getName());

        // Update allowed fields only
        existing.setDescription(widget.getDescription());
        existing.setPrice(widget.getPrice());

        return widgetRepository.save(widget);
    }

    public void deleteWidget(String name) {
        widgetRepository.deleteById(name);
    }
}
