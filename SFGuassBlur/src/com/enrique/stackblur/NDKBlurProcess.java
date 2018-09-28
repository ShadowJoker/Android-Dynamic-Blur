package com.enrique.stackblur;

import android.graphics.Bitmap;

import com.commit451.nativestackblur.NativeStackBlur;

/**
 * @see NDKBlurProcess
 * Blur using the NDK and native code.
 */
class NDKBlurProcess implements BlurProcess {
    @Override
    public Bitmap blur(Bitmap original, float radius) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(original,
                original.getWidth() / SFGuassBlurManager.scaleRatio,
                original.getHeight() / SFGuassBlurManager.scaleRatio,
                false);
        return NativeStackBlur.process(scaledBitmap, (int) radius);
    }
}
