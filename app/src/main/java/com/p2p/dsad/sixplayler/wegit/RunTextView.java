package com.p2p.dsad.sixplayler.wegit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * Created by dsad on 2017/9/11.
 */

public class RunTextView extends TextView
{

    public RunTextView(Context context) {
        super(context);
    }

    public RunTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RunTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
