package episode5.nn.transitionly;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jibi on 23/6/14.
 */
public class PreviewsListFragment extends Fragment {
    public ArrayList<PostItem> posts;
    private PostsAdapter adapter;
    private ListView mListView;
    public int startYPosition = 0;

    public PreviewsListFragment() {
    }

    public static PreviewsListFragment newInstance(){
        PreviewsListFragment fragment = new PreviewsListFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previews_list, container, false);
        if(adapter == null){
            adapter = new PostsAdapter(getActivity().getApplicationContext(), true);
        }
        mListView = (ListView) view.findViewById(R.id.list_previews);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int fromTop = view.getTop();
                FullTextFragment fragment =  FullTextFragment.newInstance(fromTop);
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.add(R.id.detailed_frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        return -top + firstVisiblePosition * c.getHeight();
    }
}
