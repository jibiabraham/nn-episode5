package episode5.nn.transitionly;



import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class FullTextFragment extends Fragment implements ObservableScrollView.Callbacks {
    private PostItem post;
    private ImageView mStickyView;
    private View mPlaceholder;
    private int mStickyHeight;
    private int startY;
    private int currentScrollY;

    public FullTextFragment() {
        // Required empty public constructor
    }

    public static FullTextFragment newInstance(int startY){
        FullTextFragment fragment = new FullTextFragment();
        Bundle args = new Bundle();
        args.putInt("startY", startY);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.startY = getArguments().getInt("startY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ObservableScrollView view = (ObservableScrollView) inflater.inflate(R.layout.fragment_full_text, container, false);
        view.setCallbacks(this);

        mStickyView = (ImageView) view.findViewById(R.id.cover_image);
        TextView fullText = (TextView) view.findViewById(R.id.preview_text);
        mPlaceholder = view.findViewById(R.id.placeholder);

        String full_text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                " \n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";

        if(post == null){
            post = new PostItem("Preview",full_text, "nothing");
        }

        mStickyView.setImageResource(R.drawable.dreamscapes);
        fullText.setText(post.full_text);

        ViewTreeObserver observer = mStickyView.getViewTreeObserver();

        if(observer != null){
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    onScrollChanged(view.getScrollY());
                }
            });
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    ViewTreeObserver observer = mStickyView.getViewTreeObserver();
                    if(observer != null)
                        observer.removeOnPreDrawListener(this);
                    mStickyHeight = mStickyView.getMeasuredHeight();
                    mPlaceholder.setMinimumHeight(mStickyHeight);
                    return true;
                }
            });
        }

        mStickyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mStickyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewParent parent = v.getParent();
                if(parent != null){
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        ObjectAnimator resetScroll = ObjectAnimator.ofInt(getView(), "scrollY", currentScrollY, 0).setDuration(250);
        Animator moveFragmentToPostion;
        AnimatorSet set = new AnimatorSet();
        if(enter){
            moveFragmentToPostion = ObjectAnimator.ofFloat(null, "translationY", startY, 0f).setDuration(350);
            set.play(moveFragmentToPostion);
        } else {
            moveFragmentToPostion = ObjectAnimator.ofFloat(null, "translationY", 0f, startY).setDuration(350);
            set.playTogether(resetScroll, moveFragmentToPostion);
        }
        return set;
    }

    @Override
    public void onScrollChanged(int scrollY) {
        currentScrollY = scrollY;
        mStickyView.setTranslationY(Math.max(0, Math.round(scrollY - 0.8 * mStickyHeight)));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent() {

    }
}
