package episode5.nn.transitionly;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jibi on 23/6/14.
 */
public class PostsAdapter extends BaseAdapter {
    private final Boolean isPreview;
    public Context context;
    public ArrayList<PostItem> posts;

    public PostsAdapter(Context context, Boolean isPreview){
        this.context = context;
        this.isPreview = isPreview;
        this.posts = new ArrayList<PostItem>();

        String[] preview_texts = context.getResources().getStringArray(R.array.preview_text);
        String[] full_texts = context.getResources().getStringArray(R.array.full_text);
        String[] drawables = context.getResources().getStringArray(R.array.drawables);
        for (int i = 0; i < drawables.length; i++) {
            PostItem post = new PostItem(preview_texts[i], full_texts[i], drawables[i]);
            posts.add(post);
        }
    }

    @Override
    public int getCount() {
        return this.posts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_preview_item, null);
        }

        PostItem post = (PostItem) getItem(position);
        ImageView cover = (ImageView) convertView.findViewById(R.id.cover_image);
        TextView text = (TextView) convertView.findViewById(R.id.preview_text);

        cover.setImageResource(R.drawable.dreamscapes);
        text.setText(this.isPreview ? post.preview_text : post.full_text);

        return convertView;
    }
}
