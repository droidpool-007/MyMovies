package com.nishant.mymovies.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

/*
 * Created by nishant on 23/12/16.
 */

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + Consts.STORAGE_CACHE_FOLDER;
        File myDir = new File(cacheDir);
        myDir.mkdirs();
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir, 52428800));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}