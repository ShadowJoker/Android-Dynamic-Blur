package com.enrique.stackblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * @see JavaBlurProcess
 * Blur using renderscript.
 */
class RSBlurProcess implements BlurProcess {

    private final RenderScript renderScript;

    public RSBlurProcess(Context context) {
        renderScript = RenderScript.create(context.getApplicationContext());
    }

    @Override
    public Bitmap blur(Bitmap original, float radius) {
        int width = Math.round(original.getWidth() / SFGuassBlurManager.scaleRatio);
        int height = Math.round(original.getHeight() / SFGuassBlurManager.scaleRatio);
        Bitmap inputBmp = Bitmap.createScaledBitmap(original, width, height, false);

        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);

        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);

        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);

        renderScript.destroy();
        return inputBmp;
    }
}
