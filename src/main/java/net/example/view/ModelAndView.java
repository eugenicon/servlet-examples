package net.example.view;

import java.util.*;

public class ModelAndView implements View {
    private final String pageUrl;
    private Map<String, Object> params = new HashMap<>();

    public ModelAndView(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public String getPageUrl() {
        return pageUrl;
    }

    @Override
    public void addParameter(String key, Object value) {
        params.put(key, value);
    }

    @Override
    public void removeParameter(String key) {
        params.remove(key);
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
    }
}
