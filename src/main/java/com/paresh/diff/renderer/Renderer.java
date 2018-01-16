package com.paresh.diff.renderer;

import com.paresh.diff.dto.DiffResponse;

public interface Renderer {

    public void render(DiffResponse diffResponse, RenderingPreferences renderingPreferences);
    public void render(DiffResponse diffResponse);

}
