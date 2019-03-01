package net.example.view;

import java.util.*;

public class ModelAndView implements View {
    private String pageUrl;
    private Map<String, Object> params = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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
