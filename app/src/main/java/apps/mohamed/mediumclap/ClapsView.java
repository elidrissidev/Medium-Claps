package apps.mohamed.mediumclap;
import android.content.*;
import android.graphics.*;
import android.support.design.widget.*;
import android.util.*;
import android.view.*;
import java.util.*;
import android.animation.*;
import android.view.animation.*;
import android.os.*;

public class ClapsView extends View
{

	private int duration = 300;

	private float mRadius = 0;
	private float mFabX, mFabX_OG = 0;
	private float mFabY, mFabY_OG = 0;
	private float mTextX = 0;
	private float mTextY = 0;

	private Paint mCirclePaint, mTextPaint;
	private FloatingActionButton mFab;
	private String mClapText;

	private ValueAnimator mAlphaAnimator = new ValueAnimator();
	private ValueAnimator mCircleAnimator = new ValueAnimator();

	private List<Integer> claps = new ArrayList<>();


	public ClapsView(Context context)
	{
		super(context);
		init();
	}

	public ClapsView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public ClapsView(Context context, AttributeSet attrs, int defStyleRes)
	{
		super(context, attrs, defStyleRes);
		init();
	}

	private void init()
	{
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.BLACK);

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(40);
		mClapText = "";
	}

	private void initFab()
	{
		mFab.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

				@Override
				public void onLayoutChange(View view, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9)
				{
					view.removeOnLayoutChangeListener(this);
					mRadius = 65;
					mFabX  = mFab.getX() + mFab.getWidth() / 2;
					mFabY = mFab.getY() + mFab.getHeight() / 2;
					mFabX_OG = mFabX;
					mFabY_OG = mFabY;
					mTextX = mFabX;
					mTextY = mFabY;
					invalidate();
				}

			});

		mFab.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view)
				{
					mFabY = mFabY_OG;
					invalidate();
					clap();
					new Handler().postDelayed(new Runnable() {

							@Override
							public void run()
							{
								mFabY = mFabY_OG;
								mTextY = mFabY_OG;
								invalidate();
							}

						}, 1000);

				}

			});
	}

	public void with(FloatingActionButton fab)
	{
		this.mFab = fab;
		initFab();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		canvas.drawCircle(mFabX, mFabY, mRadius, mCirclePaint);
		canvas.drawText("+" + mClapText, mTextX - 20, mTextY + 10, mTextPaint);
	}

	private void clap()
	{
		if (claps.size() < 50)
		{
			int clapNum = claps.size() + 1;
			claps.add(clapNum);
			mClapText = String.valueOf(clapNum);

			//Text Alpha Animator
			mAlphaAnimator.setDuration(duration);
			mAlphaAnimator.setInterpolator(new LinearInterpolator());
			mAlphaAnimator.setIntValues(0, 255);
			mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animator)
					{
						mCirclePaint.setAlpha((int) animator.getAnimatedValue());
						invalidate();
					}

				});
			mAlphaAnimator.start();

			//Circle Animator
			mCircleAnimator.setDuration(duration);
			mCircleAnimator.setInterpolator(new LinearInterpolator());
			mCircleAnimator.setFloatValues(mFabY, mFabY - 200);
			mCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animator)
					{
						mFabY = animator.getAnimatedValue();
						mTextY = animator.getAnimatedValue();
						invalidate();
					}

				});
			mCircleAnimator.start();

		}
	}

}
