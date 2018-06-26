package com.tgithubc.kumao.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.util.DPPXUtil;
import com.tgithubc.kumao.util.WeakWrapperHandler;

/**
 * Created by tc :)
 */
public class JellyfishView extends View implements WeakWrapperHandler.MessageHandler {

    private static final float FACTOR = 0.551915024494F;
    // 水母上下跃动默认offset
    private static final float DEFAULT_ROTATE_STEP = 2.5F;
    private static final int MSG_WHAT_ROTATE = 1;
    // 专辑的默认大小dp
    private static final int DEFAULT_SIZE = 280;
    // 水母预留活动范围默认dp
    private static final int DEFAULT_SPACE = 30;
    // 水母上下跃动默认offset
    private static final int DEFAULT_OFFSET = 15;

    // 指的是专辑的大小
    private int mDefaultSize;
    // 水母预留活动范围
    private int mSpaceSize;
    // 专辑封面和水母控件总大小
    private int mSize;
    // 水母拟圆的半径
    private int mRadius;
    // 水母撑起初始
    private float mDefaultOffset;
    // 水母跳动高度
    private float mVibrateOffset;
    // 旋转角度
    private float mFrontAngle = 0;
    private float mBehindAngle = mFrontAngle - 180;
    // 水母伸展/结束的时候变化乘积（1到0，0到1）
    private float mOffsetFactor;

    // 图片画笔
    private Paint mPaint;
    // 遮罩画笔
    private Paint mShadowPaint;
    // 水母画笔
    private Paint mJellyfishPaint;
    private Path mJellyfishPath;
    private Matrix mGradientMatrix;
    private LinearGradient mLinearGradient;
    // 专辑图片
    private Bitmap mCoverBitmap;
    // 专辑图片输入输出范围
    private Rect mBitmapInRect;
    private Rect mBitmapOutRect;
    // 遮罩图片
    private Bitmap mShadowBitmap;
    // 三阶拟圆的起始点和终点
    private PointF mPoint0, mPoint3, mPoint6, mPoint9;
    // 三阶拟圆的控制点
    private PointF mPoint1, mPoint2, mPoint4, mPoint5, mPoint7, mPoint8, mPoint10, mPoint11;
    // 开始的时候伸展/结束的时候收缩的动画
    private ValueAnimator mExpandAnimator, mShrinkAnimator;
    // 是否在转动
    private boolean isRotating;
    private Handler mRotateHandler;

    private Palette.PaletteAsyncListener mPaletteListener = new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {
            if (palette != null) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                int vibrantColor = vibrantSwatch == null ? Color.GRAY : vibrantSwatch.getRgb();
                Palette.Swatch lightSwatch = palette.getLightVibrantSwatch();
                int lightVibrantColor = lightSwatch == null ? Color.GRAY : lightSwatch.getRgb();
                mLinearGradient = new LinearGradient(mRadius, -mRadius, -mRadius, mRadius, lightVibrantColor, vibrantColor,
                        Shader.TileMode.CLAMP);
                mJellyfishPaint.setShader(mLinearGradient);
                invalidate();
            }
        }
    };

    public JellyfishView(Context context) {
        this(context, null);
    }

    public JellyfishView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JellyfishView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRotateHandler = new WeakWrapperHandler(this);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mShadowPaint.setFilterBitmap(true);
        mJellyfishPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mJellyfishPaint.setStyle(Paint.Style.FILL);
        mJellyfishPaint.setStrokeCap(Paint.Cap.ROUND);
        mJellyfishPaint.setStrokeJoin(Paint.Join.ROUND);

        mSpaceSize = DPPXUtil.dp2px(DEFAULT_SPACE);
        mDefaultSize = DPPXUtil.dp2px(DEFAULT_SIZE);
        mDefaultOffset = DPPXUtil.dp2px(DEFAULT_OFFSET);
        mGradientMatrix = new Matrix();
        mJellyfishPath = new Path();
        mCoverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.play_page_cover);
        mBitmapInRect = new Rect(0, 0, mCoverBitmap.getWidth(), mCoverBitmap.getHeight());
        mLinearGradient = new LinearGradient(mRadius, -mRadius, -mRadius, mRadius, Color.YELLOW, Color.RED,
                Shader.TileMode.CLAMP);
        Palette.from(mCoverBitmap).generate(mPaletteListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureDimension(mDefaultSize, widthMeasureSpec);
        int measureHeight = measureDimension(mDefaultSize, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize = Math.min(w, h);
        mBitmapOutRect = new Rect(mSpaceSize, mSpaceSize, mSize - mSpaceSize, mSize - mSpaceSize);
        mRadius = mBitmapOutRect.width() / 2 + 15;
        initPoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mJellyfishPaint.setAlpha(70);
        drawJellyfish(canvas, mBehindAngle);
        mJellyfishPaint.setAlpha(50);
        drawJellyfish(canvas, mFrontAngle);
        drawBitmap(canvas);
    }

    public void setBitmap(Bitmap bitmap) {
        mCoverBitmap = bitmap;
        mBitmapInRect = new Rect(0, 0, mCoverBitmap.getWidth(), mCoverBitmap.getHeight());
        Palette.from(mCoverBitmap).generate(mPaletteListener);
        invalidate();
    }

    public void start(boolean start) {
        if (start) {
            startExpandAnimator();
        } else {
            startShrinkAnimator();
        }
    }

    private void initPoint() {
        // top
        mPoint0 = new PointF(0, -mRadius);
        mPoint1 = new PointF(mRadius * FACTOR, -mRadius);
        mPoint11 = new PointF(-mRadius * FACTOR, -mRadius);
        //right
        mPoint3 = new PointF(mRadius, 0);
        mPoint2 = new PointF(mRadius, -mRadius * FACTOR);
        mPoint4 = new PointF(mRadius, mRadius * FACTOR);
        //bottom
        mPoint6 = new PointF(0, mRadius);
        mPoint7 = new PointF(-mRadius * FACTOR, mRadius);
        mPoint5 = new PointF(mRadius * FACTOR, mRadius);
        //left
        mPoint9 = new PointF(-mRadius, 0);
        mPoint10 = new PointF(-mRadius, -mRadius * FACTOR);
        mPoint8 = new PointF(-mRadius, mRadius * FACTOR);
    }

    private void drawBitmap(Canvas canvas) {
        int bitmapLayer = canvas.saveLayer(0F, 0F, mSize, mSize, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mCoverBitmap, mBitmapInRect, mBitmapOutRect, mPaint);
        if (mShadowBitmap == null || mShadowBitmap.isRecycled()) {
            mShadowBitmap = createShadeBitmap();
        }
        canvas.drawBitmap(mShadowBitmap, mBitmapOutRect.left, mBitmapOutRect.top, mShadowPaint);
        canvas.restoreToCount(bitmapLayer);
    }

    private void drawJellyfish(Canvas canvas, float degrees) {
        canvas.save();
        canvas.translate(mBitmapOutRect.centerX(), mBitmapOutRect.centerY());
        canvas.rotate(degrees);
        mGradientMatrix.setRotate(-degrees);
        mLinearGradient.setLocalMatrix(mGradientMatrix);
        drawPath(canvas);
        canvas.restore();
    }

    private void drawPath(Canvas canvas) {
        float offset = mDefaultOffset + mVibrateOffset;
        if (offset < mDefaultOffset) {
            offset = mDefaultOffset;
        }
        float dx = mOffsetFactor * offset;
        mJellyfishPath.rewind();
        mJellyfishPath.moveTo(mPoint0.x, mPoint0.y - dx);
        mJellyfishPath.cubicTo(mPoint1.x, mPoint1.y - dx, mPoint2.x, mPoint2.y, mPoint3.x, mPoint3.y);
        mJellyfishPath.cubicTo(mPoint4.x + dx, mPoint4.y + dx, mPoint5.x, mPoint5.y, mPoint6.x, mPoint6.y);
        mJellyfishPath.cubicTo(mPoint7.x, mPoint7.y, mPoint8.x - dx, mPoint8.y + dx, mPoint9.x, mPoint9.y);
        mJellyfishPath.cubicTo(mPoint10.x, mPoint10.y, mPoint11.x, mPoint11.y - dx, mPoint0.x, mPoint0.y - dx);
        mJellyfishPath.close();
        canvas.drawPath(mJellyfishPath, mJellyfishPaint);
    }

    private void startShrinkAnimator() {
        if (!isRotating) {
            return;
        }
        if (mShrinkAnimator == null) {
            mShrinkAnimator = getShrinkAnimator();
        }
        if (!mShrinkAnimator.isRunning()) {
            mShrinkAnimator.start();
        }
    }

    private void startExpandAnimator() {
        if (isRotating) {
            return;
        }
        if (mExpandAnimator == null) {
            mExpandAnimator = getExpandAnimator();
        }
        if (!mExpandAnimator.isRunning()) {
            mExpandAnimator.start();
        }
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result + mSpaceSize;
    }

    private Bitmap createShadeBitmap() {
        int shadeSize = mSize - mSpaceSize * 2;
        Bitmap bitmap = Bitmap.createBitmap(shadeSize, shadeSize, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        RectF f = new RectF(0, 0, shadeSize, shadeSize);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawOval(f, paint);
        return bitmap;
    }

    /**
     * 收起动画
     */
    private ValueAnimator getShrinkAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1F, 0F);
        animator.setDuration(600);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(animation -> {
            mOffsetFactor = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mRotateHandler.removeMessages(MSG_WHAT_ROTATE);
                isRotating = false;
            }
        });
        return animator;
    }

    /**
     * 展开动画
     */
    private ValueAnimator getExpandAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0F, 1F);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(animation -> mOffsetFactor = (float) animation.getAnimatedValue());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRotateHandler.sendEmptyMessage(MSG_WHAT_ROTATE);
                isRotating = true;
            }
        });
        return animator;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == MSG_WHAT_ROTATE) {
            mBehindAngle += DEFAULT_ROTATE_STEP;
            mFrontAngle += DEFAULT_ROTATE_STEP;
            invalidate();
            mRotateHandler.sendEmptyMessageDelayed(MSG_WHAT_ROTATE, 60);
        }
    }

    public void updateWave(byte[] waveform) {
        for (int i = 0, len = waveform.length - 1; i < len; i += 4) {
            int dx;
            int wave = waveform[i];
            if (wave < 0) {
                dx = -(128 + wave);
            } else if (wave > 0) {
                dx = 128 - wave;
            } else {
                dx = 0;
            }
            mVibrateOffset = dx * (mSpaceSize - mDefaultOffset) / 128;
        }
    }

    /*
    可能得找个中点让线更平滑点？
    private PointF getBezierPointForCubic(float t, PointF p0, PointF p1, PointF p2, PointF p3) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t;
        point.y = p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t;
        return point;
    }*/
}