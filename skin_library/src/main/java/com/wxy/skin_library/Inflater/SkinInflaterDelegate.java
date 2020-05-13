package com.wxy.skin_library.Inflater;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;

import com.wxy.skin_library.R;
import com.wxy.skin_library.skinview.SkinView;
import com.wxy.skin_library.skinview.SkinViewManager;
import com.wxy.skin_library.utils.SkinConfig;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Objects;

public class SkinInflaterDelegate implements LayoutInflater.Factory2 {

    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
    private static final String TAG = SkinInflaterDelegate.class.getName();

    private AppCompatViewInflater mAppCompatViewInflater;

    Window mWindow;
    SkinView mSkinView;

    public SkinInflaterDelegate(Window win, SkinView skinView) {
        mWindow = win;
        mSkinView = skinView;
        Log.e("========", "=====");
    }

    public void inflater(LayoutInflater inflater) {
        LayoutInflaterCompat.setFactory2(inflater, this);
    }

    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = createView(parent, name, context, attrs);
        if (null == view && -1 != name.indexOf('.')) {
            // 自定义view
            view = createView(context, name, null, attrs);
        }
        boolean enable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (enable) {
            SkinViewManager.getInstance().addSkinView(view, attrs, mSkinView);
        }
        return view;
    }

    private View createView(View parent, String name, Context context, AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            TypedArray a = context.obtainStyledAttributes(R.styleable.AppCompatTheme);
            String viewInflaterClassName =
                    a.getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if ((viewInflaterClassName == null)
                    || androidx.appcompat.app.AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                // Either default class name or set explicitly to null. In both cases
                // create the base inflater (no reflection)
                mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    Class<?> viewInflaterClass = Class.forName(viewInflaterClassName);
                    mAppCompatViewInflater =
                            (AppCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                    .newInstance();
                } catch (Throwable t) {
                    Log.i(TAG, "Failed to instantiate custom view inflater "
                            + viewInflaterClassName + ". Falling back to default.", t);
                    mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = mWindow.getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return onCreateView(null, name, context, attrs);
    }

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();
    final Object[] mConstructorArgs = new Object[2];
    static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    public final View createView(Context viewContext, String name,
                                 String prefix, AttributeSet attrs) {
        Objects.requireNonNull(viewContext);
        Objects.requireNonNull(name);
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        Class<? extends View> clazz = null;

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                clazz = Class.forName(prefix != null ? (prefix + name) : name, false,
                        viewContext.getClassLoader()).asSubclass(View.class);

                constructor = clazz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            }

            Object lastContext = mConstructorArgs[0];
            mConstructorArgs[0] = viewContext;
            Object[] args = mConstructorArgs;
            args[1] = attrs;

            try {
                final View view = constructor.newInstance(args);
                return view;
            } finally {
                mConstructorArgs[0] = lastContext;
            }
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
