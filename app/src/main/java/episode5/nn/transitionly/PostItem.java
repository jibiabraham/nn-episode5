package episode5.nn.transitionly;

import android.graphics.drawable.Drawable;

/**
 * Created by jibi on 23/6/14.
 */
public class PostItem {
    public String preview_text;
    public String full_text;
    public String drawable;

    public PostItem(String preview_text, String full_text, String drawable) {

        this.preview_text = preview_text;
        this.full_text = full_text;
        this.drawable = drawable;
    }
}
