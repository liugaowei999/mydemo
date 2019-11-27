package com.ly.liugw.demo.test.lru.lru.test;

import com.ly.liugw.demo.test.lru.LruCache;
import com.ly.liugw.demo.test.lru.lru.LruCacheImp;
import lombok.Data;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageMemoryCache {
	/**
	 * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
	 */
	private static final int SOFT_CACHE_SIZE = 20; // 软引用缓存容量
	private static LruCache<String, Bitmap> mLruCache; // 硬引用缓存
	private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // 软引用缓存
 
	public ImageMemoryCache() {
		//int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int memClass = 1;
        int cacheSize = 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
		mLruCache = new LruCacheImp<String, Bitmap>(cacheSize, null) {
            @Override
            public void print() {
                super.print();
                System.out.println("=============== ImageMemoryCache =================");
            }
        };
		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE, 0.75f, true) {
			private static final long serialVersionUID = 6040103833179403725L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, SoftReference<Bitmap>> eldest) {
                return super.removeEldestEntry(eldest);
            }
            //			@Override
//			protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
//				if (size() > SOFT_CACHE_SIZE) {
//					return true;
//				}
//				return false;
//			}
		};
	}
 
	/**
	 * 从缓存中获取图片
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = mLruCache.get(url);

		// 如果硬引用缓存中找不到，到软引用缓存中找
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					// 将图片移回硬缓存
					mLruCache.set(url, bitmap);
					mSoftCache.remove(url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}
		return null;
	}
 
	/**
	 * 添加图片到缓存
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
		    mLruCache.set(url, bitmap);
		}
	}
	
	public void removeBitmap(String url) {
		if (url != null) {
		    mLruCache.remove(url);
			synchronized (mSoftCache) {
				mSoftCache.remove(url);
			}
		}
	}

	public void print() {
        mLruCache.print();
    }
 
	public void clearCache() {
		mSoftCache.clear();
	}

    public static void main(String[] args) {
        ImageMemoryCache imageMemoryCache = new ImageMemoryCache();
        imageMemoryCache.addBitmapToCache("baidu.com", new Bitmap());
        imageMemoryCache.addBitmapToCache("sina.com", new Bitmap());
        imageMemoryCache.print();
        imageMemoryCache.getBitmapFromCache("baidu.com");
        imageMemoryCache.print();
    }
}

@Data
class Bitmap {
	Integer rowBytes;
	Integer height;

	String data = "fueoru3398472893";


}